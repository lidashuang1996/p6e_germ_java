package com.p6e.germ.oauth2.infrastructure.cache;

/**
 * 客户端缓存的接口
 * @author lidashuang
 * @version 1.0
 */
public interface IP6eCacheClient {

    /** 客户端名称 */
    String CLIENT_NAME = "P6E:OAUTH2:CLIENT:";
    String CLIENT_DB_ID_NAME = "P6E:OAUTH2:CLIENT_DB:ID:";
    String CLIENT_DB_KEY_NAME = "P6E:OAUTH2:CLIENT_DB:KEY:";

    /** 客户端缓存的过期时间 */
    long CLIENT_TIME = 3600;
    long CLIENT_DB_TIME = 1000;

    /**
     * 添加客户端缓存
     * @param key 缓存名称
     * @param value 缓存内容
     */
    public void set(String key, String value);

    /**
     * 删除客户端缓存
     * @param key 缓存名称
     */
    public void del(String key);

    /**
     * 获取客户端缓存
     * @param key 缓存名称
     * @return 缓存内容
     */
    public String get(String key);


    /**
     * 添加客户端缓存
     * @param key 缓存名称
     * @param value 缓存内容
     */
    public void setDbId(String key, String value);

    /**
     * 删除客户端缓存
     * @param key 缓存名称
     */
    public void delDbId(String key);

    /**
     * 获取客户端缓存
     * @param key 缓存名称
     * @return 缓存内容
     */
    public String getDbId(String key);


    /**
     * 添加客户端缓存
     * @param key 缓存名称
     * @param value 缓存内容
     */
    public void setDbKey(String key, String value);

    /**
     * 删除客户端缓存
     * @param key 缓存名称
     */
    public void delDbKey(String key);

    /**
     * 获取客户端缓存
     * @param key 缓存名称
     * @return 缓存内容
     */
    public String getDbKey(String key);


}
