package cn.com.pism.phoenix.models.vo.form.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author perccyking
 * @since 24-09-18 01:01
 */
@Data
@Schema(description = "表单字段数据保存请求参数")
public class PmnxFormSaveFieldValueReqVo {

    /**
     * 字段id
     */
    @Schema(description = "字段id")
    private String id;

    /**
     * 字段值
     */
    @Schema(description = "字段值")
    private Object value;

}
