package cn.com.pism.phoenix.core.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * @author perccyking
 * @since 24-08-28 12:25
 */
@ConfigurationProperties(prefix = "spring.pmnx")
@Data
public class PmnxProperties {

    /**
     * 缓存key前缀
     */
    private String cachePrefix;

    /**
     * 安全配置
     */
    private Security security = new Security();


    /**
     * 安全配置
     */
    @Data
    public static class Security {

        /**
         * 是否开启鉴权
         */
        private boolean enabled = true;

        /**
         * 白名单，不需要鉴权的url
         */
        private List<String> whitelist = new ArrayList<>();

        /**
         * 黑名单，任何人都不能访问的url
         * 优先级高于白名单，如果同时存在白名单和黑名单中，黑名单优先
         */
        private List<String> blacklist = new ArrayList<>();
    }

}
