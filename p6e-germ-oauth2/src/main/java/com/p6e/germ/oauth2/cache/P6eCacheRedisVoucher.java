package com.p6e.germ.oauth2.cache;

import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author lidashuang
 * @version 1.0
 */
@Component
public class P6eCacheRedisVoucher extends P6eCacheRedis implements IP6eCacheVoucher {

    @Override
    public void set(String key, String value) {
        p6eRedisTemplate.getRedisTemplate().opsForValue().set(VOUCHER_NAME + key, value, VOUCHER_TIME, TimeUnit.SECONDS);
    }

    @Override
    public String get(String key) {
        return p6eRedisTemplate.getRedisTemplate().opsForValue().get(VOUCHER_NAME + key);
    }
}
