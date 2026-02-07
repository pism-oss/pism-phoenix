package cn.com.pism.phoenix.demo;

import cn.com.pism.mybatis.core.aspect.EntityFillAspect;
import cn.com.pism.phoenix.starter.annotations.EnablePismPhoenix;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;

@SpringBootApplication
@EnablePismPhoenix
@Deprecated
public class PismPhoenixDemoApplication {

    public static void main(String[] args) throws IOException {
        Enumeration<URL> sql = PismPhoenixDemoApplication.class.getClassLoader().getResources("sql");
        ConfigurableApplicationContext run = SpringApplication.run(PismPhoenixDemoApplication.class, args);
        EntityFillAspect bean = run.getBean(EntityFillAspect.class);
    }

}
