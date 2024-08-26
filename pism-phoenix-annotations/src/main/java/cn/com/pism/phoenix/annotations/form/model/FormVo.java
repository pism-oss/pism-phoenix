package cn.com.pism.phoenix.annotations.form.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author perccyking
 * @since 24-06-10 12:26
 */
@Schema(description = "表单结构")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors(chain = true)
public class FormVo {

    /**
     * 表单名称
     */
    @Schema(description = "表单名称")
    private String label;

    /**
     * 表单字段
     */
    @Schema(description = "表单字段")
    private List<FormFieldVo> fields;

    /**
     * 表单排序
     */
    @Schema(description = "表单排序")
    private int order;

    /**
     * 子表单
     */
    @Schema(description = "子表单")
    private List<FormVo> child;

}
