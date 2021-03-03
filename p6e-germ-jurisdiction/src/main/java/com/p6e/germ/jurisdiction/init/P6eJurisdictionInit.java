package com.p6e.germ.jurisdiction.init;

import com.p6e.germ.common.config.P6eConfig;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;

import javax.annotation.Resource;

/**
 * 权限初始化的类
 * @author lidashuang
 * @version 1.0
 */
public class P6eJurisdictionInit implements ApplicationRunner {

    @Resource
    private ApplicationContext application;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        P6eConfig.init(application);
    }

}
