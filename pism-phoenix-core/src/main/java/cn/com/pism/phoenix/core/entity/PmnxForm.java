package cn.com.pism.phoenix.core.entity;

import cn.com.pism.phoenix.core.common.ComEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * 表单数据
 *
 * @author perccyking
 * @since 24-09-13 20:13
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "pmnx_form")
public class PmnxForm extends ComEntity {
    /**
     * 表单id
     */
    @TableField(value = "form_id")
    private String formId;

    /**
     * 字段id
     */
    @TableField(value = "field_id")
    private String fieldId;

    /**
     * 字段值
     */
    @TableField(value = "field_value")
    private String fieldValue;

    public static final String COL_FORM_ID = "form_id";

    public static final String COL_FIELD_ID = "field_id";

    public static final String COL_FIELD_VALUE = "field_value";
}