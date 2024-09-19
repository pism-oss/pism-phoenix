package cn.com.pism.phoenix.core.controller;

import cn.com.pism.phoenix.annotations.form.model.FormVo;
import cn.com.pism.phoenix.core.service.PmnxFormService;
import cn.com.pism.phoenix.core.txa.PmnxFormTxa;
import cn.com.pism.phoenix.core.util.FormBuildUtil;
import cn.com.pism.phoenix.core.util.SpringUtil;
import cn.com.pism.phoenix.models.exception.BizException;
import cn.com.pism.phoenix.models.vo.form.req.PmnxFormSaveValueReqVo;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * <ul>
 *     <li>获取所有表单</li>
 *     <li>根据表单id获取表单信息</li>
 *     <li>保存表单数据</li>
 * </ul>
 *
 * @author perccyking
 * @since 24-06-10 23:12
 */
@RestController
@RequestMapping("/sys/form")
@Tag(name = "表单")
@ApiSupport
@RequiredArgsConstructor
@Log4j2
public class PmnxFormController {

    private final FormBuildUtil formBuildUtil;

    private final SpringUtil springUtil;

    private final PmnxFormService pmnxFormService;

    private final PmnxFormTxa pmnxFormTxa;

    @Operation(summary = "获取所有表单")
    @GetMapping("/all")
    public List<FormVo> all() {
        return formBuildUtil.buildAll();
    }

    @Operation(summary = "使用表单id获取表单信息")
    @GetMapping("/by/{id}")
    public FormVo byId(@Parameter(description = "表单id") @PathVariable("id") String id) {
        try {
            return formBuildUtil.build(springUtil.getBean(Class.forName(id)));
        } catch (ClassNotFoundException e) {
            log.error(e.getMessage(), e);
            throw new BizException("表单信息获取失败");
        }
    }

    @Operation(summary = "保存表单数据")
    @PostMapping("/save")
    public void save(@RequestBody PmnxFormSaveValueReqVo reqVo) {
        if (StringUtils.isBlank(reqVo.getFormId())) {
            throw new BizException("表单数据无法解析");
        }
        //保存数据
        pmnxFormTxa.save(reqVo);
        CompletableFuture.runAsync(() -> {

            //标记表单更新
            pmnxFormService.markUpdate(reqVo.getFormId());

            //更新当前服务节点的表单数据
            try {
                pmnxFormService.refreshForm(reqVo.getFormId(), springUtil.getBean(ClassUtils.getClass(reqVo.getFormId())));
            } catch (ClassNotFoundException e) {
                log.error(e.getMessage(), e);
            }
        });
    }


}
