package com.p6e.germ.common.config;

/**
 *
 * @author lidashuang
 * @version 1.0
 */
public class P6eCacheConfig {

    private P6eCacheRedisConfig redis = new P6eCacheRedisConfig();

    public P6eCacheRedisConfig getRedis() {
        return redis;
    }

    public void setRedis(P6eCacheRedisConfig redis) {
        this.redis = redis;
    }

}
