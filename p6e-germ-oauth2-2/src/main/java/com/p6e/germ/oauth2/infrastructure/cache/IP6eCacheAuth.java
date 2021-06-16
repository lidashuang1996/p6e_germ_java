package com.p6e.germ.oauth2.infrastructure.cache;

/**
 * 认证数据缓存的接口
 * @author lidashuang
 * @version 1.0
 */
public interface IP6eCacheAuth {

    /** 认证数据缓存名称 */
    String AUTH_NAME = "P6E:OAUTH2:AUTH:";

    /** 认证数据缓存时间 5分钟 */
    long AUTH_TIME = 300;

    /**
     * 写入认证数据
     * @param key 数据名称
     * @param value 数据内容
     */
    public void set(String key, String value);

    /**
     * 读取认证数据
     * @param key 数据名称
     * @return 数据内容
     */
    public String get(String key);

    /**
     * 删除认证数据
     * @param key 数据名称
     */
    public void del(String key);

}
