package com.p6e.germ.oauth2.starter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.web.servlet.ServletComponentScan;

import java.lang.annotation.*;

/**
 * 启动 P6e Oauth2 的注解
 * @author lidashuang
 * @version 1.0
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ServletComponentScan
@MapperScan({ "com.p6e.germ.oauth2.infrastructure.repository.mapper" })
public @interface P6eOauth2Enable {
}
