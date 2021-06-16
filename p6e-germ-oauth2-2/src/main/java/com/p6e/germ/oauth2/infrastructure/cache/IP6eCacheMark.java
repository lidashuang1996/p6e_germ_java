package com.p6e.germ.oauth2.infrastructure.cache;

/**
 * 记号数据的缓存的接口
 * @author lidashuang
 * @version 1.0
 */
public interface IP6eCacheMark {

    /** 记号缓存名称 */
    String MARK_NAME = "P6E:OAUTH2:MARK:";

    /** 缓存的过期时间 */
    long MARK_TIME = 600;

    /**
     * 写入记号数据
     * @param key 数据名称
     * @param value 数据内容
     */
    public void set(String key, String value);

    /**
     * 读取记号数据
     * @param key 数据名称
     * @return 数据内容
     */
    public String get(String key);

    /**
     * 删除记号数据
     * @param key 数据名称
     */
    public void del(String key);

}
