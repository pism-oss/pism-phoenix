package cn.com.pism.phoenix.models.vo.user.req;

import cn.com.pism.phoenix.models.vo.user.PmnxUserVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author perccyking
 * @since 24-09-22 21:25
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(description = "用户保存请求参数")
public class PmnxUserSaveReqVo extends PmnxUserVo {


    /**
     * 是否启用
     */
    @Schema(description = "是否启用")
    private Boolean enabled;

}
