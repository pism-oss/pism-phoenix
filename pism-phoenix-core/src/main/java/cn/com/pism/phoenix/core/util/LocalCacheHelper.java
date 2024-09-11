package cn.com.pism.phoenix.core.util;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Nonnull;
import java.util.Map;
import java.util.concurrent.*;

/**
 * @author perccyking
 * @since 24-09-10 23:23
 */
@Slf4j
public class LocalCacheHelper {
    private LocalCacheHelper() {
    }

    private static final Map<String, Cache<String, String>> LOCAL_CACHE_MAP = new ConcurrentHashMap<>();

    private static final ThreadPoolExecutor EXECUTOR = new ThreadPoolExecutor(
            2,
            4,
            60L,
            TimeUnit.SECONDS,
            new LinkedBlockingQueue<>()
    ) {
        @Override
        @Nonnull
        public Future<?> submit(@Nonnull Runnable task) {
            // 提交任务时，首先删除所有队列中未执行的任务
            getQueue().clear();
            return super.submit(task);
        }
    };

    public static Cache<String, String> addCache(@Nonnull String key, @Nonnull Cache<String, String> value) {
        LOCAL_CACHE_MAP.put(key, value);
        return value;
    }

    public static Cache<String, String> createCache(String key, long timeout, TimeUnit timeUnit) {
        return LocalCacheHelper.addCache(key, Caffeine.newBuilder()
                .expireAfterWrite(timeout, timeUnit)
                .build());
    }

    public static Cache<String, String> getCache(@Nonnull String key) {
        return LOCAL_CACHE_MAP.get(key);
    }

    public static String getValue(@Nonnull String key, @Nonnull String field) {
        Cache<String, String> cache = getCache(key);
        if (cache != null) {
            try {
                return cache.getIfPresent(field);
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }

    public static void setCache(@Nonnull String key, String field, @Nonnull String value) {
        Cache<String, String> cache = getCache(key);
        if (cache != null) {
            cache.put(field, value);
        }
        log.trace("cache:{} not init", key);
    }

    public static void submitAction(@Nonnull Runnable action) {
        EXECUTOR.submit(action);
    }

}
