package com.p6e.germ.jurisdiction.annotation;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Import;
import com.p6e.germ.jurisdiction.init.P6eJurisdictionInit;
import java.lang.annotation.*;

/**
 * 权限启动器标记
 * @author lidashuang
 * @version 1.0
 */
@Documented
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Import(P6eJurisdictionInit.class)
@MapperScan({ "com.p6e.germ.jurisdiction.infrastructure.mapper" })
public @interface P6eEnableJurisdiction {
}
