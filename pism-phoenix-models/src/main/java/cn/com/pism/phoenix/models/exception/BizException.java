package cn.com.pism.phoenix.models.exception;

import cn.com.pism.exception.PismException;

import static cn.com.pism.exception.DefaultErrorCode.SYSTEM_ERROR;

/**
 * @author perccyking
 * @since 24-09-19 22:02
 */
public class BizException extends PismException {

    public BizException(String message) {
        super(message, SYSTEM_ERROR);
    }

}
