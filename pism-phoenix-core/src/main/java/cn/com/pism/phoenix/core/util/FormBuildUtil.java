package cn.com.pism.phoenix.core.util;

import cn.com.pism.phoenix.annotations.form.*;
import cn.com.pism.phoenix.annotations.model.*;
import cn.com.pism.phoenix.utils.CollectionExUtil;
import cn.com.pism.phoenix.utils.ObjectExUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * @author perccyking
 * @since 24-06-10 12:14
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class FormBuildUtil {

    private final ApplicationContext applicationContext;

    public FormVo build(Object object) {
        Class<?> objClass = object.getClass();
        FormVo formVo = new FormVo();
        //获取对象上的注解
        Form form = AnnotationUtils.findAnnotation(objClass, Form.class);
        if (form != null) {
            formVo.setLabel(form.label());
            formVo.setOrder(form.order());
            //字段
            for (Field field : objClass.getDeclaredFields()) {
                //检查字段的类型是否有Form注解
                Form filedTypeForm = AnnotationUtils.findAnnotation(field.getType(), Form.class);
                if (filedTypeForm != null) {
                    CollectionExUtil.addToList(formVo, FormVo::getChild, formVo::setChild, build(applicationContext.getBean(field.getType())));
                } else {
                    //获取字段上的注解
                    ObjectExUtil.isNotBlank(AnnotationUtils.getAnnotation(field, FormField.class), formField -> {
                        FormFieldVo formFieldVo = buildFormFieldVo(formField, object, field);
                        //将字段加入到form 的字段中
                        CollectionExUtil.addToList(formVo, FormVo::getFields, formVo::setFields, formFieldVo);
                    });
                }
            }
        }
        return formVo;
    }

    private FormFieldVo buildFormFieldVo(FormField formField, Object object, Field field) {
        FormFieldVo formFieldVo = new FormFieldVo();
        formFieldVo.setKey(formField.key());
        formFieldVo.setLabel(formField.label());
        formFieldVo.setType(formField.type());
        //使用key 填充value
        String property = null;
        try {
            property = BeanUtils.getProperty(object, field.getName());
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            log.warn("Error in getting attribute values and sending when building form field objects", e);
        }
        formFieldVo.setValue(property);
        formFieldVo.setPlaceholder(formField.placeholder());

        formFieldVo.setValidate(buildFormFieldValidateVo(formField.validate()));

        formFieldVo.setCondition(buildConditionVo(formField.condition()));

        Class<? extends FormOptionProvider> provider = formField.optionProvider();
        ObjectExUtil.isNotBlank(provider,
                () -> {
                    FormOptionProvider optionProvider = null;
                    if (!provider.equals(FormOptionProvider.class)) {
                        try {
                            optionProvider = applicationContext.getBean(provider);
                        } catch (BeansException e) {
                            log.trace("FormOptionProvider not defined");
                        }
                    }
                    ObjectExUtil.isNotBlank(optionProvider,
                            formOptionProvider -> formFieldVo.setOptions(formOptionProvider.build()));
                }, () -> {
                    List<FormOptionVo> formOptionVos = new ArrayList<>();
                    FormOption[] options = formField.options();
                    CollectionExUtil.isNotEmptyFor(Arrays.asList(options), option -> {
                        List<FormOptionItemVo> formOptionItemVos = new ArrayList<>();
                        CollectionExUtil.isNotEmptyFor(Arrays.asList(option.items()), item -> formOptionItemVos.add(buildFormOptionItemVo(item)));
                        FormOptionVo formOptionVo = new FormOptionVo();
                        formOptionVo.setItems(formOptionItemVos);
                        formOptionVo.setCondition(buildConditionVo(option.condition()));
                        formOptionVos.add(formOptionVo);
                    });
                    formFieldVo.setOptions(formOptionVos);
                });
        return formFieldVo;
    }

    private static FormOptionItemVo buildFormOptionItemVo(FormOptionItem item) {
        FormOptionItemVo formOptionItemVo = new FormOptionItemVo();
        formOptionItemVo.setLabel(item.label());
        formOptionItemVo.setValue(item.value());
        return formOptionItemVo;
    }

    private static FormFieldValidateVo buildFormFieldValidateVo(FormFieldValidate validate) {
        FormFieldValidateVo formFieldValidateVo = new FormFieldValidateVo();
        if (validate != null) {
            formFieldValidateVo.setRequired(validate.required());
            formFieldValidateVo.setMaxLength(validate.maxLength());
            formFieldValidateVo.setMinLength(validate.minLength());
        }
        return formFieldValidateVo;
    }

    private static FormConditionVo buildConditionVo(FormCondition condition) {
        if (condition != null) {
            FormConditionVo formConditionVo = new FormConditionVo();
            formConditionVo.setValue(condition.value());
            formConditionVo.setField(condition.field());
            return formConditionVo;
        }
        return null;
    }

    public List<FormVo> buildAll(Collection<Object> objects, boolean filter) {
        if (filter) {
            List<String> referenced = new ArrayList<>();
            //先做预解析，找到所有被引用的,只保留未被引用的
            for (Object object : objects) {
                //遍历对象属性
                Field[] fields = object.getClass().getDeclaredFields();
                for (Field field : fields) {
                    if (field.getType().isAnnotationPresent(Form.class)) {
                        referenced.add(field.getType().getName());
                    }
                }
            }
            objects = objects.stream().filter(obj -> !referenced.contains(obj.getClass().getName())).toList();
        }
        return objects.stream().map(this::build).toList();
    }

    public List<FormVo> buildAll(Collection<Object> objects) {
        return buildAll(objects, true);
    }

    public List<FormVo> buildAll() {
        return buildAll(applicationContext.getBeansWithAnnotation(Form.class).values());
    }
}
