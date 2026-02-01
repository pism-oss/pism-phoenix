package cn.com.pism.phoenix.core.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author perccyking
 * @since 24-06-22 18:43
 */
@RestController
@RequestMapping("/sys/role")
@Tag(name = "角色管理")
@RequiredArgsConstructor
@Log4j2
public class PmnxRoleController {

}
