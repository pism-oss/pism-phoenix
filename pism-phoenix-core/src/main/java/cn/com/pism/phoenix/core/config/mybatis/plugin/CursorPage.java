package cn.com.pism.phoenix.core.config.mybatis.plugin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Collections;
import java.util.List;

/**
 * @author perccyking
 * @since 26-04-06 22:17
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class CursorPage<T> extends Page<T> {

    /**
     * 分页方向
     */
    private boolean forward = true;

    /**
     * 游标值
     */
    private Object cursorValue;

    public CursorPage() {
        super();
    }

    /**
     * <p>
     * 游标分页构造器
     * </p>
     * by perccyking
     *
     * @param forward     : 翻页方向
     * @param size        : 翻页大小
     * @param cursorValue : 游标值
     * @since 26-04-06 23:39
     */
    public CursorPage(boolean forward, long size, Object cursorValue) {
        super(1, size);
        this.forward = forward;
        this.cursorValue = cursorValue;
    }

    /**
     * <p>
     * 向后翻页转置
     * </p>
     * by perccyking
     *
     * @param records : 分页结果
     * @return {@link Page<T>} 分页对象
     * @since 26-04-06 23:37
     */
    @Override
    public Page<T> setRecords(List<T> records) {
        if (!this.forward && records != null && !records.isEmpty()) {
            Collections.reverse(records);
        }
        return super.setRecords(records);
    }

}