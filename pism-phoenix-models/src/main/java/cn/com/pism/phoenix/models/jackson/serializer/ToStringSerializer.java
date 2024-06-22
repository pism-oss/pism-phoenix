package cn.com.pism.phoenix.models.jackson.serializer;

import cn.com.pism.phoenix.utils.Jackson;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.apache.commons.lang3.ClassUtils;

import java.io.IOException;

/**
 * 序列化为string
 *
 * @author perccyking
 * @since 24-06-03 18:48
 */
public class ToStringSerializer extends JsonSerializer<Object> {

    @Override
    public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
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
