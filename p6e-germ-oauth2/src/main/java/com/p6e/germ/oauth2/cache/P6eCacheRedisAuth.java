package com.p6e.germ.oauth2.cache;

import java.util.concurrent.TimeUnit;

/**
 * 认证缓存的服务
 * @author lidashuang
 * @version 1.0
 */
public class P6eCacheRedisAuth extends P6eCacheRedis implements IP6eCacheAuth {

    @Override
    public void setCodeVoucher(String key, String value) {
        redisTemplate.opsForValue().set(AUTH_NAME + key, value, AUTH_TIME, TimeUnit.SECONDS);
    }
}
