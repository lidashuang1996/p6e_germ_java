package com.p6e.germ.oauth2.domain.entity;

import com.p6e.germ.oauth2.domain.keyvalue.P6eMarkKeyValue;
import com.p6e.germ.oauth2.infrastructure.cache.IP6eCacheMark;
import com.p6e.germ.oauth2.infrastructure.cache.P6eCache;
import com.p6e.germ.common.utils.P6eGeneratorUtil;
import com.p6e.germ.common.utils.P6eJsonUtil;
import java.io.Serializable;

/**
 * 记号缓存实体
 * @author lidashuang
 * @version 1.0
 */
public class P6eMarkEntity implements Serializable {

    /** 标记记号 */
    private final String key;

    /** auth key/value */
    private final P6eMarkKeyValue.Content value;

    /** 注入缓存服务 */
    private static final IP6eCacheMark CACHE_MARK = P6eCache.mark;

    public static P6eMarkEntity get(String key) {
        try {
            if (key != null) {
                final String value = CACHE_MARK.get(key);
                if (value != null) {
                    final P6eMarkKeyValue.Content content =
                            P6eJsonUtil.fromJson(value, P6eMarkKeyValue.Content.class);
                    if (content != null) {
                        return new P6eMarkEntity(key, content);
                    }
                }
            }
            throw new Exception();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static P6eMarkEntity create(P6eMarkKeyValue.Content content) {
        return new P6eMarkEntity(P6eGeneratorUtil.uuid(), content);
    }

    private P6eMarkEntity(String key, P6eMarkKeyValue.Content value) {
        this.key = key;
        this.value = value;
    }

    /**
     * 缓存
     */
    public P6eMarkEntity cache() {
        CACHE_MARK.set(key, P6eJsonUtil.toJson(value));
        return this;
    }

    /**
     * 清除缓存
     */
    public P6eMarkEntity clean() {
        // CACHE_MARK.del(key);
        return this;
    }

    /**
     * 读取记号
     * @return 记号数据
     */
    public String getKey() {
        return key;
    }

    /**
     * 读取标记对象
     * @return 标记对象
     */
    public P6eMarkKeyValue.Content getData() {
        return value;
    }

    public P6eMarkEntity refresh() {
        return this;
    }
}
