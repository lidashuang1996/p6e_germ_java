package com.p6e.germ.oauth2.cache;

import org.springframework.stereotype.Component;

/**
 * @author lidashuang
 * @version 1.0
 */
@Component
public class P6eCacheRedisSignAdmin extends P6eCacheRedis implements IP6eCacheSignAdmin {

    /** 选择的认证缓存方式 */
    private static final P6eCacheRedisSignModel.Type TYPE = P6eCacheRedisSignModel.Type.M;

    @Override
    public P6eCacheRedisSignModel.Core set(P6eCacheRedisSignModel.Contract contract, Object data) {
        return TYPE.type().set(p6eRedisTemplate.getRedisTemplate(), SIGN_ADMIN_NAME, SIGN_ADMIN_TIME, contract, data);
    }

    @Override
    public P6eCacheRedisSignModel.Core del(P6eCacheRedisSignModel.Contract contract, Class<?> tClass) {
        return TYPE.type().del(p6eRedisTemplate.getRedisTemplate(), SIGN_ADMIN_NAME, contract, tClass);
    }

    @Override
    public P6eCacheRedisSignModel.Core get(P6eCacheRedisSignModel.Contract contract, Class<?> tClass) {
        return TYPE.type().get(p6eRedisTemplate.getRedisTemplate(), SIGN_ADMIN_NAME, contract, tClass);
    }

    @Override
    public void clean(P6eCacheRedisSignModel.Contract contract) {
        TYPE.type().clean(p6eRedisTemplate.getRedisTemplate(), SIGN_ADMIN_NAME, contract);
    }
}
