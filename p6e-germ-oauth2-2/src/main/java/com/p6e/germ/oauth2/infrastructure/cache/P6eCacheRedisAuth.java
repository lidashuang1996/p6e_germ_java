package com.p6e.germ.oauth2.infrastructure.cache;

import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author lidashuang
 * @version 1.0
 */
@Component
public class P6eCacheRedisAuth extends P6eCacheRedis implements IP6eCacheAuth {

    private static final String SOURCE_NAME = "";

    @Override
    public void setMark(String key, String value) {
        this.getRedisTemplate(SOURCE_NAME).opsForValue().set(AUTH_NAME + key, value, AUTH_TIME, TimeUnit.SECONDS);
    }

    @Override
    public String getMark(String key) {
        return this.getRedisTemplate(SOURCE_NAME).opsForValue().get(AUTH_NAME + key);
    }

    @Override
    public void delMark(String key) {
        this.getRedisTemplate(SOURCE_NAME).delete(AUTH_NAME + key);
    }

}
