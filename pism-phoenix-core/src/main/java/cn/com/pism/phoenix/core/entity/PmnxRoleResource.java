package cn.com.pism.phoenix.core.entity;

import cn.com.pism.phoenix.core.common.ComEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * 角色-资源关系
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
@TableName(value = "pmnx_role_resource")
public class PmnxRoleResource extends ComEntity {
    /**
     * 角色id
     */
    @TableField(value = "role_id")
    private Long roleId;

    /**
     * 资源id
     */
    @TableField(value = "resource_id")
    private Long resourceId;

    public static final String COL_ROLE_ID = "role_id";

    public static final String COL_RESOURCE_ID = "resource_id";
}