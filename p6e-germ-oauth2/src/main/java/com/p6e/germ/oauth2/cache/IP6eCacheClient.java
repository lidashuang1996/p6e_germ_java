package com.p6e.germ.oauth2.cache;

/**
 * @author lidashuang
 * @version 1.0
 */
public interface IP6eCacheClient {

    /** 客户端名称 */
    String CLIENT_NAME = "P6E:DB:CLIENT:";

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

}
