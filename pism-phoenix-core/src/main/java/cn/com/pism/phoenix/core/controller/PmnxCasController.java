package cn.com.pism.phoenix.core.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @Operation(summary = "用户名密码登录")
    @PostMapping("/login")
    public Object login() {
        return "";
    }
}
