package cn.com.pism.phoenix.annotations.cache;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * @author perccyking
 * @since 24-09-11 12:25
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PmnxLocalCache {

    /**
     * 缓存key
     */
    @AliasFor("key")
    String value() default "";

    @AliasFor("value")
    String key() default "";

    /**
     * 是否更新
     */
    boolean update() default false;

    /**
     * 过期时间
     */
    long expire() default 24L;

    /**
     * 时间单位
     */
    TimeUnit timeUnit() default TimeUnit.HOURS;
}
