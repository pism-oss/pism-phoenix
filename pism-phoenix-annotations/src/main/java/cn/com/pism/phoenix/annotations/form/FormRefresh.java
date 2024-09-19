package cn.com.pism.phoenix.annotations.form;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @author perccyking
 * @since 24-09-19 22:56
 */
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface FormRefresh {

    Class<?>[] value() default Void.class;
}
