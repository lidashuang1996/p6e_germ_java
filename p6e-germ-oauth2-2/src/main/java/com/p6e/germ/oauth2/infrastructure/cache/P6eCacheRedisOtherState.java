package com.p6e.germ.oauth2.infrastructure.cache;

import java.util.concurrent.TimeUnit;

/**
 * @author lidashuang
 * @version 1.0
 */
public class P6eCacheRedisOtherState extends P6eCacheRedis implements IP6eCacheOtherState {

    private static final String SOURCE_NAME = "A";

    @Override
    public void set(String type, String key, String value) {
        getStringRedisTemplate(SOURCE_NAME).opsForValue().set(MARK_NAME + type + key, value, MARK_TIME, TimeUnit.SECONDS);
    }

    @Override
    public String get(String type, String key) {
        return getStringRedisTemplate(SOURCE_NAME).opsForValue().get(MARK_NAME + type + key);
    }

    @Override
    public void del(String type, String key) {
        getStringRedisTemplate(SOURCE_NAME).delete(MARK_NAME + type + key);
    }

}
