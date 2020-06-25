package com.p6e.germ.oauth2.cache;

/**
 * 客户端缓存实现类
 * @author lidashuang
 * @version 1.0
 */
public class P6eCacheRedisClient extends P6eCacheRedis implements IP6eCacheClient {

    @Override
    public void set(String key, String value) {
        redisTemplate.opsForValue().set(CLIENT_NAME + key, value);
    }

    @Override
    public void del(String key) {
        redisTemplate.delete(CLIENT_NAME + key);
    }

    @Override
    public String get(String key) {
        return redisTemplate.opsForValue().get(CLIENT_NAME + key);
    }

}
