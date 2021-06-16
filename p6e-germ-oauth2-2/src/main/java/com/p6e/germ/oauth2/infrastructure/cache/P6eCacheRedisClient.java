package com.p6e.germ.oauth2.infrastructure.cache;

import java.util.concurrent.TimeUnit;

/**
 * 客户端数据缓存的实现
 * 采用 REDIS 数据库进行缓存数据
 * @author lidashuang
 * @version 1.0
 */
public class P6eCacheRedisClient extends P6eCacheRedis implements IP6eCacheClient {

    /** 数据源名称 */
    private static final String SOURCE_NAME = "A";

    @Override
    public void setDbId(String key, String value) {
        getStringRedisTemplate(SOURCE_NAME).opsForValue().set(CLIENT_DB_ID_NAME + key, value, CLIENT_DB_TIME, TimeUnit.SECONDS);
    }

    @Override
    public void delDbId(String key) {
        getStringRedisTemplate(SOURCE_NAME).delete(CLIENT_DB_ID_NAME + key);
    }

    @Override
    public String getDbId(String key) {
        return getStringRedisTemplate(SOURCE_NAME).opsForValue().get(CLIENT_DB_ID_NAME + key);
    }

    @Override
    public void setDbKey(String key, String value) {
        getStringRedisTemplate(SOURCE_NAME).opsForValue().set(CLIENT_DB_KEY_NAME + key, value, CLIENT_DB_TIME, TimeUnit.SECONDS);
    }

    @Override
    public void delDbKey(String key) {
        getStringRedisTemplate(SOURCE_NAME).delete(CLIENT_DB_KEY_NAME + key);
    }

    @Override
    public String getDbKey(String key) {
        return getStringRedisTemplate(SOURCE_NAME).opsForValue().get(CLIENT_DB_KEY_NAME + key);
    }
}
