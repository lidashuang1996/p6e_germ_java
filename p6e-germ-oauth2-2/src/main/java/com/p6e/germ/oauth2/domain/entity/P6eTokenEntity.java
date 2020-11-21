package com.p6e.germ.oauth2.domain.entity;

import com.p6e.germ.oauth2.infrastructure.cache.IP6eCacheToken;
import com.p6e.germ.oauth2.infrastructure.utils.GeneratorUtil;
import com.p6e.germ.oauth2.infrastructure.utils.SpringUtil;
import lombok.Getter;

import java.io.Serializable;

/**
 * @author lidashuang
 * @version 1.0
 */
public class P6eTokenEntity implements Serializable {

    /** 唯一标记 */
    private final String uniqueId;

    /** 缓存的内容 */
    @Getter
    private final String key;

    /** 缓存的内容 */
    @Getter
    private final String value;

    /** 注入缓存对象 */
    private final IP6eCacheToken p6eCacheToken = SpringUtil.getBean(IP6eCacheToken.class);

    public static P6eTokenEntity fetch(String key) {
        return new P6eTokenEntity(key);
    }

    /**
     * 创建的方式获取
     * @param key key
     * @param value value
     * @return P6eAuthEntity 对象
     */
    public static P6eTokenEntity create(String key, String value) {
        return new P6eTokenEntity(key, value);
    }

    /**
     * 构造创建
     * @param key key
     */
    private P6eTokenEntity(String key) {
        this.key = key;
        this.value = p6eCacheToken.get(key);
        if (this.value == null) {
            throw new NullPointerException(this.getClass() + " construction fetch key ==> NullPointerException.");
        }
        this.uniqueId = GeneratorUtil.uuid();
    }

    /**
     * 构造创建
     * @param key key
     * @param value value
     */
    private P6eTokenEntity(String key, String value) {
        this.key = key;
        this.value = value;
        this.uniqueId = GeneratorUtil.uuid();
        this.cache();
    }

    public void cache() {
        p6eCacheToken.set(key, value);
    }

    public P6eTokenEntity refresh(String data) {
        p6eCacheToken.del(this.key);
        return P6eTokenEntity.create(data, this.value);
    }

}
