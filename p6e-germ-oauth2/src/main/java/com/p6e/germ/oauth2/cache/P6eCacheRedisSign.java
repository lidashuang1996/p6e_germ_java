package com.p6e.germ.oauth2.cache;

import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * 该类实现了 sign 模式缓存用户信息
 * 通过写入、读取、删除缓存实现用户的认证功能
 *
 * 认证可以有三种模式:
 * 1. 一个账号同时有一个人使用，后来使前者退出登录
 * 2. 一个账号同时有多个人使用，后来不会对前者产生影响
 * 3. 一个账号同时多个设备使用，相同设备登录会使前者退出登录
 *
 * 该类现在默认实现 一个账号多个人使用
 * 你可以在这里修改代码从而切换为其他模式，来适应你需要的场景
 *
 * @author lidashuang
 * @version 1.0
 */
@Component
public class P6eCacheRedisSign extends P6eCacheRedis implements IP6eCacheSign {

    @Override
    public void set(String key, String value) {
        p6eRedisTemplate.getRedisTemplate().opsForValue().set(
                SIGN_COOKIE_NAME + key, value, SIGN_COOKIE_TIME, TimeUnit.SECONDS);
    }

    @Override
    public void del(String key) {
        p6eRedisTemplate.getRedisTemplate().delete(SIGN_COOKIE_NAME + key);
    }

    @Override
    public String get(String key) {
        return p6eRedisTemplate.getRedisTemplate().opsForValue().get(SIGN_COOKIE_NAME + key);
    }
}
