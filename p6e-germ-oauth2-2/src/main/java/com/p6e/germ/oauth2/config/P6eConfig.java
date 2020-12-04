package com.p6e.germ.oauth2.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
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

    /** 数据库的配置文件 */
    private P6eDatabaseConfig database = new P6eDatabaseConfig();

    /** 认证的配置文件  */
    private P6eOauth2Config oauth2 = new P6eOauth2Config();

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
}
