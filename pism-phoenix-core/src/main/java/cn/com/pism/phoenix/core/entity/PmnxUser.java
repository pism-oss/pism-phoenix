package cn.com.pism.phoenix.core.entity;

import cn.com.pism.exception.ErrorCode;
import cn.com.pism.exception.PismException;
import cn.com.pism.phoenix.core.common.ComEntity;
import cn.com.pism.phoenix.models.enums.impl.UserGenerEnum;
import cn.com.pism.phoenix.models.enums.impl.UserStatusEnum;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import lombok.experimental.Accessors;

import static cn.com.pism.phoenix.models.exception.PmnxErrorCode.*;

/**
 * 用户
 *
 * @author perccyking
 * @since 24-09-12 13:20
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "pmnx_user")
public class PmnxUser extends ComEntity {
    /**
     * 账号
     */
    @TableField(value = "account")
    private String account;

    /**
     * 电子邮箱
     */
    @TableField(value = "email")
    private String email;

    /**
     * 用户昵称
     */
    @TableField(value = "nickname")
    private String nickname;

    /**
     * 真实姓名
     */
    @TableField(value = "real_name")
    private String realName;

    /**
     * 头像URL
     */
    @TableField(value = "avatar_url")
    private String avatarUrl;

    /**
     * 性别 0-未知 1-男 2-女
     */
    @TableField(value = "gender")
    @Builder.Default
    private UserGenerEnum gender = UserGenerEnum.UNKNOWN;

    /**
     * 手机号码（带国家码）
     */
    @TableField(value = "mobile")
    private String mobile;

    /**
     * 状态 1-正常 2-禁用 3-冻结
     */
    @TableField(value = "`status`")
    @Builder.Default
    private UserStatusEnum status = UserStatusEnum.NORMAL;

    public void statusCheck() {
        if (!UserStatusEnum.NORMAL.equals(this.status)) {
            ErrorCode<?> errorCode = CANNOT_ACCESS;
            if (UserStatusEnum.DISABLED.equals(this.status)) {
                errorCode = USER_DISABLED;
            } else if (UserStatusEnum.FROZEN.equals(this.status)) {
                errorCode = USER_FROZEN;
            }
            throw new PismException(errorCode);
        }
    }

}