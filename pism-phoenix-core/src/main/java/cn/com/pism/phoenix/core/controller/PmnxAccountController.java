package cn.com.pism.phoenix.core.controller;

import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author perccyking
 * @since 24-09-20 23:12
 */
@RestController
@RequestMapping("/client/account")
@Tag(name = "账号")
@ApiSupport
@RequiredArgsConstructor
@Log4j2
public class PmnxAccountController {

}
