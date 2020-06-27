package com.p6e.germ.oauth2.cache;

import com.p6e.germ.oauth2.utils.CommonUtil;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author lidashuang
 * @version 1.0
 */
@Component
public class P6eCacheRedisVoucher extends P6eCacheRedis implements IP6eCacheVoucher {

    @Override
    public String set(String value) {
        final String key = VOUCHER_NAME + CommonUtil.generateUUID();
        redisTemplate.opsForValue().set(key, value, VOUCHER_TIME, TimeUnit.SECONDS);
        return key;
    }

    @Override
    public String get(String key) {
        return redisTemplate.opsForValue().get(VOUCHER_NAME + key);
    }
}
