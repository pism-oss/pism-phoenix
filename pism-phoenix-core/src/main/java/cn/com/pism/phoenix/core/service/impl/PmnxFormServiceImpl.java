package cn.com.pism.phoenix.core.service.impl;

import cn.com.pism.mybatis.core.service.impl.ComServiceImpl;
import cn.com.pism.phoenix.annotations.form.Form;
import cn.com.pism.phoenix.core.entity.PmnxForm;
import cn.com.pism.phoenix.core.mapper.PmnxFormMapper;
import cn.com.pism.phoenix.core.service.PmnxFormService;
import cn.com.pism.phoenix.core.util.SpringUtil;
import cn.com.pism.phoenix.utils.Jackson;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static cn.com.pism.phoenix.models.constant.PmnxFormConstants.FORM_VERSION_MARK_KEY;

/**
 * @author perccyking
 * @since 24-09-13 20:13
 */
@Service
@RequiredArgsConstructor
public class PmnxFormServiceImpl extends ComServiceImpl<PmnxFormMapper, PmnxForm> implements PmnxFormService {

    private final SpringUtil springUtil;

    private final StringRedisTemplate stringRedisTemplate;

    /**
     * <p>
     * 刷新表单
     * </p>
     * by perccyking
     *
     * @since 24-09-18 15:39
     */
    @Override
    public void refreshForm() {

        //获取所有定义好的表单
        Map<String, Object> formBeans = springUtil.getBeans(Form.class);
        if (!CollectionUtils.isEmpty(formBeans)) {

            //表单id-表单对象
            Map<String, Object> formBeanMap = formBeans.values().stream().collect(Collectors.toMap(k -> k.getClass().getName(), v -> v));

            //有定义的表单，获取配置数据
            //表单id-表单字段
            Map<String, List<PmnxForm>> formValuesGroup = getFormValuesGroup();
            if (!CollectionUtils.isEmpty(formValuesGroup)) {

                formBeanMap.forEach((formId, form) -> parseForm(form, formValuesGroup.get(formId)));
            }
        }
    }

    /**
     * <p>
     * 标记表单更新
     * </p>
     * by perccyking
     *
     * @param formId : 表单id
     * @since 24-09-19 22:35
     */
    @Override
    public void markUpdate(String formId) {
        stringRedisTemplate.opsForValue().increment(FORM_VERSION_MARK_KEY + formId, 1);
    }

    /**
     * <p>
     * 刷新表单数据
     * </p>
     * by perccyking
     *
     * @param formId :表单id
     * @param form   : 表单对象
     * @since 24-09-19 22:46
     */
    @Override
    public void refreshForm(String formId, Object form) {
        //获取表单数据
        List<PmnxForm> pmnxForms = lambdaQuery().eq(PmnxForm::getFormId, formId).list();
        parseForm(form, pmnxForms);
    }

    /**
     * <p>
     * 批量刷新表单
     * </p>
     * by perccyking
     *
     * @param forms : 表单
     * @since 24-09-19 23:38
     */
    @Override
    public void batchRefreshForm(List<Object> forms) {

        Map<String, Object> formMap = forms.stream().collect(Collectors.toMap(k -> k.getClass().getName(), v -> v));
        Set<String> formIds = formMap.keySet();

        Map<String, List<PmnxForm>> formValuesGroup = getFromValuesGroupByFormIds(formIds);

        formMap.forEach((formId, form) -> parseForm(form, formValuesGroup.get(formId)));

    }

    private Map<String, List<PmnxForm>> getFromValuesGroupByFormIds(Set<String> formIds) {
        Map<String, List<PmnxForm>> formValuesGroup = HashMap.newHashMap(0);
        if (!CollectionUtils.isEmpty(formIds)) {
            try {
                formValuesGroup = lambdaQuery()
                        .in(formIds.size() > 1, PmnxForm::getFormId, formIds)
                        .eq(formIds.size() == 1, PmnxForm::getFormId, IterableUtils.get(formIds, 0))
                        .list()
                        .stream()
                        .collect(Collectors.groupingBy(PmnxForm::getFormId));
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
        return formValuesGroup;
    }

    /**
     * <p>
     * 解析表单对象
     * </p>
     * by perccyking
     *
     * @param form       : 表单对象
     * @param formValues : 表单数据
     * @since 24-09-18 17:55
     */
    private void parseForm(Object form, List<PmnxForm> formValues) {
        //获取表单对应的数据
        if (!CollectionUtils.isEmpty(formValues)) {

            //字段id 字段值
            Map<String, PmnxForm> formFieldValueMap = formValues.stream().collect(Collectors.toMap(PmnxForm::getFieldId, v -> v));

            //表单字段 属性
            Field[] formFields = form.getClass().getDeclaredFields();

            //遍历表单字段
            for (Field formField : formFields) {

                //获取字段对应值
                PmnxForm pmnxForm = formFieldValueMap.get(formField.getName());

                //将属性值填充到属性中
                populateValueToField(form, formField, pmnxForm);

            }
        }
    }


    /**
     * <p>
     * 将属性值填充到表单对象的属性中
     * </p>
     * by perccyking
     *
     * @param form      : 表单对象
     * @param formField : 表单对象属性
     * @param pmnxForm  : 表单值属性对应值
     * @since 24-09-18 17:49
     */
    private void populateValueToField(Object form, Field formField, PmnxForm pmnxForm) {

        //判断表单属性值是否存在
        if (pmnxForm != null && StringUtils.isNotBlank(pmnxForm.getFieldValue())) {

            //填充属性值
            doPopulateValueToField(form, formField, pmnxForm);
        }
    }

    /**
     * <p>
     * 将属性值填充到表单对象的属性中
     * </p>
     * by perccyking
     *
     * @param form      : 表单对象
     * @param formField : 表单对象属性
     * @param pmnxForm  : 表单值属性对应值
     * @since 24-09-18 17:49
     */
    private void doPopulateValueToField(Object form, Field formField, PmnxForm pmnxForm) {
        //获取属性描述
        PropertyDescriptor propertyDescriptor = BeanUtils.getPropertyDescriptor(form.getClass(), formField.getName());
        if (propertyDescriptor != null) {
            try {
                //设置属性值
                propertyDescriptor.getWriteMethod().invoke(form, Jackson.parseObject(pmnxForm.getFieldValue(), formField.getType()));
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
    }

    /**
     * <p>
     * 获取表单数据分组
     * </p>
     * by perccyking
     *
     * @return 表单id-表单数据
     * @since 24-09-18 17:32
     */
    private Map<String, List<PmnxForm>> getFormValuesGroup() {
        Map<String, List<PmnxForm>> formGroup = new HashMap<>();
        try {
            formGroup = list().stream().collect(Collectors.groupingBy(PmnxForm::getFormId));
        } catch (Exception e) {
            log.error("not found form value");
        }
        return formGroup;
    }
}
