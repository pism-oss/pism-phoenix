package cn.com.pism.phoenix.models.utils;

import cn.com.pism.phoenix.models.enums.DictEnum;
import com.baomidou.mybatisplus.annotation.IEnum;

import java.io.Serializable;

/**
 * @author perccyking
 * @since 26-03-01 20:09
 */
public class DictEnumUtil {

    private DictEnumUtil() {
    }

    /**
     * <p>
     * 通过 {@link IEnum#getValue()},{@link Enum#name} 获取对应枚举
     * </p>
     * by perccyking
     *
     * @param enumClass : 枚举class
     * @param code      : 枚举值{@link IEnum#getValue()},{@link Enum#name}
     * @return {@link E} 枚举
     * @since 26-03-01 20:10
     */
    public static <E extends Enum<E> & DictEnum<N, V>, N, V extends Serializable> E fromCode(
            Class<E> enumClass, Object code) {

        for (E e : enumClass.getEnumConstants()) {

            // 枚举名称
            boolean nameEquals = e.name().equals(code);

            // 枚举值
            boolean codeEquals = e.getValue().equals(code);

            // 转string的枚举值
            boolean codeStringEquals = e.getValue().toString().equalsIgnoreCase(code.toString());

            if (nameEquals || codeEquals || codeStringEquals) {
                return e;
            }
        }

        throw new IllegalArgumentException("Invalid enum code: " + code);
    }

}