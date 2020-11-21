package com.p6e.germ.oauth2.infrastructure.cache;

import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author lidashuang
 * @version 1.0
 */
@Component
public class P6eCacheRedisClient extends P6eCacheRedis implements IP6eCacheClient {

    private static final String SOURCE_NAME = "";

    @Override
    public void set(String key, String value) {
        this.getRedisTemplate(SOURCE_NAME).opsForValue().set(CLIENT_NAME + key, value, CLIENT_TIME, TimeUnit.SECONDS);
    }

    @Override
    public String get(String key) {
        return this.getRedisTemplate(SOURCE_NAME).opsForValue().get(CLIENT_NAME + key);
    }

    @Override
    public void del(String key) {
        this.getRedisTemplate(SOURCE_NAME).delete(CLIENT_NAME + key);
    }

    @Override
    public void setDbId(String key, String value) {
        this.getRedisTemplate(SOURCE_NAME).opsForValue().set(CLIENT_DB_ID_NAME + key, value, CLIENT_DB_TIME, TimeUnit.SECONDS);
    }

    @Override
    public void delDbId(String key) {
        this.getRedisTemplate(SOURCE_NAME).delete(CLIENT_DB_ID_NAME + key);
    }

    @Override
    public String getDbId(String key) {
        return this.getRedisTemplate(SOURCE_NAME).opsForValue().get(CLIENT_DB_ID_NAME + key);
    }

    @Override
    public void setDbKey(String key, String value) {
        this.getRedisTemplate(SOURCE_NAME).opsForValue().set(CLIENT_DB_KEY_NAME + key, value, CLIENT_DB_TIME, TimeUnit.SECONDS);
    }

    @Override
    public void delDbKey(String key) {
        this.getRedisTemplate(SOURCE_NAME).delete(CLIENT_DB_KEY_NAME + key);
    }

    @Override
    public String getDbKey(String key) {
        return this.getRedisTemplate(SOURCE_NAME).opsForValue().get(CLIENT_DB_KEY_NAME + key);
    }



}
