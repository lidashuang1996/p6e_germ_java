package com.p6e.germ.oauth2;

import com.p6e.germ.oauth2.domain.entity.P6eUserEntity;
import com.p6e.germ.oauth2.starter.P6eEnableOauth2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * 认证的服务入口
 * @author lidashuang
 * @version 1.0
 */
@P6eEnableOauth2
@SpringBootApplication
public class P6eOauth2Application {
    public static void main(String[] args) {
        SpringApplication.run(P6eOauth2Application.class, args);
        System.out.println(P6eUserEntity.encryption("123456"));
    }
}
