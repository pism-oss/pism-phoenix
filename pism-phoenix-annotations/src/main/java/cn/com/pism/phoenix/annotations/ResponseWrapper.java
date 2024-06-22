package cn.com.pism.phoenix.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 对接口响应进行自动包装
 *
 * @author perccyking
 * @since 24-06-03 17:22
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ResponseWrapper {

    /**
     * <p>
     * 是否忽略包装
     * </p>
     *
     * @return {@link boolean} true 忽略、false 不忽略
     */
    boolean ignore() default false;
}
