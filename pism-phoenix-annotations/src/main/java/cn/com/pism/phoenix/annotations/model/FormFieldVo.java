package cn.com.pism.phoenix.annotations.model;

import cn.com.pism.phoenix.annotations.form.FormFieldType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author perccyking
 * @since 24-06-10 12:27
 */
@Schema(description = "表单字段")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors(chain = true)
public class FormFieldVo {

    /**
     * 字段名称
     */
    @Schema(description = "字段名称")
    private String label;

    /**
     * 字段类型
     */
    @Schema(description = "字段类型")
    private FormFieldType type;

    /**
     * 字段key
     */
    @Schema(description = "字段key")
    private String key;

    /**
     * 占位符
     */
    @Schema(description = "占位符")
    private String placeholder;

    /**
     * 值
     */
    @Schema(description = "值")
    private String value;

    /**
     * 字段验证
     */
    @Schema(description = "字段验证")
    private FormFieldValidateVo validate;

    /**
     * 字段显示条件
     */
    @Schema(description = "字段显示条件")
    private FormConditionVo condition;

    /**
     * 选项
     */
    @Schema(description = "选项")
    private List<FormOptionVo> options;

}
