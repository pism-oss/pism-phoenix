package cn.com.pism.phoenix.utils;

import java.util.function.Supplier;

/**
 * @author perccyking
 * @since 24-09-08 14:55
 */
public class LogicUtil {
    private LogicUtil() {
    }


    public static <T> T get(Supplier<T> get, Supplier<T> defaultValue) {
        T value = get.get();
        if (ObjectExUtil.isNotBlank(value)) {
            return value;
        }
        return defaultValue.get();
    }
}
