package cn.com.pism.phoenix.models.constant;

import cn.com.pism.phoenix.models.config.SystemConfig;

/**
 * @author perccyking
 * @since 24-09-12 16:34
 */
public class PmnxCasConstant {
    private PmnxCasConstant() {
    }

    private static final String PREFIX = "pmnx:cas:";

    public static final String BASE_KEY = SystemConfig.getCachePrefix() + PREFIX;

    public static final String RSA_CACHE_KEY = BASE_KEY + "rsa:";
}
