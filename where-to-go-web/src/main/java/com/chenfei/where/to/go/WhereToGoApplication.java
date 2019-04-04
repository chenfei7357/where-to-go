package com.chenfei.where.to.go;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.chenfei.where.to.go.dao")
@EnableDubbo
public class WhereToGoApplication {

    public static void main(String[] args) {

        SpringApplication.run(WhereToGoApplication.class, args);
    }

}
