package cn.com.pism.phoenix.models.vo.cas.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author perccyking
 * @since 24-08-26 17:42
 */
@Schema(description = "用户名密码登录请求参数")
@Data
public class PmnxCasPasswordLoginReqVo {

    /**
     * 账号
     */
    @Schema(description = "账号-cas公钥加密")
    private String account;

    /**
     * 密码
     */
    @Schema(description = "密码-cas公钥加密")
    private String password;

    /**
     * 密钥id
     */
    @Schema(description = "密钥id")
    private String keyId;


}
