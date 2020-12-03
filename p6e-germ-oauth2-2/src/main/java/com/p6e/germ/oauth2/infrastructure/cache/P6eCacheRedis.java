package com.p6e.germ.oauth2.infrastructure.cache;

import com.p6e.germ.oauth2.infrastructure.utils.P6eSpringUtil;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * 缓存接口的 redis 的实现
 * @author lidashuang
 * @version 1.0
 */
public abstract class P6eCacheRedis implements IP6eCache {

    private final Map<String, StringRedisTemplate> redisTemplateMap = new HashMap<>();

    @Override
    public String toType() {
        return "REDIS-TYPE";
    }

    public StringRedisTemplate getRedisTemplate(final String key) {
        //return redisTemplateMap.get(key);
        return P6eSpringUtil.getBean(StringRedisTemplate.class);
    }
}