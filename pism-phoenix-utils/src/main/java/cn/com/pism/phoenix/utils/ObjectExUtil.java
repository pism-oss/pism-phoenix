package cn.com.pism.phoenix.utils;


import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.function.Consumer;

/**
 * 对象扩展工具类
 *
 * @author perccyking
 * @since 24-06-03 17:38
 */
public class ObjectExUtil {

    private ObjectExUtil() {
    }


    public static <T> void isBlank(T t, Runnable isBlank) {
        boolean isNull = t == null
                || (t instanceof String str && StringUtils.isBlank(str))
                || (t instanceof List<?> list && CollectionUtils.isEmpty(list));
        if (isNull && isBlank != null) {
            isBlank.run();
        }
    }

    public static <T> void isNotBlank(T t, Consumer<T> isNotBlank, Runnable isBlank) {
        switch (t) {
            // null
            case null -> {
                if (isBlank != null) {
                    isBlank.run();
                }
            }

            // 字符串
            case String s -> {
                if (StringUtils.isNotBlank(s)) {
                    isNotBlank.accept(t);
                } else {
                    if (isBlank != null) {
                        isBlank.run();
                    }
                }
            }

            //集合
            case List<?> list -> CollectionExUtil.isNotEmpty(list, () -> isNotBlank.accept(t), isBlank);

            default -> isNotBlank.accept(t);
        }
    }

    public static <T> void isNotBlank(T t, Consumer<T> isNotBlank) {
        isNotBlank(t, isNotBlank, null);
    }

    public static <T> void isNotBlank(T t, Runnable isNotBlank) {
        isNotBlank(t, isNotBlank, null);
    }

    public static <T> void isNotBlank(T t, Runnable isNotBlank, Runnable isBlank) {
        isNotBlank(t, co -> isNotBlank.run(), isBlank);
    }
}
