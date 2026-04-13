package cn.com.pism.phoenix.models.enums.impl;

import cn.com.pism.phoenix.models.enums.DictEnum;
import lombok.AllArgsConstructor;

/**
 * @author perccyking
 * @since 26-02-17 23:02
 */
@AllArgsConstructor
public enum UserStatusEnum implements DictEnum<String, Integer> {

    NORMAL("正常", 1),

    /**
     * 禁用，用户账号被管理员主动禁用 永久性/长期性管理状态
     */
    DISABLED("禁用", 2),

    /**
     * 冻结，触发系统风控 临时的管理状态
     */
    FROZEN("已冻结", 3);


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
