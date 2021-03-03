package com.p6e.germ.security.annotation;

import java.lang.annotation.*;

/**
 * 标记需要安全验证的类/方法
 * @author lidashuang
 * @version 1.0
 */
@Documented
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface P6eSecurity {
}
