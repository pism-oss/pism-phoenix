package cn.com.pism.phoenix.annotations.seri;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author perccyking
 * @since 24-09-12 10:02
 */
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DictEnumSerialize {

    String keyName() default "k";

    String valueName() default "v";

    String keyDesc() default "";

    String valueDesc() default "";
}
