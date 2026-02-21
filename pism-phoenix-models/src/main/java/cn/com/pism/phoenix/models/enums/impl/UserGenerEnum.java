package cn.com.pism.phoenix.models.enums.impl;

import cn.com.pism.phoenix.models.enums.DictEnum;
import lombok.AllArgsConstructor;

/**
 * @author perccyking
 * @since 26-02-17 22:57
 */
@AllArgsConstructor
public enum UserGenerEnum implements DictEnum<String, Integer> {

    UNKNOWN("未知", 0),
    MALE("男", 1),
    FEMALE("女", 2);

    private final String name;
    private final Integer code;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Integer getValue() {
        return code;
    }
}
