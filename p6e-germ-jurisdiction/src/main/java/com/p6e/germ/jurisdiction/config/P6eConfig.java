package com.p6e.germ.jurisdiction.config;

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

    public P6eDatabaseConfig getDatabase() {
        return database;
    }

    public void setDatabase(P6eDatabaseConfig database) {
        this.database = database;
    }
}
