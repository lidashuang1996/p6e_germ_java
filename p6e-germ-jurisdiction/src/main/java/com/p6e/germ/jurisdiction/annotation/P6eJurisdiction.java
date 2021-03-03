package com.p6e.germ.jurisdiction.annotation;

import com.p6e.germ.jurisdiction.condition.P6eCondition;

import java.lang.annotation.*;

/**
 * 标记需要权限验证的类/方法
 * 以及标记按照多个权限以及条件匹配
 * @author lidashuang
 * @version 1.0
 */
@Documented
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface P6eJurisdiction {

    /**
     * 需要的权限
     * @return 权限
     */
    String value() default "";

    /**
     * 需要的权限组
     * @return 权限数组
     */
    String[] values() default {};

    /**
     * 多个权限条件
     * @return 多个权限条件
     */
    P6eCondition condition() default P6eCondition.OR;

}
