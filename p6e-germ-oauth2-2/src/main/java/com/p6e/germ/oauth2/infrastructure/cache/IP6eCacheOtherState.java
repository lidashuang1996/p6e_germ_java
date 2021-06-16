package com.p6e.germ.oauth2.infrastructure.cache;

/**
 * 第三方平台登录的 STATE 数据的缓存的接口
 * @author lidashuang
 * @version 1.0
 */
public interface IP6eCacheOtherState {

    /** 第三方平台登录的 STATE 数据缓存名称 */
    String OTHER_STATE_NAME = "P6E:OAUTH2:OTHER_STATE:";

    /** 第三方平台登录的 STATE 数据缓存的过期时间 */
    long OTHER_STATE_TIME = 180;

    /**
     * 写入第三方平台登录的 STATE 数据
     * @param key 数据名称
     * @param type 平台类型
     * @param value 数据内容
     */
    public void set(String type, String key, String value);

    /**
     * 读取第三方平台登录的 STATE 数据
     * @param key 数据名称
     * @param type 平台类型
     * @return 数据内容
     */
    public String get(String type, String key);

    /**
     * 删除第三方平台登录的 STATE 数据
     * @param key 数据名称
     * @param type 平台类型
     */
    public void del(String type, String key);

}
