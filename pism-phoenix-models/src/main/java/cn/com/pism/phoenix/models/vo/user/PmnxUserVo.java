package cn.com.pism.phoenix.models.vo.user;

import cn.com.pism.phoenix.models.enums.impl.UserGenerEnum;
import cn.com.pism.phoenix.models.enums.impl.UserStatusEnum;
import cn.com.pism.phoenix.models.jackson.serializer.ToStringSerializer;
import cn.com.pism.phoenix.models.vo.DictEnumWrapper;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import tools.jackson.databind.annotation.JsonSerialize;

/**
 * @author perccyking
 * @since 26-03-01 11:53
 */
@Data
@Schema(description = "用户")
public class PmnxUserVo {

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
     * 昵称
     */
    @Schema(description = "昵称")
    private String nickname;

    /**
     * 真名
     */
    @Schema(description = "真名")
    private String realName;

    /**
     * 头像
     */
    @Schema(description = "头像")
    private String avatarUrl;

    /**
     * 性别
     */
    @Schema(description = "性别")
    @DictEnumWrapper
    private UserGenerEnum gender;

    /**
     * 邮箱
     */
    @Schema(description = "邮箱")
    private String email;

    /**
     * 用户状态
     */
    @Schema(description = "用户状态")
    @DictEnumWrapper
    private UserStatusEnum status;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private Long createTime;
}
