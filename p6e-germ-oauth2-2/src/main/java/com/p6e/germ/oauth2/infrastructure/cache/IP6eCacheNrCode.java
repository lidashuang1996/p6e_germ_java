package com.p6e.germ.oauth2.infrastructure.cache;

/**
 * 验证码数据的缓存的接口
 * @author lidashuang
 * @version 1.0
 */
public interface IP6eCacheNrCode {

    /** 验证码缓存名称 */
    String NR_CODE_NAME = "P6E:OAUTH2:NR_CODE:";

    /** 缓存的过期时间 */
    long NR_CODE_TIME = 180;

    /**
     * 写入验证码数据
     * @param key 数据名称
     * @param value 数据内容
     */
    public void set(String key, String value);

    /**
     * 读取验证码数据
     * @param key 数据名称
     * @return 数据内容
     */
    public String get(String key);

    /**
     * 删除验证码数据
     * @param key 数据名称
     */
    public void del(String key);
}
