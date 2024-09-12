package cn.com.pism.phoenix.core.service.impl;

import cn.com.pism.phoenix.core.service.PmnxRsaService;
import cn.com.pism.phoenix.core.util.CacheUtil;
import cn.com.pism.phoenix.models.bo.cas.PmnxRsaBo;
import cn.com.pism.phoenix.utils.Jackson;
import cn.hutool.crypto.asymmetric.RSA;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.concurrent.TimeUnit;

import static cn.com.pism.phoenix.models.constant.PmnxCasConstant.RSA_CACHE_KEY;

/**
 * @author perccyking
 * @since 24-09-13 00:23
 */
@Service
@RequiredArgsConstructor
public class PmnxRsaServiceImpl implements PmnxRsaService {

    private final CacheUtil cacheUtil;

    private final StringRedisTemplate stringRedisTemplate;

    /**
     * <p>
     * 获取或创建rsa
     * </p>
     * by perccyking
     *
     * @param keyId : 密钥id
     * @return {@link PmnxRsaBo} rsa
     * @since 24-09-13 00:24
     */
    @Override
    public PmnxRsaBo getOrCreateRsaBo(String keyId) {
        return cacheUtil.getOrUpdate(RSA_CACHE_KEY + keyId, () -> {
            RSA rsa = new RSA();
            return new PmnxRsaBo(rsa.getPublicKeyBase64(), rsa.getPrivateKeyBase64());
        }, 60, TimeUnit.SECONDS, new TypeReference<>() {
            @Override
            public Type getType() {
                return PmnxRsaBo.class;
            }
        });
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
        String rsaCacheValue = stringRedisTemplate.opsForValue().get(RSA_CACHE_KEY + keyId);
        PmnxRsaBo pmnxRsaBo = Jackson.parseObject(rsaCacheValue, PmnxRsaBo.class);
        return new RSA(pmnxRsaBo.getPublicKey(), pmnxRsaBo.getPrivateKey());
    }
}
