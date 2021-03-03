package com.p6e.germ.oauth2.infrastructure.cache;

/**
 * 认证缓存的接口
 * @author lidashuang
 * @version 1.0
 */
public interface IP6eCachePush {

    /** 缓存名称 */
    String PUSH_NAME = "P6E:OAUTH2:PUSH:";

    /** 缓存时间 5分钟 */
    long PUSH_TIME = 300;

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
