package com.p6e.germ;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * 该类为 p6e-germ-oauth2 spring boot 启动入口
 * @author lidashuang
 * @version 1.0
 */
@ServletComponentScan
@SpringBootApplication
@MapperScan({"com.p6e.germ.oauth2.mapper", "com.p6e.germ.security.mapper"})
public class P6eOauth2Application {

    public static void main(String[] args) {
        SpringApplication.run(P6eOauth2Application.class, args);
    }

}
