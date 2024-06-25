package cn.com.pism.phoenix.core.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * knife4j 配置
 *
 * @author perccyking
 * @since 24-06-22 21:53
 */
@Configuration
public class PmnxKnife4jConfiguration {

    @Bean
    public GroupedOpenApi coreApi() {
        String[] paths = {"/**"};
        String[] packagedToMatch = {"cn.com.pism.phoenix.core.controller"};
        return GroupedOpenApi.builder().group("core")
                .pathsToMatch(paths)
                .packagesToScan(packagedToMatch).build();
    }

    @Bean
    public OpenAPI baseInfo() {
        return new OpenAPI()
                .info(new Info()
                        .title("Pism Phoenix Core")
                        .version("v0.0.1"));
    }
}
