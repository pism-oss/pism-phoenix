package cn.com.pism.phoenix.demo;

import cn.com.pism.mybatis.core.aspect.EntityFillAspect;
import cn.com.pism.phoenix.starter.annotations.EnablePismPhoenix;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@EnablePismPhoenix
public class PismPhoenixDemoApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(PismPhoenixDemoApplication.class, args);
        EntityFillAspect bean = run.getBean(EntityFillAspect.class);
    }

}
