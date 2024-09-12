package cn.com.pism.phoenix.core.util;

import cn.com.pism.phoenix.utils.Jackson;
import cn.com.pism.phoenix.utils.ObjectExUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.github.benmanes.caffeine.cache.Cache;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

import static cn.com.pism.phoenix.core.util.LocalCacheHelper.createCache;

/**
 * @author perccyking
 * @since 24-09-09 21:40
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class LocalCacheUtil {

    private final StringRedisTemplate stringRedisTemplate;

    public static final String VERSION = "version";

    /**
     * 获取或更新缓存
     * <p>
     * 从本地缓存获取数据
     * 如果获取到了直接返回
     * 没有获取到，请求缓存数据，放入本地缓存，更新本地版本号以及远程版本号
     * </p>
     *
     * @param key         缓存key
     * @param getSupplier 缓存数据
     * @param expire      过期时间
     * @param timeUnit    时间单位
     * @return 缓存
     */
    public <T> T getOrUpdate(String key, Supplier<T> getSupplier, long expire, TimeUnit timeUnit, TypeReference<T> typeReference) {
        //获取本地缓存对象
        Cache<String, String> cache = LocalCacheHelper.getCache(key);

        //判断本地缓存是否存在
        if (cache == null) {

            //不纯在创建缓存对象
            cache = createCache(key, expire, timeUnit);
        }

        //缓存数据
        String cacheValue = null;
        try {

            //本地缓存的数据
            cacheValue = cache.getIfPresent(key);
        } catch (Exception e) {
            log.trace("local cache is empty", e);
        }

        //判断本地缓存的数据是否存在
        if (StringUtils.isNotBlank(cacheValue)) {
            try {

                //本地缓存有数据，直接序列化返回
                return Jackson.instance().readValue(cacheValue, typeReference);
            } catch (JsonProcessingException e) {

                //序列化失败，删除本地缓存
                cache.invalidate(key);
            }
        }

        //更新缓存数
        T value = updateCache(key, getSupplier);

        //添加异步更新任务
        LocalCacheHelper.submitAction(() -> {

            //获取缓存版本号，如果两个版本号一致，不做任何操作
            String version = LocalCacheHelper.getValue(key, VERSION);

            //同步版本号
            String syncVersion = stringRedisTemplate.opsForValue().get(key);
            if ((!StringUtils.isAnyEmpty(version, syncVersion)) && StringUtils.compare(version, syncVersion) != 0) {

                //版本号不一致，更新本地缓存数据
                LocalCacheHelper.setCache(key, key, Jackson.toJsonStringNonNull(getSupplier.get()));
            }
        });

        return value;
    }

    public <T> T getOrUpdate(String key, Supplier<T> getSupplier, long timeout, TypeReference<T> typeReference) {
        return getOrUpdate(key, getSupplier, timeout, TimeUnit.MILLISECONDS, typeReference);
    }

    /**
     * <p>
     * 更新缓存
     * </p>
     * by perccyking
     *
     * @param key         : 缓存key
     * @param getSupplier : 缓存数据
     * @return {@link T} 最新缓存数据
     * @since 24-09-12 01:15
     */
    public <T> T updateCache(String key, Supplier<T> getSupplier) {
        //获取缓存数据
        T cacheValue = getSupplier.get();

        //判断数据是否存在
        if (ObjectExUtil.isNotBlank(cacheValue)) {

            //更新缓存数据
            LocalCacheHelper.setCache(key, key, Jackson.toJsonStringNonNull(cacheValue));

            //跟新本地缓存版本号
            LocalCacheHelper.setCache(key, VERSION, String.valueOf(stringRedisTemplate.opsForValue().increment(key)));
        }
        return cacheValue;
    }

}
