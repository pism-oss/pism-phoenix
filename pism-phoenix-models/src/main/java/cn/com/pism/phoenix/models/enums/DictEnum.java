package cn.com.pism.phoenix.models.enums;

import com.baomidou.mybatisplus.annotation.IEnum;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 字典枚举
 *
 * @author perccyking
 * @since 24-09-12 12:52
 */
public interface DictEnum<N, V extends Serializable> extends IEnum<V> {

    /**
     * <p>
     * 枚举名称
     * </p>
     * by perccyking
     *
     * @return {@link N} 枚举名称
     * @since 24-09-12 12:59
     */
    N getName();

    /**
     * <p>
     * 获取枚举字典扩展数据
     * </p>
     * by perccyking
     *
     * @return 枚举字典扩展数据
     * @since 24-09-12 13:06
     */
    default Map<String, Object> getExt() {
        return HashMap.newHashMap(16);
    }
}
