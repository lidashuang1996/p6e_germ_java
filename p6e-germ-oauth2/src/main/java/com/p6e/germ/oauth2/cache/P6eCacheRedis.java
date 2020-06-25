package com.p6e.germ.oauth2.cache;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author lidashuang
 * @version 1.0
 */
@Component
public class P6eCacheRedis implements IP6eCache {

    @Resource
    protected RedisTemplate<String, String> redisTemplate;

    @Override
    public String toType() {
        return "REDIS_TYPE";
    }
}
