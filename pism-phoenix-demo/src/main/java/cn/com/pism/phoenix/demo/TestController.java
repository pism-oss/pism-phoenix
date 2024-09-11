package cn.com.pism.phoenix.demo;

import cn.com.pism.phoenix.core.service.PmnxSecurityService;
import cn.com.pism.phoenix.utils.Jackson;
import cn.dev33.satoken.annotation.SaIgnore;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author perccyking
 * @since 24-07-16 11:05
 */
@RequestMapping("/test")
@RestController
@RequiredArgsConstructor
public class TestController {

//    @Resource
//    private OAuth2AuthorizationRequestResolver authorizationRequestResolver;

    private final StringRedisTemplate stringRedisTemplate;


    private final RedissonClient redissonClient;

    @Resource
    private PmnxSecurityService pmnxSecurityService;


    @GetMapping("test")
    @SaIgnore
    public String test(HttpServletRequest request) {
        return Jackson.toJsonString(pmnxSecurityService.getBlacklist());

//        stringRedisTemplate.opsForValue().set("13","13");
//        String s = stringRedisTemplate.opsForValue().get("13");
//        stringRedisTemplate.delete("13");
////        return authorizationRequestResolver.resolve(request).getAuthorizationRequestUri();
//        return s;
//        return "ok";
    }
}
