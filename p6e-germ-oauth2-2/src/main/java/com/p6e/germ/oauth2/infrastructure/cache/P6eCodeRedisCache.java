package com.p6e.germ.oauth2.infrastructure.cache;

import java.util.concurrent.TimeUnit;

/**
 * @author lidashuang
 * @version 1.0
 */
public class P6eCodeRedisCache extends P6eCacheRedis implements IP6eCodeCache {

    private static final String SOURCE_NAME = "";

    @Override
    public void set(String key, String value) {
        this.getRedisTemplate(SOURCE_NAME).opsForValue().set(CODE_NAME + key, value, CODE_TIME, TimeUnit.SECONDS);
    }

    @Override
    public String get(String key) {
        return this.getRedisTemplate(SOURCE_NAME).opsForValue().get(CODE_NAME + key);
    }

    @Override
    public void del(String key) {
        this.getRedisTemplate(SOURCE_NAME).delete(CODE_NAME + key);
    }
}
