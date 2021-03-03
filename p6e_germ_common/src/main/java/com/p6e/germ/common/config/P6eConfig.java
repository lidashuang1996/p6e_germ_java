package com.p6e.germ.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * 服务的配置文件
 * @author lidashuang
 * @version 1.0
 */
@Component
@ConfigurationProperties(prefix = "p6e")
public class P6eConfig implements Serializable {

    private static boolean IS_INIT = false;
    private static P6eConfig CONFIG = null;

    public static P6eConfig get() {
        return CONFIG;
    }

    public static void init(ApplicationContext application) {
        IS_INIT = true;
        CONFIG = application.getBean(P6eConfig.class);
    }

    public static boolean isInit() {
        return IS_INIT;
    }

    /** 数据库的配置文件 */
    private P6eDatabaseConfig database = new P6eDatabaseConfig();

    /** 认证的配置文件  */
    private P6eOauth2Config oauth2 = new P6eOauth2Config();

    private P6eCacheConfig cache = new P6eCacheConfig();

    private P6eSecurityConfig security = new P6eSecurityConfig();

    public P6eCacheConfig getCache() {
        return cache;
    }

    public void setCache(P6eCacheConfig cache) {
        this.cache = cache;
    }

    public P6eDatabaseConfig getDatabase() {
        return database;
    }

    public void setDatabase(P6eDatabaseConfig database) {
        this.database = database;
    }

    public P6eOauth2Config getOauth2() {
        return oauth2;
    }

    public void setOauth2(P6eOauth2Config oauth2) {
        this.oauth2 = oauth2;
    }

    public P6eSecurityConfig getSecurity() {
        return security;
    }

    public void setSecurity(P6eSecurityConfig security) {
        this.security = security;
    }
}
