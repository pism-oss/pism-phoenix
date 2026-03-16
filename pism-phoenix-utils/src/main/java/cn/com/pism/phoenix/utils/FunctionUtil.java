package cn.com.pism.phoenix.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.function.Supplier;

/**
 * @author perccyking
 * @since 26-03-08 23:25
 */
@Slf4j
public class FunctionUtil {

    private FunctionUtil() {
    }

    public static <T> T catchRun(Supplier<T> supplier) {
        try {
            return supplier.get();
        } catch (Exception e) {
            log.warn(e.getMessage());
        }
        return null;
    }

    public static <T> T catchRun(Supplier<T> supplier, T defaultValue) {
        T result = catchRun(supplier);
        if (result == null) {
            return defaultValue;
        }
        return result;
    }
}
