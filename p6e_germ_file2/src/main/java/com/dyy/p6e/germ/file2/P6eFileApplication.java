package com.dyy.p6e.germ.file2;

import com.dyy.p6e.germ.file2.config.P6eConfig;
import com.dyy.p6e.germ.file2.core.P6eFileCoreFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.context.WebServerApplicationContext;
import org.springframework.context.annotation.Bean;

/**
 * 文件认证服务项目
 * 项目定位：是给后台开发，对图片文件有权限认证的需求
 * 快速文件认证的需求--权限管理---等
 * @author lidashuang
 * @version 1.0
 */
@SpringBootApplication
public class P6eFileApplication {

    public static void main(String[] args) {
        SpringApplication.run(P6eFileApplication.class, args);
    }

    @Bean
    public ApplicationRunner runner(final WebServerApplicationContext application) {
        return args -> {
            // 初始化配置文件
            P6eFileCoreFactory.init(application.getBean(P6eConfig.class).getFile());
        };
    }

}
