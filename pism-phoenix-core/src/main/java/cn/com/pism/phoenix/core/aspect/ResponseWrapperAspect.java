package cn.com.pism.phoenix.core.aspect;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author perccyking
 * @since 24-08-26 14:01
 */
@Aspect
@Component
@RequiredArgsConstructor
public class ResponseWrapperAspect {

    private final ResponseWrapperEnhancer responseWrapperEnhancer;

    @Pointcut("execution(* *..controller..*.*(..))")
    public void controllerMethods() {
        // do nothing
    }

    @Pointcut("@annotation(cn.com.pism.phoenix.annotations.response.ResponseWrapper)")
    public void wrapperAnnotation() {
        // do nothing
    }

    /**
     * <p>
     * 所有被{@code ResponseWrapper}注解的类
     * </p>
     * by perccyking
     *
     * @since 24-08-26 15:17
     */
    @Pointcut("@within(cn.com.pism.phoenix.annotations.response.ResponseWrapper)")
    public void wrapperWithIn() {
        // do nothing
    }

    @Around("controllerMethods() || wrapperAnnotation() || wrapperWithIn()")
    public Object wrapResponse(ProceedingJoinPoint joinPoint) throws Throwable {
        return responseWrapperEnhancer.enhance(joinPoint);
    }

}
