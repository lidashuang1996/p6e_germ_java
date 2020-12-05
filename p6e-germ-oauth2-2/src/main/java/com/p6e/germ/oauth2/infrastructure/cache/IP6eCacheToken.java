package com.p6e.germ.oauth2.infrastructure.cache;

/**
 * TOKEN 的缓存接口
 * @author lidashuang
 * @version 1.0
 */
public interface IP6eCacheToken {

    /** 缓存名称 */
    String TOKEN_KEY_NAME = "P6E:OAUTH2:TOKEN:KEY:";
    String TOKEN_USER_INFO_NAME = "P6E:OAUTH2:TOKEN:USER_INFO:";
    String TOKEN_ACCESS_TOKEN_NAME = "P6E:OAUTH2:TOKEN:ACCESS_TOKEN:";
    String TOKEN_REFRESH_TOKEN_NAME = "P6E:OAUTH2:TOKEN:REFRESH_TOKEN:";

    /** 缓存的过期时间 */
    long TOKEN_TIME = 3600L;

    /**
     * 写入数据
     * @param key 数据名称
     * @param value 数据内容
     */
    public void set(String key, String value);

    /**
     * 读取数据
     * @param key 数据名称
     * @return 数据内容
     */
    public String get(String key);

    /**
     * 删除数据
     * @param key 数据名称
     */
    public void del(String key);


    /**
     * 写入数据
     * @param key 数据名称
     * @param value 数据内容
     */
    public void setUser(String key, String value);

    /**
     * 读取数据
     * @param key 数据名称
     * @return 数据内容
     */
    public String getUser(String key);

    /**
     * 删除数据
     * @param key 数据名称
     */
    public void delUser(String key);


    /**
     * 写入数据
     * @param key 数据名称
     * @param value 数据内容
     */
    public void setRefreshToken(String key, String value);

    /**
     * 读取数据
     * @param key 数据名称
     * @return 数据内容
     */
    public String getRefreshToken(String key);

    /**
     * 删除数据
     * @param key 数据名称
     */
    public void delRefreshToken(String key);

    /**
     * 写入数据
     * @param key 数据名称
     * @param value 数据内容
     */
    public void setAccessToken(String key, String value);

    /**
     * 修改数据缓存
     * @param key 数据名称
     * @param time 缓存时间
     */
    public void setAccessTokenExpirationTime(String key, long time);

    /**
     * 读取数据
     * @param key 数据名称
     * @return 数据内容
     */
    public String getAccessToken(String key);

    /**
     * 删除数据
     * @param key 数据名称
     */
    public void delAccessToken(String key);

    /**
     * 修改数据缓存
     * @param key 数据名称
     * @param time 缓存时间
     */
    public void setRefreshTokenExpirationTime(String key, long time);
}
