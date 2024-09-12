package cn.com.pism.phoenix.core.controller;

import cn.com.pism.exception.PismException;
import cn.com.pism.phoenix.core.service.PmnxCasService;
import cn.com.pism.phoenix.models.vo.cas.req.PmnxCasPasswordLoginReqVo;
import cn.com.pism.phoenix.models.vo.cas.resp.PmnxCasLoginRespVo;
import cn.dev33.satoken.annotation.SaIgnore;
import cn.dev33.satoken.stp.StpUtil;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static cn.com.pism.phoenix.core.config.PmnxErrorCode.USERNAME_OR_PASSWORD_ERROR;

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

    @Operation(summary = "获取公钥")
    @GetMapping("/public/{id}/key")
    @SaIgnore
    public String getPublicKey(@PathVariable("id") String id) {
        return pmnxCasService.getPublicKey(id);
    }


    @Operation(summary = "用户名密码登录")
    @PostMapping("/login")
    @SaIgnore
    public PmnxCasLoginRespVo login(@Validated PmnxCasPasswordLoginReqVo reqVo) {
        try {
            return pmnxCasService.login(reqVo);
        } catch (Exception e) {
            if (!(e instanceof PismException)) {
                throw new PismException(USERNAME_OR_PASSWORD_ERROR);
            }
            throw e;
        }
    }

    @Operation(summary = "退出登录")
    @GetMapping("/logout")
    public void logout() {
        StpUtil.logout();
    }
}
