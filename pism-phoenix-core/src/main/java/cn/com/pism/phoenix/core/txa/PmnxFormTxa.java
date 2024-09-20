package cn.com.pism.phoenix.core.txa;

import cn.com.pism.phoenix.core.common.ComEntity;
import cn.com.pism.phoenix.core.entity.PmnxForm;
import cn.com.pism.phoenix.core.service.PmnxFormService;
import cn.com.pism.phoenix.models.vo.form.req.PmnxFormSaveValueReqVo;
import cn.com.pism.phoenix.utils.Jackson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author perccyking
 * @since 24-09-18 16:09
 */
@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
@Slf4j
public class PmnxFormTxa {

    private final PmnxFormService pmnxFormService;

    /**
     * <p>
     * 保存表单数据
     * </p>
     * by perccyking
     *
     * @param reqVo : 请求参数
     * @since 24-09-18 16:11
     */
    public void save(PmnxFormSaveValueReqVo reqVo) {

        if (CollectionUtils.isNotEmpty(reqVo.getFieldValues())) {

            //需要更新的
            List<PmnxForm> updates = new ArrayList<>();

            //新增的
            List<PmnxForm> adds = new ArrayList<>();

            //查询当前表单所有的配置
            Map<String, PmnxForm> pmnxFormMap = pmnxFormService
                    .lambdaQuery()
                    .eq(PmnxForm::getFormId, reqVo.getFormId())
                    .select(ComEntity::getId, PmnxForm::getFormId, PmnxForm::getFieldId, PmnxForm::getFieldValue)
                    .list()
                    .stream()
                    .collect(Collectors.toMap(PmnxForm::getFieldId, v -> v));

            reqVo.getFieldValues().forEach(v -> {
                PmnxForm pmnxForm = pmnxFormMap.get(v.getId());
                if (pmnxForm != null) {
                    pmnxForm.setFieldValue(Jackson.toJsonString(v.getValue()));
                    updates.add(pmnxForm);
                } else {
                    pmnxForm = new PmnxForm();
                    pmnxForm.setFieldId(reqVo.getFormId());
                    pmnxForm.setFormId(v.getId());
                    pmnxForm.setFieldValue(Jackson.toJsonString(v.getValue()));
                    adds.add(pmnxForm);
                }
            });


            pmnxFormService.batchUpdate(updates);
            pmnxFormService.batchInsert(adds);
        }
    }
}
