package cn.com.pism.phoenix.annotations.form;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * 用于标记表单类型
 *
 * @author perccyking
 * @since 24-06-07 15:03
 */
@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface Form {

    @AliasFor("label")
    String value() default "";

    /**
     * label
     *
     * @return label
     */
    @AliasFor("value")
    String label() default "";

    /**
     * 表单排序
     *
     * @return 排序
     */
    int order() default 0;


}
