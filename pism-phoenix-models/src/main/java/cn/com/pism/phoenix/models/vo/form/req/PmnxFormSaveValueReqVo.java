package cn.com.pism.phoenix.models.vo.form.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * @author perccyking
 * @since 24-09-18 00:59
 */
@Data
@Schema(description = "表单数据保存接口")
public class PmnxFormSaveValueReqVo {

    /**
     * 表单id
     */
    @Schema(description = "表单id")
    private String formId;

    /**
     * 字段值
     */
    @Schema(description = "字段值")
    List<PmnxFormSaveFieldValueReqVo> fieldValues;
}
