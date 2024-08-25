package cn.com.pism.phoenix.starter;

import cn.com.pism.mybatis.starter.annotation.EnablePismMybatisPlus;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author perccyking
 * @since 24-06-10 12:24
 */
@Configuration
@ComponentScan(
        basePackages = {
                "cn.com.pism.phoenix"
        }
)
@EnableCaching
@EnablePismMybatisPlus
//@EnableEzasse
public class PismPhoenixConfiguration {
}
