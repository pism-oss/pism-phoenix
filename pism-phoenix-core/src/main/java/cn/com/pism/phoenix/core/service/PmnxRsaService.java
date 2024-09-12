package cn.com.pism.phoenix.core.service;

import cn.com.pism.phoenix.models.bo.cas.PmnxRsaBo;
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
     * @return {@link PmnxRsaBo} rsa
     * @since 24-09-13 00:24
     */
    PmnxRsaBo getOrCreateRsaBo(String keyId);

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
