package cn.com.pism.phoenix.core.config.knife4j;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
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
        License license = new License();
        license.setName("Apache 2.0");
        license.setUrl("http://www.apache.org/licenses/LICENSE-2.0.html");

        Contact contact = new Contact();
        contact.setName("https://github.com/pism-oss");
        contact.setUrl("https://github.com/pism-oss");
        contact.setEmail("oss@pism.com.cn");

        return new OpenAPI()
                .info(new Info()
                        .title("Pism Phoenix Core")
                        .description("Pism Phoenix Core")
                        .contact(contact)
                        .summary("summary")
                        .license(license)
                        .version("v0.0.1"));
    }
}
