package cn.com.pism.phoenix.annotations.env;

import java.lang.annotation.*;

/**
 * @author perccyking
 * @since 24-09-13 12:23
 */
@Documented
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Env {
    EnvEnum[] value() default {EnvEnum.PROD};
}
