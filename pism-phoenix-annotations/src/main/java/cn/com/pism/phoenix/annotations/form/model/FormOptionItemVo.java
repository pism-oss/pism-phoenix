package cn.com.pism.phoenix.annotations.form.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author perccyking
 * @since 24-06-10 11:49
 */
@Schema(description = "表单选项列表")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors(chain = true)
public class FormOptionItemVo {

    /**
     * 选项名称
     */
    @Schema(description = "选项名称")
    private String label;

    /**
     * 选项值
     */
    @Schema(description = "选项值")
    private String value;

}
