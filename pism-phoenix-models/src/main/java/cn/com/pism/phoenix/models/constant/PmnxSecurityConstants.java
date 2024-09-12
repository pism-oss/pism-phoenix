package cn.com.pism.phoenix.models.constant;

import cn.com.pism.phoenix.models.config.SystemConfig;

/**
 * @author perccyking
 * @since 24-08-27 19:58
 */
public class PmnxSecurityConstants {

    private PmnxSecurityConstants() {
    }

    private static final String PREFIX = "pmnx:security:";

    public static final String BASE_KEY = SystemConfig.getCachePrefix() + PREFIX;


    /*
     * redis cache key
     */
    public static final String ROLE_PERMISSION_CACHE_KEY = BASE_KEY + "role_permission:";

    public static final String USER_PERMISSION_CACHE_KEY = BASE_KEY + "user:%s:permission";

    public static final String USER_ROLE_CACHE_KEY = BASE_KEY + "user:%s:role";

    /*
     * local cache key
     */

    public static final String BLACK_LIST_LOCAL_CACHE_KEY = PREFIX + "blacklist";

    public static final String WHITE_LIST_LOCAL_CACHE_KEY = PREFIX + "whitelist";

    public static final String RESOURCE_CODE_LOCAL_CACHE_KEY = PREFIX + "resource_code:";
}
