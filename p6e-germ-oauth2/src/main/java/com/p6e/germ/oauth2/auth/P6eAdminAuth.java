package com.p6e.germ.oauth2.auth;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 客户端 [ CLIENT ] 认证的注解
 * @author lidashuang
 * @version 1.0
 */
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface P6eAdminAuth {
    // 可以在这里通过设置参数来实现一些等级权限的功能
    // 通过 Spring Boot AOP 实现注解的功能

    /**
     * 该接口需要的角色等级
     * @return 角色等级
     */
    int role() default -1;
}
