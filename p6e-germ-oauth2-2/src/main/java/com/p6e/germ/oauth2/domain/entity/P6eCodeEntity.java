package com.p6e.germ.oauth2.domain.entity;

import com.p6e.germ.oauth2.domain.keyvalue.P6eCodeKeyValue;
import com.p6e.germ.oauth2.infrastructure.cache.P6eCache;
import com.p6e.germ.oauth2.infrastructure.utils.P6eGeneratorUtil;
import com.p6e.germ.oauth2.infrastructure.utils.P6eJsonUtil;

/**
 * 扫描登录
 * @author lidashuang
 * @version 1.0
 */
public class P6eCodeEntity {

    private final String key;
    private P6eCodeKeyValue p6eCodeKeyValue;

    public P6eCodeEntity() {
        this.key = P6eGeneratorUtil.uuid();
    }

    public P6eCodeEntity(String key) {
        this.key = key;
        if (this.key == null) {
            throw new RuntimeException();
        }
    }

    public P6eCodeEntity create(String mark) {
        if (mark == null) {
            throw new RuntimeException();
        }
        this.p6eCodeKeyValue = new P6eCodeKeyValue().setMark(mark);
        return this;
    }

    public P6eCodeEntity get() {
        final String content = P6eCache.code.get(key);
        if (content == null) {
            throw new RuntimeException();
        }
        this.p6eCodeKeyValue = P6eJsonUtil.fromJson(content, P6eCodeKeyValue.class);
        if (this.p6eCodeKeyValue == null) {
            throw new RuntimeException();
        }
        return this;
    }

    public String getKey() {
        return key;
    }

    public P6eCodeKeyValue getP6eCodeKeyValue() {
        return p6eCodeKeyValue;
    }

    public boolean verificationMark(String mark) {
        if (p6eCodeKeyValue != null && p6eCodeKeyValue.getMark() != null) {
            return p6eCodeKeyValue.getMark().equals(mark);
        }
        return false;
    }

    public P6eCodeEntity cache() {
        P6eCache.code.set(this.key, P6eJsonUtil.toJson(this.p6eCodeKeyValue));
        return this;
    }

    public void clean() {
        P6eCache.code.del(this.key);
    }

    public void setData(P6eCodeKeyValue data) {
        this.p6eCodeKeyValue = data;
        if (this.p6eCodeKeyValue == null) {
            throw new RuntimeException();
        }
        this.p6eCodeKeyValue.setMark(null);
        P6eCache.code.set(this.key, P6eJsonUtil.toJson(this.p6eCodeKeyValue));
    }
}
