package cn.com.pism.phoenix.models.jackson.serializer;

import cn.com.pism.phoenix.annotations.seri.DictEnumSerialize;
import cn.com.pism.phoenix.models.enums.DictEnum;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonStreamContext;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author perccyking
 * @since 24-09-12 13:09
 */
@Slf4j
public class DictEnumSerializer extends JsonSerializer<DictEnum<?, ?>> {
    @Override
    public void serialize(DictEnum<?, ?> value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        String k = "k";
        String v = "v";
        JsonStreamContext outputContext = serializers.getGenerator().getOutputContext();
        Object currentValue = outputContext.getCurrentValue();
        String fieldName = outputContext.getCurrentName();
        try {
            DictEnumSerialize annotation = currentValue.getClass().getDeclaredField(fieldName).getAnnotation(DictEnumSerialize.class);
            if (annotation != null) {
                k = annotation.keyName();
                v = annotation.valueName();
            }
        } catch (NoSuchFieldException e) {
            log.error(e.getMessage(), e);
        }
        Map<String, Object> jsonObject = HashMap.newHashMap(3);
        jsonObject.put(k, value.getValue());
        jsonObject.put(v, value.getName());
        jsonObject.put("ext", value.getExt());
        gen.writeObject(jsonObject);
    }
}
