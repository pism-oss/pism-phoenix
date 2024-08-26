package cn.com.pism.phoenix.annotations.form.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author perccyking
 * @since 24-06-10 12:32
 */
@Schema(description = "字段验证")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors(chain = true)
public class FormFieldValidateVo {

    /**
     * 是否必填
     */
    @Schema(description = "是否必填")
    @Builder.Default
    private boolean required = false;

    /**
     * 最大长度
     */
    @Schema(description = "最大长度")
    @Builder.Default
    private int maxLength = -1;

    /**
     * 最小长度
     */
    @Schema(description = "最小长度")
    @Builder.Default
    private int minLength = -1;


}
