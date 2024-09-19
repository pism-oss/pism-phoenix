package cn.com.pism.phoenix.models.constant;

import cn.com.pism.phoenix.models.config.SystemConfig;

/**
 * @author perccyking
 * @since 24-09-19 22:38
 */
public class PmnxFormConstants {

    private PmnxFormConstants() {
    }

    private static final String PREFIX = "pmnx:form:";

    public static final String BASE_KEY = SystemConfig.getCachePrefix() + PREFIX;

    public static final String FORM_VERSION_MARK_KEY = BASE_KEY + "version:";
}
