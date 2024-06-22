package cn.com.pism.phoenix.annotations.form;

import java.lang.annotation.*;

/**
 * 表单字段标记
 *
 * @author perccyking
 * @since 24-06-07 16:00
 */
@Documented
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface FormField {

    /**
     * 字段key 全局唯一
     *
     * @return 表单字段key
     */
    String key() default "";

    /**
     * label
     *
     * @return label
     */
    String label() default "";

    /**
     * 表单字段类型
     *
     * @return 字段类型
     */
    FormFieldType type() default FormFieldType.INPUT;

    /**
     * 占位符
     *
     * @return 输入框等组件中的占位符
     */
    String placeholder() default "";

    /**
     * 字段验证
     *
     * @return validate
     */
    FormFieldValidate validate() default @FormFieldValidate;

    /**
     * 字段展示条件
     *
     * @return 条件
     */
    FormCondition condition() default @FormCondition;

    /**
     * 选项
     *
     * @return 选项
     */
    FormOption[] options() default {};


    /**
     * 提供选项数据，provider 优先级高于 options
     *
     * @return 选项数据provider
     */
    Class<? extends FormOptionProvider> optionProvider() default FormOptionProvider.class;

}
