package cn.com.pism.phoenix.starter.annotations;

import cn.com.pism.phoenix.starter.PismPhoenixConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author perccyking
 * @since 24-06-10 12:18
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({PismPhoenixConfiguration.class})
@Documented
public @interface EnablePismPhoenix {
}
