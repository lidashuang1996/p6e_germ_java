package com.p6e.germ.jurisdiction.annotation;

import com.p6e.germ.jurisdiction.condition.P6eCondition;

import java.lang.annotation.*;

/**
 * @author lidashuang
 * @version 1.0
 */
@Documented
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface P6eJurisdiction {

    /**
     * 权限
     * @return 权限
     */
    String value() default "";

    /**
     * 处理的权限数组
     * @return 权限数组
     */
    String[] values() default {};

    /**
     * 条件判断
     * @return 选择的条件
     */
    P6eCondition condition() default P6eCondition.OR;

}
