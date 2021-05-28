package com.p6e.germ.oauth2.infrastructure.cache;

/**
 * 记号的缓存的接口
 * @author lidashuang
 * @version 1.0
 */
public interface IP6eCacheOtherState {

    /** 缓存名称 */
    String MARK_NAME = "P6E:OAUTH2:MARK:";

    /** 缓存的过期时间 */
    long MARK_TIME = 300000;

    /**
     * 写入数据
     * @param key 数据名称
     * @param value 数据内容
     */
    public void set(String type, String key, String value);

    /**
     * 读取数据
     * @param key 数据名称
     * @return 数据内容
     */
    public String get(String type, String key);

    /**
     * 删除数据
     * @param key 数据名称
     */
    public void del(String type, String key);

}