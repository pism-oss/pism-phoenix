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
 * @since 24-06-10 11:44
 */
@Schema(description = "表单选项")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors(chain = true)
public class FormOptionVo {

    /**
     * 选项列表
     */
    @Schema(description = "选项列表")
    List<FormOptionItemVo> items;

    /**
     * 限制条件
     */
    @Schema(description = "限制条件")
    FormConditionVo condition;

}
