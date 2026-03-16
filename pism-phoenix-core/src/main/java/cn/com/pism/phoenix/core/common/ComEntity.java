package cn.com.pism.phoenix.core.common;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

/**
 * 实体父类，所有实体都应继承这个类
 *
 * @author perccyking
 * @since 24-08-22 20:51
 */
@Data
public class ComEntity {

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;


    /**
     * 创建时间戳（毫秒）
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Long createTime;

    /**
     * 创建人ID
     */
    @TableField(value = "create_by", fill = FieldFill.INSERT)
    private Long createBy;

    /**
     * 更新时间戳（毫秒）
     */
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private Long updateTime;

    /**
     * 更新人ID
     */
    @TableField(value = "update_by", fill = FieldFill.INSERT_UPDATE)
    private Long updateBy;

    /**
     * 是否删除 0-未删除 1-已删除
     */
    @TableField(value = "deleted", fill = FieldFill.INSERT_UPDATE)
    @TableLogic(delval = "1", value = "0")
    private boolean deleted;

    /**
     * 删除时间戳（毫秒）
     */
    @TableField(value = "delete_time", fill = FieldFill.INSERT_UPDATE)
    private Long deleteTime;

    /**
     * 删除人ID
     */
    @TableField(value = "delete_by", fill = FieldFill.INSERT_UPDATE)
    private Long deleteBy;


    public static final String COL_ID = "id";

    public static final String COL_CREATE_BY = "create_by";

    public static final String COL_UPDATE_BY = "update_by";

    public static final String COL_CREATE_TIME = "create_time";

    public static final String COL_UPDATE_TIME = "update_time";

    public static final String COL_DELETED = "deleted";

    public static final String COL_DELETE_TIME = "delete_time";

    public static final String COL_DELETE_BY = "delete_by";

    public static final String FIELD_ID = "id";

    public static final String FIELD_CREATE_BY = "createBy";

    public static final String FIELD_UPDATE_BY = "updateBy";

    public static final String FIELD_CREATE_TIME = "createTime";

    public static final String FIELD_UPDATE_TIME = "updateTime";

    public static final String FIELD_DELETED = "deleted";

    public static final String FIELD_DELETE_TIME = "deleteTime";

    public static final String FIELD_DELETE_BY = "deleteBy";
}
