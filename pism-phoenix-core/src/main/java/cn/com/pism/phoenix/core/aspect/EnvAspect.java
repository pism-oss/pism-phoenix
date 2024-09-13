package cn.com.pism.phoenix.core.aspect;

import cn.com.pism.exception.PismException;
import cn.com.pism.phoenix.annotations.env.Env;
import cn.com.pism.phoenix.annotations.env.EnvEnum;
import cn.com.pism.phoenix.core.config.PmnxProperties;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import java.util.List;

import static cn.com.pism.phoenix.models.exception.PmnxErrorCode.CANNOT_ACCESS;

/**
 * @author perccyking
 * @since 24-09-13 12:57
 */
@Aspect
@Component
@RequiredArgsConstructor
public class EnvAspect {

    private final PmnxProperties pmnxProperties;


    @Pointcut("@annotation(cn.com.pism.phoenix.annotations.env.Env)")
    public void envPointcut() {
        // do nothing
    }

    @Before("envPointcut()")
    public void evnCheck(JoinPoint joinPoint) {

        //查找类上的注解
        Env annotation = AnnotationUtils.findAnnotation(joinPoint.getTarget().getClass(), Env.class);
        checkEnv(annotation);

        //方法上的注解
        Env methodAnnotation = AnnotationUtils.findAnnotation(((MethodSignature) joinPoint.getSignature()).getMethod(), Env.class);
        checkEnv(methodAnnotation);

    }

    private void checkEnv(Env env) {
        if (env != null) {
            EnvEnum[] value = env.value();
            if (!List.of(value).contains(pmnxProperties.getEnv())) {
                throw new PismException(CANNOT_ACCESS);
            }
        }
    }
}
