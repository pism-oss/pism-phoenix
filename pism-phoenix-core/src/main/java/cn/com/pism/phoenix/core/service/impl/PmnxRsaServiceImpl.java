package cn.com.pism.phoenix.core.service.impl;

import cn.com.pism.phoenix.core.service.PmnxRsaService;
import cn.com.pism.phoenix.models.bo.cas.PmnxRsaBo;
import cn.com.pism.phoenix.utils.Jackson;
import cn.hutool.crypto.asymmetric.RSA;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.concurrent.TimeUnit;

import static cn.com.pism.phoenix.models.constant.PmnxCasConstants.*;

/**
 * @author perccyking
 * @since 24-09-13 00:23
 */
@Service
@RequiredArgsConstructor
public class PmnxRsaServiceImpl implements PmnxRsaService {

    private final StringRedisTemplate stringRedisTemplate;

    /**
     * <p>
     * 获取或创建rsa
     * </p>
     * by perccyking
     *
     * @param keyId : 密钥id
     * @return {@link RSA} rsa
     * @since 24-09-13 00:24
     */
    @Override
    public RSA getOrCreateRsa(String keyId) {
        return getOrCreateRsa(keyId, RSA_POOL_EXPIRE_SECONDS);
    }

    /**
     * <p>
     * 获取或创建rsa
     * </p>
     * by perccyking
     *
     * @param keyId  : 密钥id
     * @param expire : 过期时间，单位s
     * @return {@link RSA} rsa
     * @since 24-09-13 12:53
     */
    @Override
    public RSA getOrCreateRsa(String keyId, long expire) {

        // keyId 检查是否被使用，至少保留一个小时

        String cacheKey = getPoolCacheKey(keyId);
        String rsaCacheValue = stringRedisTemplate.opsForValue().get(cacheKey);
        if (StringUtils.isNotBlank(rsaCacheValue)) {
            PmnxRsaBo cached = Jackson.parseObject(rsaCacheValue, PmnxRsaBo.class);
            if (cached != null && StringUtils.isNotBlank(cached.getPrivateKey()) && StringUtils.isNotBlank(cached.getPublicKey())) {
                stringRedisTemplate.expire(cacheKey, expire, TimeUnit.SECONDS);
                return new RSA(cached.getPrivateKey(), cached.getPublicKey());
            }
            stringRedisTemplate.delete(cacheKey);
        }

        RSA rsa = new RSA();
        PmnxRsaBo pmnxRsaBo = new PmnxRsaBo(rsa.getPublicKeyBase64(), rsa.getPrivateKeyBase64());
        stringRedisTemplate.opsForValue().set(cacheKey, Jackson.toJsonStringNonNull(pmnxRsaBo), expire, TimeUnit.SECONDS);
        return new RSA(pmnxRsaBo.getPrivateKey(), pmnxRsaBo.getPublicKey());
    }

    /**
     * <p>
     * 通过密钥id获取rsa对象
     * </p>
     * by perccyking
     *
     * @param keyId : 密钥id
     * @return {@link RSA} rsa
     * @since 24-09-13 00:28
     */
    @Override
    public RSA getRsaByKeyId(String keyId) {
        String cacheKey = getPoolCacheKey(keyId);
        String rsaCacheValue = stringRedisTemplate.opsForValue().get(cacheKey);
        PmnxRsaBo pmnxRsaBo = Jackson.parseObject(rsaCacheValue, PmnxRsaBo.class);
        if (pmnxRsaBo == null) {
            return getOrCreateRsa(keyId, RSA_POOL_EXPIRE_SECONDS);
        }
        return new RSA(pmnxRsaBo.getPrivateKey(), pmnxRsaBo.getPublicKey());
    }

    private String getPoolCacheKey(String keyId) {
        int index = getPoolIndex(keyId);
        return RSA_POOL_KEY + index;
    }


    private int getPoolIndex(String keyId) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(keyId.getBytes(StandardCharsets.UTF_8));
            int value = ByteBuffer.wrap(hash, 0, 4).getInt();
            return Math.floorMod(value, RSA_POOL_SIZE);
        } catch (Exception e) {
            return Math.floorMod(keyId.hashCode(), RSA_POOL_SIZE);
        }
    }
}
