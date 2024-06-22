package cn.com.pism.phoenix.annotations.form;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author perccyking
 * @since 24-06-10 10:34
 */
@Target({ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface FormOption {

    /**
     * 选项列表
     *
     * @return 选项
     */
    FormOptionItem[] items() default {};

    /**
     * 选项能使用的条件
     *
     * @return 条件
     */
    FormCondition condition() default @FormCondition;
}
