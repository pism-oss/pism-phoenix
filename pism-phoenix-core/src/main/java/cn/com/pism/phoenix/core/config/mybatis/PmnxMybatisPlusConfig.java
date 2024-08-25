package cn.com.pism.phoenix.core.config.mybatis;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.autoconfigure.MybatisPlusProperties;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Nonnull;

/**
 * @author perccyking
 * @since 24-08-25 18:23
 */
@MapperScan("cn.com.pism.phoenix.core.mapper")
@Configuration
public class PmnxMybatisPlusConfig implements BeanPostProcessor {

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        // 添加防全表更新插件
        interceptor.addInnerInterceptor(new BlockAttackInnerInterceptor());
        // 添加分页插件
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }

    @Override
    public Object postProcessAfterInitialization(@Nonnull Object bean, @Nonnull String beanName) throws BeansException {
        if (bean instanceof MybatisPlusProperties mybatisPlusProperties) {
            MybatisPlusProperties.CoreConfiguration configuration = mybatisPlusProperties.getConfiguration();
            if (configuration == null) {
                configuration = new MybatisPlusProperties.CoreConfiguration();
                mybatisPlusProperties.setConfiguration(configuration);
            }
            if (configuration.getMapUnderscoreToCamelCase() == null) {
                configuration.setMapUnderscoreToCamelCase(Boolean.TRUE);
            }
            GlobalConfig globalConfig = mybatisPlusProperties.getGlobalConfig();
            if (globalConfig == null) {
                globalConfig = new GlobalConfig();
                mybatisPlusProperties.setGlobalConfig(globalConfig);
            }
            GlobalConfig.DbConfig dbConfig = globalConfig.getDbConfig();
            if (dbConfig == null) {
                dbConfig = new GlobalConfig.DbConfig();
                globalConfig.setDbConfig(dbConfig);
            }
            dbConfig.setLogicDeleteField("dlt");
            dbConfig.setLogicDeleteValue("1");
            dbConfig.setLogicNotDeleteValue("0");
        }
        return bean;
    }

}
