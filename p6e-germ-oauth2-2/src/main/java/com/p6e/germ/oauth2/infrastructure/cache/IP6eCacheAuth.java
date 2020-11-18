package com.p6e.germ.oauth2.infrastructure.cache;

/**
 * @author lidashuang
 * @version 1.0
 */
public interface IP6eCacheAuth {

    /** 缓存名称 */
    String AUTH_NAME = "P6E:AUTH:";

    /** 缓存时间 5分钟 */
    long AUTH_TIME = 300;

    /**
     * 写入数据
     * @param key 数据名称
     * @param value 数据内容
     */
    public void setMark(String key, String value);

    public String getMark(String key);

    public void delMark(String key);


}
