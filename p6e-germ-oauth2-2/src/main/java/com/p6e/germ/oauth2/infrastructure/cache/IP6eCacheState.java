package com.p6e.germ.oauth2.infrastructure.cache;

/**
 * @author lidashuang
 * @version 1.0
 */
public interface IP6eCacheState {

    /** 凭证缓存的前缀名称 */
    String QQ_STATE_NAME = "P6E:OAUTH2:STATE:QQ:";
    String WE_CHAT_STATE_NAME = "P6E:OAUTH2:STATE:WE_CHAT:";

    /** 凭证缓存的过期时间 */
    long STATE_TIME = 300;

    /**
     * 写入数据
     * @param key 数据名称
     * @param value 数据内容
     */
    public void setQq(String key, String value);

    /**
     * 读取数据
     * @param key 数据名称
     * @return 数据内容
     */
    public String getQq(String key);

    /**
     * 删除数据
     * @param key 数据名称
     */
    public void delQq(String key);

    /**
     * 写入数据
     * @param key 数据名称
     * @param value 数据内容
     */
    public void setWeChat(String key, String value);

    /**
     * 读取数据
     * @param key 数据名称
     * @return 数据内容
     */
    public String getWeChat(String key);

    /**
     * 删除数据
     * @param key 数据名称
     */
    public void delWeChat(String key);

}
