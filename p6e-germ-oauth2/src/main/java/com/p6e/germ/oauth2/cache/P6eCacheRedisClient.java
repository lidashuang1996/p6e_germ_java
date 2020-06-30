package com.p6e.germ.oauth2.cache;

import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * 客户端缓存实现类
 * @author lidashuang
 * @version 1.0
 */
@Component
public class P6eCacheRedisClient extends P6eCacheRedis implements IP6eCacheClient {

    @Override
    public void set(String key, String value) {
        p6eRedisTemplate.getRedisTemplate().opsForValue().set(CLIENT_NAME + key, value, CLIENT_TIME, TimeUnit.SECONDS);
    }

    @Override
    public void del(String key) {
        p6eRedisTemplate.getRedisTemplate().delete(CLIENT_NAME + key);
    }

    @Override
    public String get(String key) {
        return p6eRedisTemplate.getRedisTemplate().opsForValue().get(CLIENT_NAME + key);
    }

}
