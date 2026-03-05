package cn.com.pism.phoenix.models.vo;

import cn.com.pism.phoenix.models.jackson.deserializer.DictEnumDeserializer;
import cn.com.pism.phoenix.models.jackson.serializer.DictEnumSerializer;
import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import tools.jackson.databind.annotation.JsonDeserialize;
import tools.jackson.databind.annotation.JsonSerialize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author perccyking
 * @since 26-03-03 21:25
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@JacksonAnnotationsInside
@JsonSerialize(using = DictEnumSerializer.class)
@JsonDeserialize(using = DictEnumDeserializer.class)
public @interface DictEnumWrapper {
}
