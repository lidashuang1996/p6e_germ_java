package com.p6e.germ.jurisdiction.infrastructure.cache;

/**
 * @author lidashuang
 * @version 1.0
 */
public interface IP6eCacheJurisdiction {

    /**
     * 写入一条数据
     * @param key 数据的 KEY
     * @param value 数据的 VALUE
     */
    public void set(String key, String value);

    /**
     * 获取一条数据
     * @param key 数据的 KEY
     * @return 数据的 VALUE 的数据
     */
    public String get(String key);

    /**
     * 删除一条数据
     * @param key 数据的 KEY
     */
    public void del(String key);

}
