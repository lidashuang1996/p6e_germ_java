package com.p6e.germ.oauth2.infrastructure.cache;

/**
 * 认证缓存的接口
 * @author lidashuang
 * @version 1.0
 */
public interface IP6eCacheAuth {

    /** 缓存名称 */
    String AUTH_NAME = "P6E:OAUTH2:AUTH:";

    /** 缓存时间 5分钟 */
    long AUTH_TIME = 300;

    /**
     * 写入数据
     * @param key 数据名称
     * @param value 数据内容
     */
    public void set(String key, String value);

    /**
     * 读取数据
     * @param key 数据名称
     */
    public String get(String key);

    /**
     * 删除数据
     * @param key 数据名称
     */
    public void del(String key);

}
