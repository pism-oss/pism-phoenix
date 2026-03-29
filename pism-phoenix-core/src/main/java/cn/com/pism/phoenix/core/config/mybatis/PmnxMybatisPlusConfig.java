package cn.com.pism.phoenix.core.config.mybatis;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author perccyking
 * @since 24-08-25 18:23
 */
@MapperScan("cn.com.pism.phoenix.core.mapper")
@Configuration
@RequiredArgsConstructor
public class PmnxMybatisPlusConfig {

    private final LogicDeletedInnerInterceptor logicDeletedInnerInterceptor;


    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(logicDeletedInnerInterceptor);
        // 添加防全表更新插件
        interceptor.addInnerInterceptor(new BlockAttackInnerInterceptor());
        // 添加分页插件
//        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }

}
