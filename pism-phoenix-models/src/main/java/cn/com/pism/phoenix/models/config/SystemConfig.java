package cn.com.pism.phoenix.models.config;

import org.apache.commons.lang3.StringUtils;

import static cn.com.pism.phoenix.models.constant.PmnxConstants.COLON;


/**
 * @author perccyking
 * @since 24-08-27 02:33
 */
public class SystemConfig {
    private SystemConfig() {
    }

    private static String cachePrefix = "";

    public static String getCachePrefix() {
        if (StringUtils.isNotBlank(cachePrefix) && !cachePrefix.endsWith(COLON)) {
            return cachePrefix + COLON;
        }
        return cachePrefix;
    }

    public static void setCachePrefix(String cachePrefix) {
        SystemConfig.cachePrefix = cachePrefix;
    }
}
