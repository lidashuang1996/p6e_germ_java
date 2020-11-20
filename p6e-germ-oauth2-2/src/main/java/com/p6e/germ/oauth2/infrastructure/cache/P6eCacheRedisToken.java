package com.p6e.germ.oauth2.infrastructure.cache;

import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author lidashuang
 * @version 1.0
 */
@Component
public class P6eCacheRedisToken extends P6eCacheRedis implements IP6eCacheToken {

    private static final String SOURCE_NAME = "";

    @Override
    public void set(String key, String value) {
        this.getRedisTemplate(SOURCE_NAME).opsForValue().set(TOKEN_NAME + key, value, TOKEN_TIME, TimeUnit.SECONDS);
    }

    @Override
    public String get(String key) {
        return this.getRedisTemplate(SOURCE_NAME).opsForValue().get(TOKEN_NAME + key);
    }

    @Override
    public void del(String key) {
        this.getRedisTemplate(SOURCE_NAME).delete(TOKEN_NAME + key);
    }

}
