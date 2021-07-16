package com.jhmarryme;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * description: 
 * @author JiaHao Wang
 * @date 2021/1/26 22:57
 */
@SpringBootApplication
@MapperScan(basePackages = "com.jhmarryme.mapper")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
