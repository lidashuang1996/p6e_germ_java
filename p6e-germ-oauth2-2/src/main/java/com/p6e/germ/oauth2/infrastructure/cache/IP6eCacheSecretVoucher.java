package com.p6e.germ.oauth2.infrastructure.cache;

/**
 * 凭证的缓存接口
 * @author lidashuang
 * @version 1.0
 */
public interface IP6eCacheSecretVoucher {

    /** 凭证缓存的前缀名称 */
    String VOUCHER_NAME = "P6E:OAUTH2:VOUCHER:";

    /** 凭证缓存的过期时间 */
    long VOUCHER_TIME = 300;

    /**
     * 写入数据
     * @param key 数据名称
     * @param value 数据内容
     */
    public void set(String key, String value);

    /**
     * 读取数据
     * @param key 数据名称
     * @return 数据内容
     */
    public String get(String key);

    /**
     * 删除数据
     * @param key 数据名称
     */
    public void del(String key);

}
