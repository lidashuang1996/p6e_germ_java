package com.p6e.germ.oauth2.domain.entity;

import com.p6e.germ.common.utils.P6eGeneratorUtil;
import com.p6e.germ.oauth2.infrastructure.cache.IP6eCacheQrCode;
import com.p6e.germ.oauth2.infrastructure.cache.P6eCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 二维码实体
 * @author lidashuang
 * @version 1.0
 */
public class P6eQrCodeEntity {

    private static final Logger LOGGER = LoggerFactory.getLogger(P6eQrCodeEntity.class);
    /** 默认的空内容 */
    private static final String NULL_CONTENT = "__@__";

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
    public static P6eQrCodeEntity create(String mark) {
        final String uuid = P6eGeneratorUtil.uuid();
        LOGGER.info("扫码登录的生成 CODE ==> " + uuid + "  CONNTENT  " + NULL_CONTENT);
        return new P6eQrCodeEntity(uuid, mark + NULL_CONTENT);
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
        final int len = 2, index = 1;
        final String[] vs = this.value.split(NULL_CONTENT);
        if (vs.length == len && vs[index] != null) {
            return vs[1];
        } else {
            return null;
        }
    }

    public boolean verificationMark(String mark) {
        System.out.println("mark ==> " + mark + "   this.value ==> " + this.value);
        return this.value.startsWith(mark + NULL_CONTENT);
    }

    /**
     * 缓存数据
     * @return 自身对象
     */
    public P6eQrCodeEntity cache() {
        CACHE_QR_CODE.set(key, value);
        return this;
    }


    public P6eQrCodeEntity setValue(String value) {
        if (this.value.endsWith(NULL_CONTENT)) {
            CACHE_QR_CODE.set(this.key, this.value + value);
            return get(this.key);
        } else {
            throw new RuntimeException();
        }
    }

    public P6eQrCodeEntity clean() {
        // CACHE_QR_CODE.del(key);
        return this;
    }
}
