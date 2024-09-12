package cn.com.pism.phoenix.core.service;

import cn.com.pism.phoenix.models.vo.cas.req.PmnxCasPasswordLoginReqVo;
import cn.com.pism.phoenix.models.vo.cas.resp.PmnxCasLoginRespVo;

/**
 * @author perccyking
 * @since 24-09-12 15:50
 */
public interface PmnxCasService {

    /**
     * <p>
     * 用户名密码登录
     * </p>
     * by perccyking
     *
     * @param reqVo : 请求参数
     * @return {@link PmnxCasLoginRespVo} 登录结果
     * @since 24-09-12 15:51
     */
    PmnxCasLoginRespVo login(PmnxCasPasswordLoginReqVo reqVo);

    /**
     * <p>
     * 获取公钥
     * </p>
     * by perccyking
     *
     * @param keyId : 密钥id
     * @return {@link String} 公钥
     * @since 24-09-12 15:56
     */
    String getPublicKey(String keyId);
}
