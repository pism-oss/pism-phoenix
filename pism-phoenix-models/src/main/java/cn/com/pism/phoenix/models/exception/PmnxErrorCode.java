package cn.com.pism.phoenix.models.exception;

import cn.com.pism.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 5x系统
 * 4x权限
 * 2x成功
 *
 * @author perccyking
 * @since 24-09-09 17:21
 */
@AllArgsConstructor
@Getter
public enum PmnxErrorCode implements ErrorCode {

    USERNAME_OR_PASSWORD_ERROR(4000, "用户名或密码错误"),

    CANNOT_ACCESS(4001, "无法访问");
    private final Integer code;

    private final String msg;

}
