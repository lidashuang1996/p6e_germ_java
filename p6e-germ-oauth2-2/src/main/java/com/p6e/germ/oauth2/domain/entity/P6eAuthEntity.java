package com.p6e.germ.oauth2.domain.entity;

import com.p6e.germ.common.utils.P6eGeneratorUtil;
import com.p6e.germ.oauth2.domain.keyvalue.P6eAuthKeyValue;
import com.p6e.germ.oauth2.infrastructure.cache.IP6eCacheAuth;
import com.p6e.germ.oauth2.infrastructure.cache.P6eCache;

/**
 * @author lidashuang
 * @version 1.0
 */
public class P6eAuthEntity {

    private final String key;
    private final String value;

    private final IP6eCacheAuth auth = P6eCache.auth;

    public static P6eAuthEntity create(P6eAuthKeyValue.Content value) {
        return new P6eAuthEntity(value);
    }

    public static P6eAuthEntity get(P6eAuthKeyValue.Key key) {
        return new P6eAuthEntity(key);
    }

    private P6eAuthEntity(P6eAuthKeyValue.Key key) {
        this.key = key.getContent();
        this.value = auth.get(this.key);
    }

    private P6eAuthEntity(P6eAuthKeyValue.Content value) {
        this.key = P6eGeneratorUtil.uuid();
        this.value = value.getContent();
        auth.set(this.key, this.value);
        if (this.key == null || this.value == null) {
            throw new RuntimeException();
        }
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public P6eAuthEntity clean() {
        return this;
    }

}
