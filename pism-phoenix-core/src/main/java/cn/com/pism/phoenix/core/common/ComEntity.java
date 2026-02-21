package cn.com.pism.phoenix.core.common;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
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
    @TableField(value = "create_time")
    private Long createTime;

    /**
     * 创建人ID
     */
    @TableField(value = "create_by")
    private Long createBy;

    /**
     * 更新时间戳（毫秒）
     */
    @TableField(value = "update_time")
    private Long updateTime;

    /**
     * 更新人ID
     */
    @TableField(value = "update_by")
    private Long updateBy;

    /**
     * 是否删除 0-未删除 1-已删除
     */
    @TableField(value = "deleted")
    @TableLogic(delval = "1", value = "0")
    private boolean deleted;

    /**
     * 删除时间戳（毫秒）
     */
    @TableField(value = "delete_time")
    private Long deleteTime;

    /**
     * 删除人ID
     */
    @TableField(value = "delete_by")
    private Long deleteBy;
}
