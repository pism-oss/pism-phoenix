package cn.com.pism.phoenix.models.jackson.serializer;

import cn.com.pism.phoenix.annotations.serialize.DictEnumSerialize;
import cn.com.pism.phoenix.models.enums.DictEnum;
import lombok.extern.slf4j.Slf4j;
import tools.jackson.core.JacksonException;
import tools.jackson.core.JsonGenerator;
import tools.jackson.databind.BeanProperty;
import tools.jackson.databind.SerializationContext;
import tools.jackson.databind.ValueSerializer;

import java.util.HashMap;
import java.util.Map;

/**
 * @author perccyking
 * @since 24-09-12 13:09
 */
@Slf4j
public class DictEnumSerializer extends ValueSerializer<DictEnum<?, ?>> {

    private BeanProperty beanProperty;

    @Override
    public void serialize(DictEnum<?, ?> value, JsonGenerator gen, SerializationContext ctxt) throws JacksonException {
        String k = "k";
        String v = "v";
        DictEnumSerialize annotation = beanProperty.getAnnotation(DictEnumSerialize.class);
        if (annotation != null) {
            k = annotation.keyName();
            v = annotation.valueName();
        }
        Map<String, Object> jsonObject = HashMap.newHashMap(3);
        jsonObject.put(k, value.getValue());
        jsonObject.put(v, value.getName());
        jsonObject.put("ext", value.getExt());
        gen.writePOJO(jsonObject);
    }

    @Override
    public ValueSerializer<?> createContextual(SerializationContext ctxt, BeanProperty property) {
        this.beanProperty = property;
        return super.createContextual(ctxt, property);
    }
}
