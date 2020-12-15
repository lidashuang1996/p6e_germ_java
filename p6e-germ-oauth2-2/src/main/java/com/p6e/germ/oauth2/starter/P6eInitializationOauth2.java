package com.p6e.germ.oauth2.starter;

import com.p6e.germ.oauth2.config.P6eConfig;
import com.p6e.germ.oauth2.infrastructure.utils.P6eJsonUtil;
import com.p6e.germ.oauth2.infrastructure.utils.P6eSpringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * 项目初始化方法
 * @author lidashuang
 * @version 1.0
 */
@Component
public class P6eInitializationOauth2 implements ApplicationRunner {

    /** 注入日志对象 */
    private static final Logger LOGGER = LoggerFactory.getLogger(P6eInitializationOauth2.class);

    /** 注入 ApplicationContext 对象 */
    private final ApplicationContext applicationContext;

    @Autowired
    public P6eInitializationOauth2(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public void run(ApplicationArguments args) {
        LOGGER.info("P6e oauth2 init start.");
        P6eSpringUtil.init(applicationContext);
        LOGGER.info("P6e oauth2 config ==> " + P6eJsonUtil.toJson(applicationContext.getBean(P6eConfig.class)));
        LOGGER.info("P6e oauth2 init end.");
    }

}
