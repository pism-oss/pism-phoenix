package cn.com.pism.phoenix.core.util;

import cn.com.pism.phoenix.utils.Jackson;
import cn.com.pism.phoenix.utils.ObjectExUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * @author perccyking
 * @since 24-09-09 14:53
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class CacheUtil {

    public final StringRedisTemplate stringRedisTemplate;

    /**
     * <p>
     * 获取或更新缓存
     * </p>
     * by perccyking
     *
     * @param key           : 缓存key
     * @param getSupplier   : 缓存未命中
     * @param timeout       : 过期时间
     * @param timeUnit      : 过期时间单位
     * @param typeReference : 类型
     * @return {@link T} 缓存值
     * @since 24-09-09 15:22
     */
    public <T> T getOrUpdate(String key, Supplier<T> getSupplier, long timeout, TimeUnit timeUnit, TypeReference<T> typeReference) {

        // 从Redis获取缓存值
        String cacheValue = stringRedisTemplate.opsForValue().get(key);

        // 如果缓存值存在且不为空
        if (StringUtils.isNotBlank(cacheValue)) {
            try {

                // 反序列化缓存值并返回
                return Jackson.instance().readValue(cacheValue, typeReference);
            } catch (JsonProcessingException e) {

                // 处理反序列化失败的情况
                stringRedisTemplate.delete(key);
            }
        }

        // 如果缓存值不存在或反序列化失败，使用Supplier获取值
        T value = getSupplier.get();

        // 如果获取到的值不为空，将其存储到缓存中
        if (ObjectExUtil.isNotBlank(value)) {

            // 将值序列化为JSON并存储到Redis
            stringRedisTemplate.opsForValue().set(key, Jackson.toJsonStringNonNull(value), timeout, timeUnit);
        } else {
            log.warn("Supplier returned null for key: {}", key);
        }

        return value;
    }

    /**
     * <p>
     * 获取或更新缓存
     * </p>
     * by perccyking
     *
     * @param key           : 缓存key
     * @param getSupplier   : 缓存未命中
     * @param timeout       : 过期时间 毫秒
     * @param typeReference : 类型
     * @return {@link T}
     * @since 24-09-09 15:24
     */
    public <T> T getOrUpdate(String key, Supplier<T> getSupplier, long timeout, TypeReference<T> typeReference) {
        return getOrUpdate(key, getSupplier, timeout, TimeUnit.MILLISECONDS, typeReference);
    }

    /**
     * <p>
     * 获取或更新缓存
     * </p>
     * by perccyking
     *
     * @param key           : 缓存key
     * @param getSupplier   : 缓存未命中
     * @param typeReference : 类型
     * @return {@link T}
     * @since 24-09-09 15:27
     */
    public <T> T getOrUpdate(String key, Supplier<T> getSupplier, TypeReference<T> typeReference) {
        return getOrUpdate(key, getSupplier, -1L, typeReference);
    }
}
