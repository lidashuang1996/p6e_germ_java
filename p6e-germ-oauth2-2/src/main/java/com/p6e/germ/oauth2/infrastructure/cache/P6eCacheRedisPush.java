package com.p6e.germ.oauth2.infrastructure.cache;

import java.util.concurrent.TimeUnit;

/**
 * @author lidashuang
 * @version 1.0
 */
public class P6eCacheRedisPush extends P6eCacheRedis implements IP6eCachePush {

    private static final String SOURCE_NAME = "A";

    @Override
    public void set(String key, String value) {
        getStringRedisTemplate(SOURCE_NAME).opsForValue().set(PUSH_NAME + key, value, PUSH_TIME, TimeUnit.SECONDS);
    }

    @Override
    public String get(String key) {
        return getStringRedisTemplate(SOURCE_NAME).opsForValue().get(PUSH_NAME + key);
    }

    @Override
    public void del(String key) {
        getStringRedisTemplate(SOURCE_NAME).delete(PUSH_NAME + key);
    }

}