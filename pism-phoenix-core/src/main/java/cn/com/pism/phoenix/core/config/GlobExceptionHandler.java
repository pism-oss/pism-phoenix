package cn.com.pism.phoenix.core.config;

import cn.com.pism.exception.PismException;
import cn.com.pism.phoenix.models.JsonResult;
import cn.com.pism.phoenix.models.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

import static cn.com.pism.exception.DefaultErrorCode.SYSTEM_ERROR;

/**
 * @author perccyking
 * @since 24-09-12 18:39
 */
@RestControllerAdvice
@Slf4j
public class GlobExceptionHandler {

    @ExceptionHandler(BindException.class)
    public JsonResult<Object> methodArgumentNotValidException(BindException e) {
        log.error(e.getMessage(), e);
        String defaultMessage;
        try {
            BindingResult bindingResult = e.getBindingResult();
            defaultMessage = Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage();
        } catch (Exception ex) {
            return JsonResult.failed(e.getLocalizedMessage());
        }
        return JsonResult.failed(defaultMessage);
    }

    @ExceptionHandler(BizException.class)
    public JsonResult<Object> bizException(BizException e) {
        log.error(e.getMessage(), e);
        return JsonResult.failed(e.getMessage());
    }

    @ExceptionHandler(PismException.class)
    public JsonResult<Object> pismException(PismException e) {
        if (e.getErrorCode() != null) {
            log.error("{}-{}", e.getMessage(), e.getErrorCode().getMsg(), e);
            return JsonResult.failed(e.getErrorCode());
        } else {
            log.error(e.getMessage(), e);
            return JsonResult.failed(e.getMessage());
        }
    }

    @ExceptionHandler(Exception.class)
    public JsonResult<Object> exception(Exception e) {
        log.error(e.getMessage(), e);
        return JsonResult.failed(SYSTEM_ERROR);
    }
}
