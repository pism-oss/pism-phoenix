package cn.com.pism.phoenix.core.entity;

import cn.com.pism.phoenix.core.common.ComEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * 角色
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
@TableName(value = "pmnx_role")
public class PmnxRole extends ComEntity {
    /**
     * 角色编码
     */
    @TableField(value = "role_code")
    private String roleCode;

    /**
     * 角色名称
     */
    @TableField(value = "role_name")
    private String roleName;

    /**
     * 是否启用，1：启用，0：禁用，默认启用
     */
    @TableField(value = "enabled")
    private Boolean enabled;

    public static final String COL_ROLE_CODE = "role_code";

    public static final String COL_ROLE_NAME = "role_name";

    public static final String COL_ENABLED = "enabled";
}