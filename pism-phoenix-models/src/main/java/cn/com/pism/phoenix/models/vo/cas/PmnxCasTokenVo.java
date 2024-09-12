package cn.com.pism.phoenix.models.vo.cas;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author perccyking
 * @since 24-09-12 15:45
 */
@Data
@Schema(description = "token信息")
public class PmnxCasTokenVo {

    /**
     * token
     */
    @Schema(description = "token")
    private String token;

    /**
     * token名称
     */
    @Schema(description = "token名称")
    private String tokenName;

    /**
     * 有效期,单位s
     */
    @Schema(description = "有效期,单位s")
    private long timeout;

}
