package com.jhmarryme;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author com.jhmarryme
 * @date 2020/9/7 22:28
 */
@SpringBootApplication
@RestController
@MapperScan("com.com.jhmarryme.web.example.dao")
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @GetMapping("/hello")
    public String hello() {
        return "hello spring security";
    }
}
