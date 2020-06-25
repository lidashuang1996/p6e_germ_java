package com.p6e.germ.oauth2.cache;

/**
 * @author lidashuang
 * @version 1.0
 */
public interface IP6eCacheSign {

    String SIGN_USER_NAME = "P6E:SIGN:USER:";
    String SIGN_TOKEN_NAME = "P6E:SIGN:TOKEN:";
    String SIGN_REFRESH_TOKEN_NAME = "P6E:SIGN:REFRESH_TOKEN:";

    long SIGN_DATE_TIME = 86400L;

    public P6eCacheRedisSignModel.Core set(String id, Object data);
    public P6eCacheRedisSignModel.Core del(String token);
    public P6eCacheRedisSignModel.Core get(String token);
}
