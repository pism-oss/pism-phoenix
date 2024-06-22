package cn.com.pism.phoenix.annotations.form;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author perccyking
 * @since 24-06-10 10:39
 */
@Target({ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface FormOptionItem {

    /**
     * 选项值
     *
     * @return value
     */
    String value() default "";

    /**
     * 选项名称
     *
     * @return 名称
     */
    String label() default "";
}
