package cn.com.pism.phoenix.models.vo.cas.resp;

import cn.com.pism.phoenix.models.vo.cas.PmnxCasTokenVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author perccyking
 * @since 24-09-12 15:43
 */
@Data
@Schema(description = "登录响应")
public class PmnxCasLoginRespVo {

    /**
     * token
     */
    @Schema(description = "token")
    private PmnxCasTokenVo token;

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
