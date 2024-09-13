package cn.com.pism.phoenix.annotations.env;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author perccyking
 * @since 24-09-13 12:23
 */
@AllArgsConstructor
@Getter
public enum EnvEnum {

    /**
     * 开发环境
     */
    DEV("dev"),

    /**
     * 测试环境
     */
    TEST("test"),

    /**
     * 预发布黄金
     */
    PRE("pre"),

    /**
     * 生产环境
     */
    PROD("prod");;
    private final String value;

}
