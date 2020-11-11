package com.dyy.p6e.germ.file2.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * p6e config 配置文件
 * @author lidashuang
 * @version 1.0
 */
@Component
@ConfigurationProperties(prefix = "p6e")
public class P6eConfig implements Serializable {

    /** 文件操作的配置文件对象 */
    private P6eConfigFile file = new P6eConfigFile();

    public P6eConfigFile getFile() {
        return file;
    }

    public void setFile(P6eConfigFile file) {
        this.file = file;
    }
}
