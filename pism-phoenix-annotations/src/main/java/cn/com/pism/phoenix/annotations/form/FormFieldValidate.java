package cn.com.pism.phoenix.annotations.form;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author perccyking
 * @since 24-06-09 20:28
 */
@Target({ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface FormFieldValidate {

    /**
     * 是否必填 默认false 非必填
     *
     * @return 是否必填
     */
    boolean required() default false;

    /**
     * 最大长度 -1不限
     *
     * @return 最大长度
     */
    int maxLength() default -1;

    /**
     * 最小长度 -1 不限
     *
     * @return 最小长度
     */
    int minLength() default -1;
}
