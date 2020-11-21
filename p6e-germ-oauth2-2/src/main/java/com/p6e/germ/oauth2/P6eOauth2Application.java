package com.p6e.germ.oauth2;

import com.p6e.germ.oauth2.infrastructure.utils.SpringUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 *
 * @author lidashuang
 * @version 1.0
 */
@ServletComponentScan
@SpringBootApplication
@EnableScheduling
@MapperScan({"com.p6e.germ.oauth2.infrastructure.repository.mapper"})
public class P6eOauth2Application {

    public static void main(String[] args) {
        SpringUtil.init(SpringApplication.run(P6eOauth2Application.class, args));
    }

}
