package com.p6e.germ.oauth2.starter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

import java.lang.annotation.*;

/**
 * 启动 P6e Oauth2 的注解
 * @author lidashuang
 * @version 1.0
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ComponentScans({
        @ComponentScan("com.p6e.germ.oauth2.starter"),
        @ComponentScan("com.p6e.germ.oauth2.infrastructure.task"),
        @ComponentScan("com.p6e.germ.oauth2.infrastructure.repository.plugin"),
        @ComponentScan("com.p6e.germ.oauth2.context.rest"),
        @ComponentScan("com.p6e.germ.oauth2.context.controller"),
        @ComponentScan("com.p6e.germ.oauth2.context.support.filter")
})
@MapperScan({ "com.p6e.germ.oauth2.infrastructure.repository.mapper" })
public @interface P6eEnableOauth2 {
}
