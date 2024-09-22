package cn.com.pism.phoenix.core.controller;

import cn.com.pism.phoenix.core.service.PmnxUserService;
import cn.com.pism.phoenix.models.vo.page.PageReqVo;
import cn.com.pism.phoenix.models.vo.page.PageRespVo;
import cn.com.pism.phoenix.models.vo.user.req.PmnxUserPageReqVo;
import cn.com.pism.phoenix.models.vo.user.resp.PmnxUserPageRespVo;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <ul>
 *     <li>用户列表查询</li>
 *     <li>用户保存</li>
 *     <li>用户删除</li>
 *     <li>重置密码</li>
 * </ul>
 *
 * @author perccyking
 * @since 24-06-22 18:43
 */
@RestController
@RequestMapping("/sys/user")
@Tag(name = "用户管理")
@ApiSupport
@RequiredArgsConstructor
@Log4j2
public class PmnxUserController {

    private final PmnxUserService pmnxUserService;

    @Operation(description = "用户列表-分页")
    @PostMapping("/page")
    public PageRespVo<PmnxUserPageRespVo> page(@RequestBody PageReqVo<PmnxUserPageReqVo> reqVo) {
        return PageRespVo.of(pmnxUserService.page(reqVo));
    }

}
