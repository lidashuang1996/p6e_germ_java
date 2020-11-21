package com.p6e.germ.oauth2.infrastructure.cache;

/**
 * TOKEN 的缓存接口
 * @author lidashuang
 * @version 1.0
 */
public interface IP6eCacheToken {
    /** 缓存名称 */
    String TOKEN_NAME = "P6E:TOKEN:";

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

}
