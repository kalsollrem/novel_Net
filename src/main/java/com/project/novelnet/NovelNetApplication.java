package com.project.novelnet;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(value = {"com.project.novelnet.repository"})
public class NovelNetApplication {

    public static void main(String[] args) {
        SpringApplication.run(NovelNetApplication.class, args);
    }

}
