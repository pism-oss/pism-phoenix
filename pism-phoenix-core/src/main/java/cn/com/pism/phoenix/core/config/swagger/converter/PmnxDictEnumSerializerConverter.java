package cn.com.pism.phoenix.core.config.swagger.converter;

import cn.com.pism.phoenix.models.enums.DictEnum;
import cn.com.pism.phoenix.models.jackson.serializer.DictEnumSerializer;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.core.converter.AnnotatedType;
import io.swagger.v3.core.converter.ModelConverter;
import io.swagger.v3.core.converter.ModelConverterContext;
import io.swagger.v3.oas.models.media.ObjectSchema;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.media.StringSchema;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.providers.ObjectMapperProvider;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.util.Iterator;

/**
 * @author perccyking
 * @see DictEnumSerializer
 * @since 26-02-21 13:42
 */
@Component
@RequiredArgsConstructor
public class PmnxDictEnumSerializerConverter implements PmnxModelConverter {

    private final ObjectMapperProvider springDocObjectMapper;

    @Override
    public Schema resolve(AnnotatedType type,
                          ModelConverterContext context,
                          Iterator<ModelConverter> chain) {

        JavaType javaType = springDocObjectMapper
                .jsonMapper()
                .constructType(type.getType());

        if (javaType == null) {
            return next(type, context, chain);
        }

        Class<?> rawClass = javaType.getRawClass();

        if (!isDictEnum(rawClass)) {
            return next(type, context, chain);
        }

        if (!hasDictEnumSerializer(type.getCtxAnnotations())) {
            return next(type, context, chain);
        }

        return buildDictEnumSchema(rawClass);
    }

    private boolean isDictEnum(Class<?> cls) {
        return cls.isEnum() && DictEnum.class.isAssignableFrom(cls);
    }

    private boolean hasDictEnumSerializer(Annotation[] annotations) {
        if (annotations == null) {
            return false;
        }

        for (Annotation annotation : annotations) {
            if (annotation instanceof JsonSerialize jsonSerialize) {
                Class<?> using = jsonSerialize.using();
                return DictEnumSerializer.class.isAssignableFrom(using);
            }
        }
        return false;
    }

    private Schema<?> buildDictEnumSchema(Class<?> enumClass) {

        ObjectSchema schema = new ObjectSchema();

        StringSchema keySchema = new StringSchema();
        keySchema.setDescription("枚举key");

        StringSchema valueSchema = new StringSchema();
        valueSchema.setDescription("枚举值");

        schema.addProperty("k", keySchema);
        schema.addProperty("v", valueSchema);

        schema.setDescription(enumClass.getSimpleName());

        return schema;
    }

}
