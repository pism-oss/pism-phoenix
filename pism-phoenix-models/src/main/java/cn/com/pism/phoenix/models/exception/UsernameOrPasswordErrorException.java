package cn.com.pism.phoenix.models.exception;

import cn.com.pism.exception.PismException;

import static cn.com.pism.phoenix.models.exception.PmnxErrorCode.USERNAME_OR_PASSWORD_ERROR;


/**
 * @author perccyking
 * @since 24-09-13 01:41
 */
public class UsernameOrPasswordErrorException extends PismException {

    public UsernameOrPasswordErrorException() {
        super(USERNAME_OR_PASSWORD_ERROR);
    }

    public UsernameOrPasswordErrorException(Throwable cause) {
        super(cause, USERNAME_OR_PASSWORD_ERROR);
    }
}
