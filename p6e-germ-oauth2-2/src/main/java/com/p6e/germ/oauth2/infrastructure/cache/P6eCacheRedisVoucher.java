package com.p6e.germ.oauth2.infrastructure.cache;

import java.util.concurrent.TimeUnit;

/**
 * @author lidashuang
 * @version 1.0
 */
public class P6eCacheRedisVoucher extends P6eCacheRedis implements IP6eCacheVoucher {

    private static final String SOURCE_NAME = "";

    @Override
    public void set(String key, String value) {
        this.getRedisTemplate(SOURCE_NAME).opsForValue().set(VOUCHER_NAME + key, value, VOUCHER_TIME, TimeUnit.SECONDS);
    }

    @Override
    public String get(String key) {
        return this.getRedisTemplate(SOURCE_NAME).opsForValue().get(VOUCHER_NAME + key);
    }

    @Override
    public void del(String key) {
        this.getRedisTemplate(SOURCE_NAME).delete(VOUCHER_NAME + key);
    }

}
