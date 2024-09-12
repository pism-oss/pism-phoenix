package cn.com.pism.phoenix.core.service.impl;

import cn.com.pism.exception.PismException;
import cn.com.pism.phoenix.core.entity.PmnxUser;
import cn.com.pism.phoenix.core.service.PmnxCasService;
import cn.com.pism.phoenix.core.service.PmnxRsaService;
import cn.com.pism.phoenix.core.service.PmnxUserService;
import cn.com.pism.phoenix.models.vo.cas.PmnxCasTokenVo;
import cn.com.pism.phoenix.models.vo.cas.req.PmnxCasPasswordLoginReqVo;
import cn.com.pism.phoenix.models.vo.cas.resp.PmnxCasLoginRespVo;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import cn.hutool.crypto.digest.BCrypt;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static cn.com.pism.phoenix.core.config.PmnxErrorCode.USERNAME_OR_PASSWORD_ERROR;

/**
 * @author perccyking
 * @since 24-09-12 15:50
 */
@Service
@RequiredArgsConstructor
public class PmnxCasServiceImpl implements PmnxCasService {

    private final PmnxRsaService pmnxRsaService;

    private final PmnxUserService pmnxUserService;

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
    @Override
    public PmnxCasLoginRespVo login(PmnxCasPasswordLoginReqVo reqVo) {

        RSA rsa = pmnxRsaService.getRsaByKeyId(reqVo.getKeyId());

        String decryptAccount = rsa.decryptStr(reqVo.getAccount(), KeyType.PrivateKey);
        String decryptPassword = rsa.decryptStr(reqVo.getPassword(), KeyType.PublicKey);
        //通过账号获取密码
        String password = pmnxUserService.getPasswordByAccount(decryptAccount);
        if (!BCrypt.checkpw(decryptPassword, password)) {
            throw new PismException(USERNAME_OR_PASSWORD_ERROR);
        }

        return doLogin(pmnxUserService.lambdaQuery().eq(PmnxUser::getAccount, decryptAccount).one());
    }

    private PmnxCasLoginRespVo doLogin(PmnxUser pmnxUser) {
        //登录
        StpUtil.login(pmnxUser.getId());
        SaTokenInfo tokenInfo = StpUtil.getTokenInfo();

        PmnxCasLoginRespVo loginRespVo = new PmnxCasLoginRespVo();
        loginRespVo.setAccount(pmnxUser.getAccount());
        loginRespVo.setEmail(pmnxUser.getEmail());

        PmnxCasTokenVo tokenVo = new PmnxCasTokenVo();
        tokenVo.setTokenName(tokenInfo.getTokenName());
        tokenVo.setToken(tokenInfo.getTokenValue());
        tokenVo.setTimeout(tokenInfo.getTokenTimeout());
        loginRespVo.setToken(tokenVo);
        return loginRespVo;
    }

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
    @Override
    public String getPublicKey(String keyId) {
        return pmnxRsaService.getOrCreateRsaBo(keyId).getPublicKey();
    }
}
