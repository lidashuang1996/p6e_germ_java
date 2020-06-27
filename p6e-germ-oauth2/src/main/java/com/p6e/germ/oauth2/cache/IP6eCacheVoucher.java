package com.p6e.germ.oauth2.cache;

/**
 * @author lidashuang
 * @version 1.0
 */
public interface IP6eCacheVoucher {

    /**
     * 生成数据
     * @param key 缓存 key
     * @return 生成的公钥
     */
    public String generate(String key);

}
