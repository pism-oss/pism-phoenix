package cn.com.pism.phoenix.core.aspect;

import cn.com.pism.phoenix.annotations.response.ResponseWrapper;
import cn.com.pism.phoenix.core.util.SpringMvcUtil;
import cn.com.pism.phoenix.models.JsonResult;
import cn.com.pism.phoenix.utils.Jackson;
import jakarta.servlet.http.HttpServletResponse;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

/**
 * @author perccyking
 * @since 24-08-26 14:01
 */
@Component
public class ResponseWrapperEnhancer {
    public Object enhance(ProceedingJoinPoint joinPoint) throws Throwable {
        ResponseWrapper annotation = AnnotationUtils.findAnnotation(joinPoint.getTarget().getClass(), ResponseWrapper.class);
        if (annotation != null && annotation.ignore()) {
            return joinPoint.proceed();
        }
        ResponseWrapper methodAnnotation = AnnotationUtils.findAnnotation(((MethodSignature) joinPoint.getSignature()).getMethod(), ResponseWrapper.class);
        if (methodAnnotation != null && methodAnnotation.ignore()) {
            return joinPoint.proceed();
        }
        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long duration = System.currentTimeMillis() - startTime;
        if (result instanceof JsonResult) {
            ((JsonResult<?>) result).setDs(duration);
        } else {
            result = JsonResult.successData(result);
            ((JsonResult<?>) result).setDs(duration);
        }
        HttpServletResponse response = SpringMvcUtil.getResponse();
        response.setContentType(APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        try (PrintWriter writer = response.getWriter()) {
            writer.write(Jackson.toJsonString(result));
            writer.flush();
        }
        return null;
    }
}
