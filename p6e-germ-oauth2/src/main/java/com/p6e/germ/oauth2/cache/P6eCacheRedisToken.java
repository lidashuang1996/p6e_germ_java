package com.p6e.germ.oauth2.cache;

import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author lidashuang
 * @version 1.0
 */
@Component
public class P6eCacheRedisToken extends P6eCacheRedis implements IP6eCacheToken {

    @Override
    public void setAccessToken(String key, String value) {
        p6eRedisTemplate.getRedisTemplate().opsForValue().set(TOKEN_NAME + "ACCESS_TOKEN:" + key, value, TOKEN_TIME, TimeUnit.SECONDS);
    }

    @Override
    public void setRefreshToken(String key, String value) {
        p6eRedisTemplate.getRedisTemplate().opsForValue().set(TOKEN_NAME  + "REFRESH_TOKEN:" + key, value, TOKEN_TIME, TimeUnit.SECONDS);
    }

    @Override
    public void setUserInfo(String key, String value) {
        p6eRedisTemplate.getRedisTemplate().opsForValue().set(TOKEN_NAME  + "USER_INFO:" + key, value, TOKEN_TIME, TimeUnit.SECONDS);
    }

    @Override
    public void del(String key) {
        p6eRedisTemplate.getRedisTemplate().delete(TOKEN_NAME + key);
    }

    @Override
    public String get(String key) {
        return p6eRedisTemplate.getRedisTemplate().opsForValue().get(TOKEN_NAME + key);
    }
}
