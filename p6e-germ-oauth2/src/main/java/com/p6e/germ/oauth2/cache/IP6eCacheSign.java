package com.p6e.germ.oauth2.cache;

/**
 * @author lidashuang
 * @version 1.0
 */
public interface IP6eCacheSign {

    String SIGN_COOKIE_NAME = "P6E:SIGN:COOKIE:";

    long SIGN_COOKIE_TIME = 3600L;

    public void set(String key, String value);
    public void del(String key);
    public String get(String key);
}
