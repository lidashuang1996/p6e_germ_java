package com.p6e.germ.oauth2.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * redis config
 * 这个配置文件的目的是
 *  1. 创建 redis 对象
 *  2. 创建 redis 多数据源对象
 * @author lidashuang
 * @version 1.0
 */
@Component
public class P6eRedisTemplate {

    private final RedisTemplate<String, String> redisTemplate;

    @Autowired
    public P6eRedisTemplate(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public RedisTemplate<String, String> getRedisTemplate() {
        return redisTemplate;
    }
}
