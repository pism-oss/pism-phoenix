package cn.com.pism.phoenix.core.aspect;

import cn.com.pism.phoenix.annotations.form.FormRefresh;
import cn.com.pism.phoenix.core.service.PmnxFormService;
import cn.com.pism.phoenix.core.util.LocalCacheUtil;
import cn.com.pism.phoenix.core.util.SpringUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author perccyking
 * @since 24-09-19 23:04
 */
@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class FormRefreshAspect {

    private final SpringUtil springUtil;

    private final PmnxFormService pmnxFormService;

    private final LocalCacheUtil localCacheUtil;

    @Pointcut("@annotation(cn.com.pism.phoenix.annotations.form.FormRefresh)")
    public void fromRefreshPointcut() {
        // do nothing
    }

    @Before("fromRefreshPointcut()")
    public void fromRefresh(JoinPoint joinPoint) {
        try {
            FormRefresh methodAnnotation = AnnotationUtils.findAnnotation(((MethodSignature) joinPoint.getSignature()).getMethod(), FormRefresh.class);
            if (methodAnnotation != null) {
                Class<?>[] values = methodAnnotation.value();
                List<Object> forms = new ArrayList<>();
                for (Class<?> value : values) {
                    if (value.equals(Void.class)) {
                        pmnxFormService.refreshForm();
                    } else {
                        if (localCacheUtil.isSameVersion(value.getName())) {
                            //检查本地与远程版本
                            forms.add(springUtil.getBean(value));
                        }
                    }
                }
                pmnxFormService.batchRefreshForm(forms);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

    }
}
