package cn.com.pism.phoenix.models.vo.user.resp;

import cn.com.pism.phoenix.models.jackson.serializer.ToStringSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author perccyking
 * @since 24-09-22 03:11
 */
@Data
@Schema(description = "用户分页对象")
public class PmnxUserPageRespVo {

    /**
     * 主键id
     */
    @Schema(description = "主键id")
    @JsonSerialize(using = ToStringSerializer.class)
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

    /**
     * 是否启用
     */
    @Schema(description = "是否启用")
    private boolean enabled = true;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long createTime;


}
