package com.p6e.germ.security.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * 读取 yml 配置的信息到对象
 * 读取 yml 配置文件 p6e 开头的数据
 * @author lidashuang
 * @version 1.0
 */
@Component
@ConfigurationProperties(prefix = "p6e")
public class P6eConfig implements Serializable {

    /** 安全模块的配置文件 */
    private P6eConfigSecurity security = new P6eConfigSecurity();

    public P6eConfigSecurity getSecurity() {
        return security;
    }

    public void setSecurity(P6eConfigSecurity security) {
        this.security = security;
    }

}
