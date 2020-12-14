package com.p6e.germ.oauth2;

import com.p6e.germ.oauth2.starter.P6eOauth2Enable;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 认证的服务入口
 * @author lidashuang
 * @version 1.0
 */
@P6eOauth2Enable
@SpringBootApplication
public class P6eOauth2Application {

    public static void main(String[] args) {
        SpringApplication.run(P6eOauth2Application.class, args);
    }
}
