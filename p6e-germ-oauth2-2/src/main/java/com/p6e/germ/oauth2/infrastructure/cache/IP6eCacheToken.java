package com.p6e.germ.oauth2.infrastructure.cache;

/**
 * @author lidashuang
 * @version 1.0
 */
public interface IP6eCacheToken {
    String TOKEN_NAME = "P6E:TOKEN:";
    long TOKEN_TIME = 3600L;

    public void setAccessToken(String key, String value);
    public void setRefreshToken(String key, String value);
    public void setUserInfo(String key, String value);

    public void del(String key);
    public String get(String key);
}
