package com.chenfei.where.to.go;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.chenfei.where.to.go.dao")
public class WhereToGoApplication {

    public static void main(String[] args) {

        SpringApplication.run(WhereToGoApplication.class, args);
    }

}
