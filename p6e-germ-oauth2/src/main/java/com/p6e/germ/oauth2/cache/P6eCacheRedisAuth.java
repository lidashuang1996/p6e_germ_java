package com.p6e.germ.oauth2.cache;

import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * 认证缓存的服务
 * @author lidashuang
 * @version 1.0
 */
@Component
public class P6eCacheRedisAuth extends P6eCacheRedis implements IP6eCacheAuth {

    @Override
    public void setCodeMark(String key, String value) {
        p6eRedisTemplate.getRedisTemplate().opsForValue().set(AUTH_NAME + "CODE:MARK:" + key, value, AUTH_TIME, TimeUnit.SECONDS);
    }

    @Override
    public String getCodeMark(String key) {
        return p6eRedisTemplate.getRedisTemplate().opsForValue().get(AUTH_NAME + "CODE:MARK:" + key);
    }

    @Override
    public void delCodeMark(String key) {
        p6eRedisTemplate.getRedisTemplate().delete(AUTH_NAME + "CODE:MARK:" + key);
    }

    @Override
    public void setCodeVoucher(String key, String value) {
        p6eRedisTemplate.getRedisTemplate().opsForValue().set(AUTH_NAME + "CODE:VOUCHER:" + key, value, AUTH_TIME, TimeUnit.SECONDS);
    }

    @Override
    public String getCodeVoucher(String key) {
        return p6eRedisTemplate.getRedisTemplate().opsForValue().get(AUTH_NAME + "CODE:VOUCHER:" + key);
    }

    @Override
    public void delCodeVoucher(String key) {
        p6eRedisTemplate.getRedisTemplate().delete(AUTH_NAME + "CODE:VOUCHER:" + key);
    }

    @Override
    public void setTokenMark(String key, String value) {
        p6eRedisTemplate.getRedisTemplate().opsForValue().set(AUTH_NAME + "TOKEN:MARK:" + key, value, AUTH_TIME, TimeUnit.SECONDS);
    }

    @Override
    public String getTokenMark(String key) {
        return p6eRedisTemplate.getRedisTemplate().opsForValue().get(AUTH_NAME + "TOKEN:MARK:" + key);
    }

    @Override
    public void delTokenMark(String key) {
        p6eRedisTemplate.getRedisTemplate().delete(AUTH_NAME + "TOKEN:MARK:" + key);
    }
}
