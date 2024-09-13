package cn.com.pism.phoenix.core.controller;

import cn.com.pism.exception.PismException;
import cn.com.pism.phoenix.annotations.env.Env;
import cn.com.pism.phoenix.annotations.env.EnvEnum;
import cn.com.pism.phoenix.core.config.PmnxProperties;
import cn.com.pism.phoenix.core.service.PmnxCasService;
import cn.com.pism.phoenix.models.exception.UsernameOrPasswordErrorException;
import cn.com.pism.phoenix.models.vo.cas.req.PmnxCasPasswordLoginReqVo;
import cn.com.pism.phoenix.models.vo.cas.resp.PmnxCasLoginRespVo;
import cn.dev33.satoken.annotation.SaIgnore;
import cn.dev33.satoken.stp.StpUtil;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

/**
 * @author perccyking
 * @since 24-08-26 17:19
 */
@RestController
@RequestMapping("/open/cas")
@Tag(name = "认证中心")
@ApiSupport
@RequiredArgsConstructor
@Log4j2
public class PmnxCasController {

    private final PmnxCasService pmnxCasService;
    private final PmnxProperties pmnxProperties;

    @Operation(summary = "获取公钥")
    @GetMapping("/public/{keyId}/key")
    @SaIgnore
    public String getPublicKey(@Parameter(description = "密钥id") @PathVariable("keyId") String keyId) {
        return pmnxCasService.getPublicKey(keyId);
    }


    @Operation(summary = "用户名密码登录")
    @PostMapping("/login")
    @SaIgnore
    public PmnxCasLoginRespVo login(@RequestBody PmnxCasPasswordLoginReqVo reqVo) {
        try {
            return pmnxCasService.login(reqVo);
        } catch (Exception e) {
            if (!(e instanceof PismException)) {
                throw new UsernameOrPasswordErrorException(e);
            }
            throw e;
        }
    }

    @Operation(summary = "退出登录")
    @GetMapping("/logout")
    public void logout() {
        StpUtil.logout();
    }


    @Operation(summary = "使用公钥加密文本")
    @GetMapping("/rsa/encrypt/public")
    @SaIgnore
    @Env({EnvEnum.DEV, EnvEnum.TEST})
    public String rsaEncryptPublic(@Parameter(description = "待加密文本") @RequestParam("text") String text,
                                   @Parameter(description = "密钥id") @RequestParam("keyId") String keyId) {
        return pmnxCasService.rsaEncryptPublic(text, keyId);
    }
}
