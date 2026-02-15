package cn.com.pism.phoenix.models.constant;

import cn.com.pism.phoenix.models.config.SystemConfig;

/**
 * @author perccyking
 * @since 24-09-12 16:34
 */
public class PmnxCasConstants {
    private PmnxCasConstants() {
    }

    private static final String PREFIX = "pmnx:cas:";

    public static final String BASE_KEY = SystemConfig.getCachePrefix() + PREFIX;

    public static final String RSA_CACHE_KEY = BASE_KEY + "rsa:";

    /**
     * RSA密钥池前缀（方案：keyId 摘要取模 + 固定池）
     */
    public static final String RSA_POOL_KEY = BASE_KEY + "rsa:pool:";

    /**
     * RSA密钥池大小
     */
    public static final int RSA_POOL_SIZE = 500;

    /**
     * RSA密钥池过期时间（秒）
     */
    public static final long RSA_POOL_EXPIRE_SECONDS = 10;

}
