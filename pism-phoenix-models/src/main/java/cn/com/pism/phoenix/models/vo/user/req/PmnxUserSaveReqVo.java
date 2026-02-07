package cn.com.pism.phoenix.models.vo.user.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author perccyking
 * @since 24-09-22 21:25
 */
@Data
@Schema(description = "用户保存请求参数")
public class PmnxUserSaveReqVo {

    /**
     * 主键id
     */
    @Schema(description = "主键id")
    private Long id;

    /**
     * 账号
     */
    @Schema(description = "账号")
    private String account;

    /**
     * 邮箱
     */
    @Schema(description = "邮箱")
    private String email;


}
