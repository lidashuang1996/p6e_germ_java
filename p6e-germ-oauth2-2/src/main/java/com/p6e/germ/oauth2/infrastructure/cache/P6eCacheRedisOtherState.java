package com.p6e.germ.oauth2.infrastructure.cache;

import java.util.concurrent.TimeUnit;

/**
 * 第三方平台登录的 STATE 数据的缓存的实现
 * 采用 REDIS 数据库进行缓存数据
 * @author lidashuang
 * @version 1.0
 */
public class P6eCacheRedisOtherState extends P6eCacheRedis implements IP6eCacheOtherState {

    /** 数据源名称 */
    private static final String SOURCE_NAME = "A";

    @Override
    public void set(String type, String key, String value) {
        getStringRedisTemplate(SOURCE_NAME).opsForValue().set(OTHER_STATE_NAME + type + ":" + key, value, OTHER_STATE_TIME, TimeUnit.SECONDS);
    }

    @Override
    public String get(String type, String key) {
        return getStringRedisTemplate(SOURCE_NAME).opsForValue().get(OTHER_STATE_NAME + type + ":" + key);
    }

    @Override
    public void del(String type, String key) {
        getStringRedisTemplate(SOURCE_NAME).delete(OTHER_STATE_NAME + type + ":" + key);
    }

}
