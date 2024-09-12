package cn.com.pism.phoenix.core.config;

import cn.com.pism.phoenix.models.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

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
}
