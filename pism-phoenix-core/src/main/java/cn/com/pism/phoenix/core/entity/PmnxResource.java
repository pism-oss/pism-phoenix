package cn.com.pism.phoenix.core.entity;

import cn.com.pism.phoenix.core.common.ComEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * 资源
 * @author perccyking
 * @since 24-09-12 13:20
 */

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "pmnx_resource")
public class PmnxResource extends ComEntity {
    /**
     * 资源编码
     */
    @TableField(value = "resource_code")
    private String resourceCode;

    /**
     * 资源名称
     */
    @TableField(value = "resource_name")
    private String resourceName;

    /**
     * 资源类型
     */
    @TableField(value = "resource_type")
    private String resourceType;

    /**
     * 资源内容
     */
    @TableField(value = "resource_content")
    private String resourceContent;

    /**
     * 父级id
     */
    @TableField(value = "parent_id")
    private Long parentId;

    /**
     * 排序
     */
    @TableField(value = "sort")
    private Integer sort;

    /**
     * 是否启用，1：启用，0：禁用，默认启用
     */
    @TableField(value = "enabled")
    private Boolean enabled;

    public static final String COL_RESOURCE_CODE = "resource_code";

    public static final String COL_RESOURCE_NAME = "resource_name";

    public static final String COL_RESOURCE_TYPE = "resource_type";

    public static final String COL_RESOURCE_CONTENT = "resource_content";

    public static final String COL_PARENT_ID = "parent_id";

    public static final String COL_SORT = "sort";

    public static final String COL_ENABLED = "enabled";
}