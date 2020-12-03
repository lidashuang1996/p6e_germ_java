package com.p6e.germ.oauth2.domain.entity;

import com.p6e.germ.oauth2.infrastructure.cache.IP6eCacheAuth;
import com.p6e.germ.oauth2.infrastructure.utils.P6eSpringUtil;
import lombok.Getter;

import java.io.Serializable;

/**
 * @author lidashuang
 * @version 1.0
 */
public class P6eAuthEntity implements Serializable {

    /** 缓存的内容 */
    private final String key;

    /** 缓存的内容 */
    private final String value;

    /** 注入缓存对象 */
    private final IP6eCacheAuth p6eCacheAuth = P6eSpringUtil.getBean(IP6eCacheAuth.class);

    /**
     * 构造创建
     * @param key key
     */
    public P6eAuthEntity(String key) {
        this.key = key;
        this.value = p6eCacheAuth.get(key);
        if (this.value == null) {
            throw new NullPointerException(this.getClass() + " construction fetch key ==> NullPointerException.");
        }
        this.clean();
    }

    /**
     * 构造创建
     * @param key key
     * @param value value
     */
    public P6eAuthEntity(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public void cache() {
        p6eCacheAuth.set(key, value);
    }

    public void clean() {
        p6eCacheAuth.del(key);
    }

}
