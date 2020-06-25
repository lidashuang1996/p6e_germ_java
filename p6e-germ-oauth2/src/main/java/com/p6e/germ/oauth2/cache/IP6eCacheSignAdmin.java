package com.p6e.germ.oauth2.cache;

/**
 * @author lidashuang
 * @version 1.0
 */
public interface IP6eCacheSignAdmin {

    String SIGN_ADMIN_NAME = "P6E:ADMIN_SIGN:";
    long SIGN_ADMIN_TIME = 86400L;

    public P6eCacheRedisSignModel.Core set(P6eCacheRedisSignModel.Contract contract, Object data);
    public P6eCacheRedisSignModel.Core del(P6eCacheRedisSignModel.Contract contract, Class<?> tClass);
    public P6eCacheRedisSignModel.Core get(P6eCacheRedisSignModel.Contract contract, Class<?> tClass);

    void clean(P6eCacheRedisSignModel.Contract contract);
}
