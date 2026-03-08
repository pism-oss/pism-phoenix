package cn.com.pism.phoenix.core.config.swagger;

import cn.com.pism.phoenix.models.enums.DictEnum;
import cn.com.pism.phoenix.models.vo.DictEnumWrapper;
import com.fasterxml.jackson.databind.type.SimpleType;
import io.swagger.v3.core.converter.AnnotatedType;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.Paths;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.media.StringSchema;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springdoc.core.customizers.GlobalOpenApiCustomizer;
import org.springdoc.core.customizers.PropertyCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.annotation.Annotation;
import java.util.*;

/**
 * springdoc 增强
 *
 * @author perccyking
 * @since 24-06-22 22:02
 */
@Configuration
public class PmnxSpringDocOpenApiConfiguration {

    private static final String X_ORDER = "x-order";

    private static final String X_DICT_ENUM = "x-ext-enum";
    private static final String X_DICT_ENUM_DESC = "x-ext-enum-desc";
    private static final String X_EXT = "x-ext";

    private static final String EXT = "Ext";

    @Bean
    public PropertyCustomizer dictEnumPropertyCustomizer() {
        return (Schema propertySchema, AnnotatedType type) -> {
            if (type.getCtxAnnotations() != null) {
                for (Annotation annotation : type.getCtxAnnotations()) {
                    if (annotation instanceof DictEnumWrapper dictEnumWrapper) {

                        DictEnum[] enumConstants = (DictEnum[]) ((SimpleType) type.getType()).getRawClass().getEnumConstants();
                        List<String> enums = new ArrayList<>();
                        StringBuilder des = new StringBuilder();
                        for (DictEnum enumConstant : enumConstants) {
                            enums.add(((Enum) enumConstant).name());
                            des.append(((Enum) enumConstant).name())
                                    .append("(")
                                    .append(enumConstant.getValue())
                                    .append(",")
                                    .append(enumConstant.getName())
                                    .append(")")
                                    .append(";\n");
                        }
                        propertySchema.addExtension(X_DICT_ENUM, enums);
                        propertySchema.addExtension(X_DICT_ENUM_DESC, des.toString());
                        propertySchema.addExtension(X_EXT, true);
                    }
                }
            }
            return propertySchema;
        };
    }

    @Bean
    public GlobalOpenApiCustomizer requestResponseSchemaCustomizer() {

        return openApi -> {

            if (openApi.getComponents() == null || openApi.getComponents().getSchemas() == null) {
                return;
            }

            // 已有的schema
            Map<String, Schema> schemas = openApi.getComponents().getSchemas();
            Map<String, Schema<?>> newReqSchemas = new HashMap<>();

            schemas.forEach((schemaName, schema) -> {
                Map<String, Schema> properties = schema.getProperties();
                if (properties == null) {
                    return;
                }

                boolean hasExt = properties
                        .values()
                        .stream()
                        .anyMatch(prop -> prop.getExtensions() != null
                                && prop.getExtensions().containsKey(X_EXT));

                if (hasExt) {
                    String extSchemaName = schemaName + EXT;
                    Schema extSchema = new Schema();
                    extSchema.setType(schema.getType());
                    extSchema.setDescription(schema.getDescription() + EXT);
                    properties.forEach((propName, originalProp) -> {
                        if (originalProp.getExtensions() != null && originalProp.getExtensions().containsKey(X_EXT)) {
                            StringSchema extProp = new StringSchema();
                            extProp.setEnum((List<String>) originalProp.getExtensions().get(X_DICT_ENUM));
                            extProp.setDescription(originalProp.getDescription()+":\n"+originalProp.getExtensions().get(X_DICT_ENUM_DESC));
                            extSchema.addProperty(propName, extProp);

                        } else {
                            extSchema.addProperty(propName, originalProp);
                        }
                    });
                    newReqSchemas.put(extSchemaName, extSchema);
                }
            });

            schemas.putAll(newReqSchemas);

            if (openApi.getPaths() == null) {
                return;
            }
            openApi.getPaths().values().forEach(pathItem -> {
                pathItem.readOperations().forEach(operation -> {
                    if (operation.getRequestBody() != null && operation.getRequestBody().getContent() != null) {
                        operation.getRequestBody().getContent().values().forEach(mediaType -> {
                            if (mediaType.getSchema() != null && mediaType.getSchema().get$ref() != null) {
                                String ref = mediaType.getSchema().get$ref();

                                // 提取原本的 VO 名称 (比如 ref 是 "#/components/schemas/UserVO")
                                String refName = ref.substring(ref.lastIndexOf("/") + 1);

                                // 检查：如果我们刚刚为这个 VO 克隆了请求版，就修改接口引用
                                if (newReqSchemas.containsKey(refName + EXT)) {
                                    mediaType.getSchema().set$ref(ref + EXT);
                                }
                            }
                        });
                    }
                });
            });

            addSerial(openApi);

        };
    }

    /**
     * 添加序号
     *
     * @param openApi openApi
     */
    private void addSerial(OpenAPI openApi) {
        if (openApi.getPaths() == null) {
            return;
        }

        Map<String, Integer> groupSerial = HashMap.newHashMap(16);
        Map<String, Integer> groupPathSerial = HashMap.newHashMap(16);
        Set<String> groupSet = HashSet.newHashSet(16);

        Paths paths = openApi.getPaths();
        paths.forEach((path, v) -> {
            for (Operation operation : v.readOperations()) {

                addGroupSerial(operation, groupSerial, groupSet);

                addPathSerial(operation, groupPathSerial);
            }
        });
    }

    private void addPathSerial(Operation operation, Map<String, Integer> groupPathSerial) {
        Map<String, Object> extensions = operation.getExtensions();
        int serial = 0;
        if (MapUtils.isNotEmpty(extensions)) {
            serial = (int) extensions.getOrDefault(X_ORDER, Integer.MAX_VALUE);
            if (Integer.MAX_VALUE == serial || Integer.MIN_VALUE == serial) {
                serial = getSerial(operation, serial, groupPathSerial, extensions);
            }
        } else {
            Map<String, Object> newExtensions = new HashMap<>();
            serial = getSerial(operation, serial, groupPathSerial, newExtensions);
            operation.setExtensions(newExtensions);
        }

        String itemName = StringUtils.firstNonBlank(operation.getSummary(), operation.getDescription(), operation.getOperationId());
        operation.setSummary(serial + "." + itemName);
    }

    private int getSerial(Operation operation, int serial, Map<String, Integer> groupPathSerial, Map<String, Object> newExtensions) {
        List<String> tags = operation.getTags();
        if (CollectionUtils.isNotEmpty(tags)) {
            String tag = tags.getFirst();
            serial = groupPathSerial.getOrDefault(tag, 0);
            groupPathSerial.put(tag, serial + 1);
            newExtensions.put(X_ORDER, serial);
        }
        return serial;
    }

    private void addGroupSerial(Operation operation, Map<String, Integer> groupSerial, Set<String> groupSet) {
        List<String> tags = new ArrayList<>();
        for (String tag : operation.getTags()) {
            Integer orDefault = groupSerial.getOrDefault(tag, groupSet.size());
            groupSerial.put(tag, orDefault);
            groupSet.add(tag);
            tags.add(orDefault + "." + tag);
        }
        operation.setTags(tags);
    }

}
