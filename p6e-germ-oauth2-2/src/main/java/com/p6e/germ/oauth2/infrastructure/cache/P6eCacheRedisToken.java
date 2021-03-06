package com.p6e.germ.oauth2.infrastructure.cache;

import java.util.concurrent.TimeUnit;

/**
 * 用户信息/认证信息 数据的缓存接口
 * 采用 REDIS 数据库进行缓存数据
 * @author lidashuang
 * @version 1.0
 */
public class P6eCacheRedisToken extends P6eCacheRedis implements IP6eCacheToken {

    /** 数据源名称 */
    private static final String SOURCE_NAME = "A";

    @Override
    public void setKey(String key, String value) {
        getStringRedisTemplate(SOURCE_NAME).opsForValue().set(TOKEN_KEY_NAME + key, value, TOKEN_TIME, TimeUnit.SECONDS);
    }

    @Override
    public String getKey(String key) {
        return getStringRedisTemplate(SOURCE_NAME).opsForValue().get(TOKEN_KEY_NAME + key);
    }

    @Override
    public void delKey(String key) {
        getStringRedisTemplate(SOURCE_NAME).delete(TOKEN_KEY_NAME + key);
    }

    @Override
    public void setUser(String key, String value) {
        getStringRedisTemplate(SOURCE_NAME).opsForValue().set(TOKEN_USER_INFO_NAME + key, value, TOKEN_TIME, TimeUnit.SECONDS);
    }

    @Override
    public String getUser(String key) {
        return getStringRedisTemplate(SOURCE_NAME).opsForValue().get(TOKEN_USER_INFO_NAME + key);
    }

    @Override
    public void delUser(String key) {
        getStringRedisTemplate(SOURCE_NAME).delete(TOKEN_USER_INFO_NAME + key);
    }


    @Override
    public void setAccessToken(String key, String value) {
        getStringRedisTemplate(SOURCE_NAME).opsForValue().set(TOKEN_ACCESS_TOKEN_NAME + key, value, TOKEN_TIME, TimeUnit.SECONDS);
    }

    @Override
    public void setAccessTokenExpirationTime(String key, long time) {
        final String value = getAccessToken(key);
        if (value != null) {
            getStringRedisTemplate(SOURCE_NAME).opsForValue().set(TOKEN_ACCESS_TOKEN_NAME + key, value, time, TimeUnit.SECONDS);
        }
    }

    @Override
    public String getAccessToken(String key) {
        return getStringRedisTemplate(SOURCE_NAME).opsForValue().get(TOKEN_ACCESS_TOKEN_NAME + key);
    }

    @Override
    public void delAccessToken(String key) {
        getStringRedisTemplate(SOURCE_NAME).delete(TOKEN_ACCESS_TOKEN_NAME + key);
    }

    @Override
    public void setRefreshToken(String key, String value) {
        getStringRedisTemplate(SOURCE_NAME).opsForValue().set(TOKEN_REFRESH_TOKEN_NAME + key, value, TOKEN_TIME, TimeUnit.SECONDS);
    }

    @Override
    public void setRefreshTokenExpirationTime(String key, long time) {
        final String value = getRefreshToken(key);
        if (value != null) {
            getStringRedisTemplate(SOURCE_NAME).opsForValue().set(TOKEN_REFRESH_TOKEN_NAME + key, value, time, TimeUnit.SECONDS);
        }
    }

    @Override
    public String getRefreshToken(String key) {
        return getStringRedisTemplate(SOURCE_NAME).opsForValue().get(TOKEN_REFRESH_TOKEN_NAME + key);
    }

    @Override
    public void delRefreshToken(String key) {
        getStringRedisTemplate(SOURCE_NAME).delete(TOKEN_REFRESH_TOKEN_NAME + key);
    }
}
