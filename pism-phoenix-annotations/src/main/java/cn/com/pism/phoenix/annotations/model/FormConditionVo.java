package cn.com.pism.phoenix.annotations.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author perccyking
 * @since 24-06-10 11:50
 */
@Schema(description = "表单条件")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors(chain = true)
public class FormConditionVo {

    /**
     * 字段key
     */
    @Schema(description = "字段key")
    private String field;

    /**
     * 期望值
     */
    @Schema(description = "期望值")
    private String value;

}
