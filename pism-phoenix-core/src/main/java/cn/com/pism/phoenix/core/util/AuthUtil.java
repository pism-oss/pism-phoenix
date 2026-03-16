package cn.com.pism.phoenix.core.util;

import cn.com.pism.phoenix.utils.FunctionUtil;
import cn.dev33.satoken.stp.StpUtil;

/**
 * @author perccyking
 * @since 26-03-08 23:35
 */
public class AuthUtil {

    public static final Long UNKNOWN_USER = -99L;


    private AuthUtil() {
    }

    public static Long getLoginId() {
        // 可以考虑对当前方法做本地缓存优化
        return FunctionUtil.catchRun(StpUtil::getLoginIdAsLong, UNKNOWN_USER);
    }
}
