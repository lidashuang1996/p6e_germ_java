package com.p6e.germ.oauth2.infrastructure.cache;

/**
 * 用户信息/认证信息数据的缓存接口
 * @author lidashuang
 * @version 1.0
 */
public interface IP6eCacheToken {

    /** 用户信息/认证信息数据缓存名称 */
    String TOKEN_KEY_NAME = "P6E:OAUTH2:TOKEN:KEY:";
    String TOKEN_USER_INFO_NAME = "P6E:OAUTH2:TOKEN:USER_INFO:";
    String TOKEN_ACCESS_TOKEN_NAME = "P6E:OAUTH2:TOKEN:ACCESS_TOKEN:";
    String TOKEN_REFRESH_TOKEN_NAME = "P6E:OAUTH2:TOKEN:REFRESH_TOKEN:";

    /** 用户信息/认证信息 数据缓存的过期时间 */
    long TOKEN_TIME = 3600L;

    /**
     * 写入 key 数据
     * @param key 数据名称
     * @param value 数据内容
     */
    public void setKey(String key, String value);

    /**
     * 读取 key 数据
     * @param key 数据名称
     * @return 数据内容
     */
    public String getKey(String key);

    /**
     * 删除 key 数据
     * @param key 数据名称
     */
    public void delKey(String key);

    /**
     * 写入用户数据
     * @param key 数据名称
     * @param value 数据内容
     */
    public void setUser(String key, String value);

    /**
     * 读取用户数据
     * @param key 数据名称
     * @return 数据内容
     */
    public String getUser(String key);

    /**
     * 删除用户数据
     * @param key 数据名称
     */
    public void delUser(String key);

    /**
     * 写入 access token 数据
     * @param key 数据名称
     * @param value 数据内容
     */
    public void setAccessToken(String key, String value);

    /**
     * 修改 access token 数据过期时间
     * @param key 数据名称
     * @param time 缓存时间
     */
    public void setAccessTokenExpirationTime(String key, long time);

    /**
     * 读取 access token 数据
     * @param key 数据名称
     * @return 数据内容
     */
    public String getAccessToken(String key);

    /**
     * 删除 access token 数据
     * @param key 数据名称
     */
    public void delAccessToken(String key);

    /**
     * 写入 refresh token 数据
     * @param key 数据名称
     * @param value 数据内容
     */
    public void setRefreshToken(String key, String value);

    /**
     * 修改 refresh token 数据过期时间
     * @param key 数据名称
     * @param time 缓存时间
     */
    public void setRefreshTokenExpirationTime(String key, long time);

    /**
     * 读取 refresh token 数据
     * @param key 数据名称
     * @return 数据内容
     */
    public String getRefreshToken(String key);

    /**
     * 删除 refresh token 数据
     * @param key 数据名称
     */
    public void delRefreshToken(String key);
}
