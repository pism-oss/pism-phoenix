package cn.com.pism.phoenix.core.service;

import cn.hutool.crypto.asymmetric.RSA;

/**
 * @author perccyking
 * @since 24-09-13 00:23
 */
public interface PmnxRsaService {
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
    RSA getOrCreateRsaBo(String keyId);

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
    RSA getOrCreateRsaBo(String keyId, long expire);

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
    RSA getRsaByKeyId(String keyId);
}
