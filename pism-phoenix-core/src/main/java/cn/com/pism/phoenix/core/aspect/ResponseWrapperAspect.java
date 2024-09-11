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

    /**
     * <p>
     * 切点：所有controller包下所有类的方法
     * </p>
     * by perccyking
     *
     * @since 24-09-12 01:25
     */
    @Pointcut("execution(* *..controller..*.*(..))")
    public void controllerPackage() {
        // do nothing
    }

    /**
     * <p>
     * 切点：所有Controller类中的方法
     * </p>
     * by perccyking
     *
     * @since 24-09-12 01:25
     */
    @Pointcut("within(*..*Controller)")
    public void controllerMethods() {
        // do nothing
    }

    /**
     * <p>
     * 切点：被{@code ResponseWrapper} 注解的方法
     * </p>
     * by perccyking
     *
     * @since 24-09-12 01:26
     */
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

    @Around("controllerPackage() ||controllerMethods()|| wrapperAnnotation() || wrapperWithIn()")
    public Object wrapResponse(ProceedingJoinPoint joinPoint) throws Throwable {
        return responseWrapperEnhancer.enhance(joinPoint);
    }

}
