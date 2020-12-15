package com.p6e.germ.security.annotation;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @author lidashuang
 * @version 1.0
 */
@Order(10)
@Component
@Documented
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface P6eSecurityConfig {
}
