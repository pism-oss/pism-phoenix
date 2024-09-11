package cn.com.pism.phoenix.core.entity;

import cn.com.pism.phoenix.core.common.ComEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * 用户表
 *
 * @author perccyking
 * @since 24-08-25 18:15
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
     * 邮箱
     */
    @TableField(value = "email")
    private String email;

    /**
     * 是否启用，1：启用，0：禁用，默认启用
     */
    @TableField(value = "enabled")
    @Builder.Default
    private boolean enabled = true;

    public static final String COL_ACCOUNT = "account";

    public static final String COL_EMAIL = "email";

    public static final String COL_PASSWORD = "password";

    public static final String COL_ENABLED = "enabled";

}