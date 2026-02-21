package cn.com.pism.phoenix.core.config.swagger.converter;

import cn.com.pism.phoenix.models.enums.DictEnum;
import cn.com.pism.phoenix.models.jackson.serializer.DictEnumSerializer;
import com.fasterxml.jackson.databind.JavaType;
import io.swagger.v3.core.converter.AnnotatedType;
import io.swagger.v3.core.converter.ModelConverter;
import io.swagger.v3.core.converter.ModelConverterContext;
import io.swagger.v3.oas.models.media.ObjectSchema;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.media.StringSchema;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.providers.ObjectMapperProvider;
import org.springframework.stereotype.Component;

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
    public Schema resolve(AnnotatedType type, ModelConverterContext context, Iterator<ModelConverter> chain) {
        JavaType javaType = springDocObjectMapper.jsonMapper().constructType(type.getType());
        if (javaType != null) {
            Class<?> cls = javaType.getRawClass();
            if (cls.isEnum() && DictEnum.class.isAssignableFrom(cls)) {
                ObjectSchema schema = new ObjectSchema();

                StringSchema kSchema = new StringSchema();
                kSchema.setDescription("枚举key");
                StringSchema vSchema = new StringSchema();
                vSchema.setDescription("枚举值");
                schema.addProperty("k", kSchema);
                schema.addProperty("v", vSchema);

                schema.setDescription(DictEnum.class.getSimpleName());

                return schema;
            }
        }
        return (chain.hasNext()) ? chain.next().resolve(type, context, chain) : null;
    }
}
