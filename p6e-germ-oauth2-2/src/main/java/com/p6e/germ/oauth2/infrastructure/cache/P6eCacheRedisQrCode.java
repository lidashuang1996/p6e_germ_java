package com.p6e.germ.oauth2.infrastructure.cache;

import java.util.concurrent.TimeUnit;

/**
 * 扫码数据的缓存的实现
 * 采用 REDIS 数据库进行缓存数据
 * @author lidashuang
 * @version 1.0
 */
public class P6eCacheRedisQrCode extends P6eCacheRedis implements IP6eCacheQrCode {

    /** 数据源名称 */
    private static final String SOURCE_NAME = "A";

    @Override
    public void set(String key, String value) {
        getStringRedisTemplate(SOURCE_NAME).opsForValue().set(QR_CODE_NAME + key, value, QR_CODE_TIME, TimeUnit.SECONDS);
    }

    @Override
    public String get(String key) {
        return getStringRedisTemplate(SOURCE_NAME).opsForValue().get(QR_CODE_NAME + key);
    }

    @Override
    public void del(String key) {
        getStringRedisTemplate(SOURCE_NAME).delete(QR_CODE_NAME + key);
    }
}
