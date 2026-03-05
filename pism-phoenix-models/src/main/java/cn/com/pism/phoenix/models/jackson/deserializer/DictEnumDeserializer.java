package cn.com.pism.phoenix.models.jackson.deserializer;

import cn.com.pism.phoenix.models.enums.DictEnum;
import tools.jackson.core.JacksonException;
import tools.jackson.core.JsonParser;
import tools.jackson.databind.BeanProperty;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.ValueDeserializer;

import java.util.Objects;

/**
 * @author perccyking
 * @since 24-09-12 13:11
 */
public class DictEnumDeserializer extends ValueDeserializer<DictEnum<?, ?>> {

    private BeanProperty beanProperty;

    @Override
    public DictEnum<?, ?> deserialize(JsonParser p, DeserializationContext ctxt) throws JacksonException {
        String value = p.getString();
        Class<?> rawClass = beanProperty.getType().getRawClass();
        return convert(rawClass, value);
    }

    @Override
    public ValueDeserializer<?> createContextual(DeserializationContext ctxt, BeanProperty property) {
        this.beanProperty = property;
        return super.createContextual(ctxt, property);
    }


    public DictEnum<?, ?> convert(Class<?> enumClass, String input) {
        Object[] constants = enumClass.getEnumConstants();
        if (constants == null) return null;

        for (Object obj : constants) {
            DictEnum<?, ?> dictEnum = (DictEnum<?, ?>) obj;
            Enum<?> e = (Enum<?>) obj;

            if (e.name().equalsIgnoreCase(input)) {
                return dictEnum;
            }

            String codeValue = String.valueOf(dictEnum.getValue());
            if (Objects.equals(codeValue, input)) {
                return dictEnum;
            }
        }
        return null;
    }
}