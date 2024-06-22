package cn.com.pism.phoenix.annotations.form;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 表单条件
 *
 * @author perccyking
 * @since 24-06-10 09:53
 */
@Target({ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface FormCondition {

    /**
     * 关联的字段
     *
     * @return 字段key
     */
    String field() default "";

    /**
     * 期望值
     *
     * @return 期望值
     */
    String value() default "";
}
