package com.p6e.germ.oauth2.cache;

/**
 * @author lidashuang
 * @version 1.0
 */
public interface IP6eCacheVoucher {

    /** 凭证缓存的前缀名称 */
    String VOUCHER_NAME = "P6E:VOUCHER:";

    /** 凭证缓存的过期时间 */
    long VOUCHER_TIME = 180;

    /**
     * 写入数据
     * @param key 数据名称
     * @param value 数据内容
     */
    public void set(String key, String value);

    /**
     * 读取数据
     * @param key 数据编号
     * @return 数据内容
     */
    public String get(String key);

    public void del(String key);

}
