package cn.com.pism.phoenix.core.aspect;

import cn.com.pism.exception.PismException;
import cn.com.pism.phoenix.annotations.cache.PmnxLocalCache;
import cn.com.pism.phoenix.core.config.SystemConfig;
import cn.com.pism.phoenix.core.util.LocalCacheUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author perccyking
 * @since 24-09-11 12:41
 */
@Component
@Slf4j
@Aspect
@RequiredArgsConstructor
public class PmnxLocalCacheAspect {

    private final LocalCacheUtil localCacheUtil;

    @Pointcut("@annotation(cn.com.pism.phoenix.annotations.cache.PmnxLocalCache)")
    public void cachePointcut() {
        // do nothing
    }

    @Around("cachePointcut()")
    public Object cacheProcess(ProceedingJoinPoint joinPoint) throws Throwable {

        //获取切面方法
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();

        //获取方法上的注解
        PmnxLocalCache pmnxLocalCache = AnnotationUtils.getAnnotation(method, PmnxLocalCache.class);

        //判断主键是否存在
        if (pmnxLocalCache != null) {

            //定义的缓存key
            String cacheKey = pmnxLocalCache.key();
            if (StringUtils.isNotBlank(cacheKey)) {

                //加上缓存key前缀
                cacheKey = SystemConfig.getCachePrefix() + cacheKey;

                //是否更新
                if (pmnxLocalCache.update()) {

                    //更新本地缓存数据
                    return localCacheUtil.updateCache(cacheKey, () -> joinProceed(joinPoint));
                }

                //尝试获取或更新缓存数据
                return localCacheUtil.getOrUpdate(cacheKey, () -> joinProceed(joinPoint),
                        pmnxLocalCache.expire(), pmnxLocalCache.timeUnit());
            }
        }

        return joinPoint.proceed();
    }

    /**
     * <p>
     * 切点处理
     * </p>
     * by perccyking
     *
     * @param joinPoint : 切点
     * @return {@link Object}
     * @since 24-09-12 01:19
     */
    private static Object joinProceed(ProceedingJoinPoint joinPoint) {
        try {
            return joinPoint.proceed();
        } catch (Throwable e) {
            throw new PismException(e);
        }
    }
}
