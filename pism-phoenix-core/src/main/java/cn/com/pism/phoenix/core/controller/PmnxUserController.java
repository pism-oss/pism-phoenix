package cn.com.pism.phoenix.core.controller;

import cn.com.pism.mybatis.core.annotation.EntityFill;
import cn.com.pism.phoenix.core.service.PmnxUserService;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author perccyking
 * @since 24-06-22 18:43
 */
@RestController
@RequestMapping("/sys/user")
@Tag(name = "用户")
@ApiSupport
@RequiredArgsConstructor
@Log4j2
public class PmnxUserController {

    private final PmnxUserService userService;

    @GetMapping("/123")
    @EntityFill
    public void aa() {

    }
}
