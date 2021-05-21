package com.p6e.germ.oauth2.domain.entity;

import com.p6e.germ.common.utils.P6eGeneratorUtil;
import com.p6e.germ.oauth2.infrastructure.cache.IP6eCacheQrCode;
import com.p6e.germ.oauth2.infrastructure.cache.P6eCache;

/**
 * 二维码实体
 * @author lidashuang
 * @version 1.0
 */
public class P6eQrCodeEntity {

    /** 默认的空内容 */
    private static final String NULL_CONTENT = "__NULL_CONTENT__";

    /** 注入缓存的服务 */
    private static final IP6eCacheQrCode CACHE_QR_CODE = P6eCache.qrCode;

    /** 缓存的 KEY */
    private final String key;

    /** 缓存的内容 */
    private final String value;

    /**
     * 获取二维码数据
     * @param key 参数
     * @return P6eQrCodeEntity 对象
     */
    public static P6eQrCodeEntity get(String key) {
        try {
            if (key != null) {
                final String value = CACHE_QR_CODE.get(key);
                if (value != null) {
                    return new P6eQrCodeEntity(key, value);
                }
            }
            throw new Exception();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 创建二维码数据
     * @return P6eQrCodeEntity 对象
     */
    public static P6eQrCodeEntity create() {
        return new P6eQrCodeEntity(P6eGeneratorUtil.uuid(), NULL_CONTENT);
    }

    /**
     * 构造方法
     * @param key key 数据
     * @param value value 数据
     */
    private P6eQrCodeEntity(String key, String value) {
        this.key = key;
        this.value = value;
    }

    /**
     * 获取 key 数据
     * @return key 数据
     */
    public String getKey() {
        return key;
    }

    /**
     * 获取 value 数据
     * @return value 数据
     */
    public String getValue() {
        return value.equals(NULL_CONTENT) ? null : value;
    }

    /**
     * 缓存数据
     * @return 自身对象
     */
    public P6eQrCodeEntity cache() {
        CACHE_QR_CODE.set(key, value);
        return this;
    }

}
