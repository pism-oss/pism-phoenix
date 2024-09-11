package cn.com.pism.phoenix.starter;

import cn.com.pism.ezasse.starter.annotation.EnableEzasse;
import cn.com.pism.mybatis.starter.annotation.EnablePismMybatisPlus;
import cn.com.pism.phoenix.core.config.PmnxProperties;
import cn.com.pism.phoenix.core.config.SystemConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
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
@EnableConfigurationProperties(PmnxProperties.class)
@EnableEzasse
public class PismPhoenixConfiguration {

    @Autowired
    public void configInit(PmnxProperties properties) {
        SystemConfig.setCachePrefix(properties.getCachePrefix());
    }
}
