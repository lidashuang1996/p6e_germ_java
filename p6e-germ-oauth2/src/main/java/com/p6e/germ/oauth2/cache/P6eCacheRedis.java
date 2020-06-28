package com.p6e.germ.oauth2.cache;

import com.p6e.germ.oauth2.config.P6eRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author lidashuang
 * @version 1.0
 */
@Component
public class P6eCacheRedis implements IP6eCache {

    @Resource
    protected P6eRedisTemplate p6eRedisTemplate;

    @Override
    public String toType() {
        return "REDIS_TYPE";
    }

}
