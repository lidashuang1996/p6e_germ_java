package com.p6e.germ.oauth2.cache;

import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * 认证缓存的服务
 * @author lidashuang
 * @version 1.0
 */
@Component
public class P6eCacheRedisAuth extends P6eCacheRedis implements IP6eCacheAuth {

    @Override
    public void setCodeMark(String key, String value) {
        p6eRedisTemplate.getRedisTemplate().opsForValue().set(AUTH_NAME + "CODE:" + key, value, AUTH_TIME, TimeUnit.SECONDS);
    }

    @Override
    public String getCodeMark(String key) {
        return p6eRedisTemplate.getRedisTemplate().opsForValue().get(AUTH_NAME + "CODE:" + key);
    }

    @Override
    public void setCode(String key, String value) {
        p6eRedisTemplate.getRedisTemplate().opsForValue().set(AUTH_NAME + "MARK:" + key, value, AUTH_TIME, TimeUnit.SECONDS);
    }

    @Override
    public String getCode(String key) {
        return p6eRedisTemplate.getRedisTemplate().opsForValue().get(AUTH_NAME + "MARK:" + key);
    }

    @Override
    public void delCode(String key) {
        p6eRedisTemplate.getRedisTemplate().delete(AUTH_NAME + "MARK:" + key);
    }
}
