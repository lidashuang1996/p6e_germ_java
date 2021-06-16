package com.p6e.germ.oauth2.infrastructure.cache;

import java.util.concurrent.TimeUnit;

/**
 * 密钥（密码）/凭证的缓存实现
 * 采用 REDIS 数据库进行缓存数据
 * @author lidashuang
 * @version 1.0
 */
public class P6eCacheRedisSecretVoucher extends P6eCacheRedis implements IP6eCacheSecretVoucher {

    /** 数据源名称 */
    private static final String SOURCE_NAME = "A";

    @Override
    public void set(String key, String value) {
        getStringRedisTemplate(SOURCE_NAME).opsForValue().set(SECRET_VOUCHER_NAME + key, value, SECRET_VOUCHER_TIME, TimeUnit.SECONDS);
    }

    @Override
    public String get(String key) {
        return getStringRedisTemplate(SOURCE_NAME).opsForValue().get(SECRET_VOUCHER_NAME + key);
    }

    @Override
    public void del(String key) {
        getStringRedisTemplate(SOURCE_NAME).delete(SECRET_VOUCHER_NAME + key);
    }

}
