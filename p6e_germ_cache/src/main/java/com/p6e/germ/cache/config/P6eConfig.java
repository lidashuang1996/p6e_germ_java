package com.p6e.germ.cache.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author lidashuang
 * @version 1.0
 */
@Component
@ConfigurationProperties(prefix = "p6e")
public class P6eConfig {

    private P6eCacheConfig cache = new P6eCacheConfig();

    public P6eCacheConfig getCache() {
        return cache;
    }

    public void setCache(P6eCacheConfig cache) {
        this.cache = cache;
    }

}
