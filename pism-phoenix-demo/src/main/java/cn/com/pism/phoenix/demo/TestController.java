package cn.com.pism.phoenix.demo;

import cn.com.pism.phoenix.annotations.form.FormRefresh;
import cn.com.pism.phoenix.core.service.PmnxSecurityService;
import cn.com.pism.phoenix.demo.config.UserConfig;
import cn.com.pism.phoenix.utils.Jackson;
import cn.dev33.satoken.annotation.SaIgnore;
import cn.hutool.crypto.digest.BCrypt;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

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

    private final UserConfig userConfig;

    private final JdbcTemplate jdbcTemplate;

    @Resource
    private PmnxSecurityService pmnxSecurityService;


    @GetMapping("test")
    @SaIgnore
    @FormRefresh
    public String test(HttpServletRequest request) {
        return Jackson.toJsonString(userConfig);

//        stringRedisTemplate.opsForValue().set("13","13");
//        String s = stringRedisTemplate.opsForValue().get("13");
//        stringRedisTemplate.delete("13");
////        return authorizationRequestResolver.resolve(request).getAuthorizationRequestUri();
//        return s;
//        return "ok";
    }

    private List<UserConfig> a = new ArrayList<>();

    @GetMapping("/test1")
    public void test1(){


        while (true){
            UserConfig userConfig1 = new UserConfig();
            userConfig1.setXx(List.of(UUID.randomUUID().toString()));
            a.add(userConfig1);
        }

    }

}
