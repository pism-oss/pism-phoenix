package cn.com.pism.phoenix.demo;

import cn.com.pism.phoenix.starter.annotations.EnablePismPhoenix;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnablePismPhoenix
@Log4j2
public class PismPhoenixDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(PismPhoenixDemoApplication.class, args);
    }

}
