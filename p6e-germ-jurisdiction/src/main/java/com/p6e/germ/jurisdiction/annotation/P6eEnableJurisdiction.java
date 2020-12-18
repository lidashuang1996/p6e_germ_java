package com.p6e.germ.jurisdiction.annotation;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.web.servlet.ServletComponentScan;

import java.lang.annotation.*;

/**
 * @author lidashuang
 * @version 1.0
 */
@Documented
@ServletComponentScan
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@MapperScan({ "com.p6e.germ.jurisdiction.infrastructure.repository.mapper" })
public @interface P6eEnableJurisdiction {
}
