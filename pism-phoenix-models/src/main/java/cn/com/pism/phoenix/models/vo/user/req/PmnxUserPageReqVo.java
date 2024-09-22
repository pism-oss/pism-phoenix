package cn.com.pism.phoenix.models.vo.user.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author perccyking
 * @since 24-09-22 03:09
 */
@Data
@Schema(description = "用户分页请求参数")
public class PmnxUserPageReqVo {

    /**
     * 关键字
     */
    @Schema(description = "关键字")
    private String keyWord;

    /**
     * 是否启用
     */
    @Schema(description = "是否启用")
    private Boolean enabled;


}
