package cn.com.pism.phoenix.models.jackson.serializer;

import cn.com.pism.phoenix.utils.Jackson;
import org.apache.commons.lang3.ClassUtils;
import tools.jackson.core.JacksonException;
import tools.jackson.core.JsonGenerator;
import tools.jackson.databind.SerializationContext;
import tools.jackson.databind.ValueSerializer;

/**
 * 序列化为string
 *
 * @author perccyking
 * @since 24-06-03 18:48
 */
public class ToStringSerializer extends ValueSerializer<Object> {

    @Override
    public void serialize(Object value, JsonGenerator gen, SerializationContext ctxt) throws JacksonException {
        String res = null;
        if (value != null) {
            if (ClassUtils.isPrimitiveOrWrapper(value.getClass())) {
                res = String.valueOf(value);
            } else {
                res = Jackson.toJsonString(value);
            }
        }
        if (res != null) {
            gen.writeString(res);
        }
    }
}
