package com.p6e.germ.oauth2.infrastructure.cache;

/**
 * 密钥（密码）/凭证的缓存接口
 * @author lidashuang
 * @version 1.0
 */
public interface IP6eCacheSecretVoucher {

    /** 密钥（密码）/凭证缓存的名称 */
    String SECRET_VOUCHER_NAME = "P6E:OAUTH2:SECRET_VOUCHER:";

    /** 密钥（密码）/凭证缓存的过期时间 */
    long SECRET_VOUCHER_TIME = 180;

    /**
     * 写入密钥（密码）/凭证数据
     * @param key 数据名称
     * @param value 数据内容
     */
    public void set(String key, String value);

    /**
     * 读取密钥（密码）/凭证数据
     * @param key 数据名称
     * @return 数据内容
     */
    public String get(String key);

    /**
     * 删除密钥（密码）/凭证数据
     * @param key 数据名称
     */
    public void del(String key);

}
