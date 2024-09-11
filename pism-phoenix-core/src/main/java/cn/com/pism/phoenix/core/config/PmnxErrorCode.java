package cn.com.pism.phoenix.core.config;

import cn.com.pism.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 5x系统
 * 4x权限
 * 2x成功
 * @author perccyking
 * @since 24-09-09 17:21
 */
@AllArgsConstructor
@Getter
public enum PmnxErrorCode implements ErrorCode {
    CANNOT_ACCESS(4001, "无法访问");
    private final Integer code;

    private final String msg;

}
