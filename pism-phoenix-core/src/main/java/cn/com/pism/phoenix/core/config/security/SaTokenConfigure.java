package cn.com.pism.phoenix.core.config.security;

import cn.com.pism.exception.PismException;
import cn.com.pism.phoenix.core.config.PmnxProperties;
import cn.com.pism.phoenix.core.service.PmnxSecurityService;
import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

import static cn.com.pism.phoenix.core.config.PmnxErrorCode.CANNOT_ACCESS;
import static cn.dev33.satoken.util.StrFormatter.format;

/**
 * 路由动态鉴权配置
 *
 * @author perccyking
 * @since 24-08-27 00:48
 */
@Configuration
@RequiredArgsConstructor
@Slf4j
public class SaTokenConfigure implements WebMvcConfigurer {

    private final PmnxProperties pmnxProperties;

    private final PmnxSecurityService pmnxSecurityService;

    private static final String PATH_ALL = "/**";

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration registration = registry.addInterceptor(new SaInterceptor(handler -> {

                    //黑名单
                    checkBlacklist();

                    //过滤白名单
                    checkWhitelist();

                    //登录校验
                    SaRouter.match(PATH_ALL).check(() -> {
                        log.info("request url:{}", SaHolder.getRequest().getRequestPath());
                        StpUtil.checkLogin();
                    });

                    //资源权限校验
                    checkResourcePermission();

                }))
                //拦截所有链接
                .addPathPatterns(PATH_ALL);

        //权限校验未开启，拦截器排除所有链接
        if (!pmnxProperties.getSecurity().isEnabled()) {
            registration.excludePathPatterns(PATH_ALL);
        }
    }

    private void checkResourcePermission() {
        pmnxSecurityService.getResourceCode().forEach(resourceCodeBo ->
                SaRouter.match(resourceCodeBo.getResource())
                        .check(() -> StpUtil.checkPermission(resourceCodeBo.getResource()))
                        .stop());
    }

    /**
     * <p>
     * 校验黑名单
     * </p>
     * by perccyking
     *
     * @since 24-09-09 17:18
     */
    private void checkBlacklist() {
        List<String> blacklist = new ArrayList<>();
        blacklist.addAll(pmnxProperties.getSecurity().getBlacklist());
        blacklist.addAll(pmnxSecurityService.getBlacklist());
        SaRouter.match(blacklist)
                .check(() -> {
                    throw new PismException(CANNOT_ACCESS);
                })
                .stop();
    }

    /**
     * <p>
     * 校验白名单
     * </p>
     * by perccyking
     *
     * @since 24-09-09 17:17
     */
    private void checkWhitelist() {
        List<String> whitelist = new ArrayList<>();
        whitelist.addAll(pmnxProperties.getSecurity().getWhitelist());
        whitelist.addAll(pmnxSecurityService.getWhitelist());
        SaRouter.match(whitelist)
                .check(() -> log.info(format("anonymous url:{}", SaHolder.getRequest().getRequestPath())))
                .stop();
    }
}
