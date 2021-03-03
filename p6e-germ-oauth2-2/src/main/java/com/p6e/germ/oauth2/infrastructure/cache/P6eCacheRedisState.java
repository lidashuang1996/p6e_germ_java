package com.p6e.germ.oauth2.infrastructure.cache;

import java.util.concurrent.TimeUnit;

/**
 * @author lidashuang
 * @version 1.0
 */
public class P6eCacheRedisState extends P6eCacheRedis implements IP6eCacheState {

    private static final String SOURCE_NAME = "A";

    @Override
    public void setQq(String key, String value) {
        getStringRedisTemplate(SOURCE_NAME).opsForValue().set(QQ_STATE_NAME + key, value, STATE_TIME, TimeUnit.SECONDS);
    }

    @Override
    public String getQq(String key) {
        return getStringRedisTemplate(SOURCE_NAME).opsForValue().get(QQ_STATE_NAME + key);
    }

    @Override
    public void delQq(String key) {
        getStringRedisTemplate(SOURCE_NAME).delete(QQ_STATE_NAME + key);
    }

    @Override
    public void setWeChat(String key, String value) {
        getStringRedisTemplate(SOURCE_NAME).opsForValue().set(WE_CHAT_STATE_NAME + key, value, STATE_TIME, TimeUnit.SECONDS);
    }

    @Override
    public String getWeChat(String key) {
        return getStringRedisTemplate(SOURCE_NAME).opsForValue().get(WE_CHAT_STATE_NAME + key);
    }

    @Override
    public void delWeChat(String key) {
        getStringRedisTemplate(SOURCE_NAME).delete(WE_CHAT_STATE_NAME + key);
    }
}
