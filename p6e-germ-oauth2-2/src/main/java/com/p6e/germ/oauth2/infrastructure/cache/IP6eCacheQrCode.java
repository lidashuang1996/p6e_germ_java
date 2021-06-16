package com.p6e.germ.oauth2.infrastructure.cache;

/**
 * 扫码数据的缓存的接口
 * @author lidashuang
 * @version 1.0
 */
public interface IP6eCacheQrCode {

    /** 扫码缓存名称 */
    String QR_CODE_NAME = "P6E:OAUTH2:QR_CODE:";

    /** 扫码缓存的过期时间 */
    long QR_CODE_TIME = 180;

    /**
     * 写入扫码数据
     * @param key 数据名称
     * @param value 数据内容
     */
    public void set(String key, String value);

    /**
     * 读取扫码数据
     * @param key 数据名称
     * @return 数据内容
     */
    public String get(String key);

    /**
     * 删除扫码数据
     * @param key 数据名称
     */
    public void del(String key);
}
