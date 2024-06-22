package cn.com.pism.phoenix.demo;

import cn.com.pism.phoenix.starter.annotations.EnablePismPhoenix;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnablePismPhoenix
public class PismPhoenixDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(PismPhoenixDemoApplication.class, args);
    }

}
