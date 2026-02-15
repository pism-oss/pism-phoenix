package cn.com.pism.phoenix.core.controller;

import cn.com.pism.phoenix.core.service.PmnxUserService;
import cn.com.pism.phoenix.models.vo.page.PageReqVo;
import cn.com.pism.phoenix.models.vo.page.PageRespVo;
import cn.com.pism.phoenix.models.vo.user.req.PmnxUserDeleteReqVo;
import cn.com.pism.phoenix.models.vo.user.req.PmnxUserPageReqVo;
import cn.com.pism.phoenix.models.vo.user.req.PmnxUserResetPasswordReqVo;
import cn.com.pism.phoenix.models.vo.user.req.PmnxUserSaveReqVo;
import cn.com.pism.phoenix.models.vo.user.resp.PmnxUserPageRespVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

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
@RequiredArgsConstructor
@Log4j2
public class PmnxUserController {

    private final PmnxUserService pmnxUserService;

    @Operation(description = "用户列表-分页")
    @PostMapping("/page")
    public PageRespVo<PmnxUserPageRespVo> page(@RequestBody PageReqVo<PmnxUserPageReqVo> reqVo) {
        return PageRespVo.of(pmnxUserService.page(reqVo));
    }


    @Operation(description = "创建用户")
    @PostMapping
    public void create(@RequestBody PmnxUserSaveReqVo reqVo) {
        pmnxUserService.save(reqVo);
    }

    @Operation(description = "更新用户")
    @PutMapping("/{id}")
    public void update(@PathVariable Long id, @RequestBody PmnxUserSaveReqVo reqVo) {
        reqVo.setId(id);
        pmnxUserService.save(reqVo);
    }

    @Operation(description = "删除用户")
    @DeleteMapping
    public void delete(@RequestBody PmnxUserDeleteReqVo reqVo) {
        pmnxUserService.delete(reqVo);
    }

    @Operation(description = "重置用户密码")
    @PatchMapping("/password")
    public void resetPassword(@RequestBody PmnxUserResetPasswordReqVo reqVo) {
        pmnxUserService.resetPassword(reqVo);
    }

}
