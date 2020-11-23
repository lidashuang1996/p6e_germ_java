package com.p6e.germ.oauth2.domain.entity;

import com.p6e.germ.oauth2.infrastructure.repository.db.P6eClientDb;
import com.p6e.germ.oauth2.infrastructure.repository.mapper.P6eClientMapper;
import com.p6e.germ.oauth2.infrastructure.utils.CopyUtil;
import com.p6e.germ.oauth2.infrastructure.utils.GeneratorUtil;
import com.p6e.germ.oauth2.infrastructure.utils.SpringUtil;
import lombok.Getter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 客户端的实体
 * @author lidashuang
 * @version 1.0
 */
public class P6eClientEntity implements Serializable {

    /** 值对象 */
    @Getter
    private Integer id;

    @Getter
    private Integer status;

    @Getter
    private String icon;

    @Getter
    private String name;

    @Getter
    private String key;

    @Getter
    private String secret;

    @Getter
    private String scope;

    @Getter
    private String redirectUri;

    @Getter
    private String describe;

    @Getter
    private String limitingRule;

    /** 唯一标记 ID */
    private final String uniqueId;

    /** 注入映射 */
    private final P6eClientMapper p6eClientMapper = SpringUtil.getBean(P6eClientMapper.class);

    /**
     * id 读取
     * @param id 参数
     */
    private P6eClientEntity(final Integer id) {
        if (id == null) {
            throw new NullPointerException(this.getClass() + " construction() ==> id is null.");
        }
        uniqueId = GeneratorUtil.uuid();
        final P6eClientDb p6eClientDb = p6eClientMapper.queryById(id);
        if (p6eClientDb == null) {
            throw new NullPointerException(this.getClass() + " ==> construction() ==> query db id is null." );
        } else {
            CopyUtil.run(p6eClientDb, this);
        }
    }

    /**
     * key 读取
     * @param key 参数
     */
    private P6eClientEntity(final String key) {
        if (key == null) {
            throw new NullPointerException(this.getClass() + " construction() ==> key is null.");
        }
        uniqueId = GeneratorUtil.uuid();
        final P6eClientDb p6eClientDb = p6eClientMapper.queryByKey(key);
        if (p6eClientDb == null) {
            throw new NullPointerException(this.getClass() + " ==> construction() ==> query db key is null." );
        } else {
            CopyUtil.run(p6eClientDb, this);
        }
    }

    /**
     * db 方式
     * @param db 参数
     */
    private P6eClientEntity(final P6eClientDb db) {
        if (db == null) {
            throw new NullPointerException();
        }
        uniqueId = GeneratorUtil.uuid();
        CopyUtil.run(db, this);
    }

    private P6eClientEntity(final Integer status, final String name, final String describe,
                           final String scope, final String redirectUrl, final String limitingRule) {
        if (status == null
                || name == null
                || describe == null
                || scope == null
                || redirectUrl == null
                || limitingRule == null) {
            throw new NullPointerException();
        }
        if (p6eClientMapper.create(new P6eClientDb().setKey(key)) > 0) {
            uniqueId = GeneratorUtil.uuid();
            this.refresh();
        } else {
            throw new RuntimeException();
        }
    }

    public P6eClientEntity update() {
        if (id == null) {
            throw new NullPointerException();
        }
        if (p6eClientMapper.update(CopyUtil.run(this, P6eClientDb.class)) > 0) {
            this.refresh();
            return this;
        } else {
            throw new RuntimeException();
        }
    }

    public P6eClientEntity delete() {
        if (id == null) {
            throw new NullPointerException();
        }
        this.refresh();
        if (p6eClientMapper.delete(id) > 0) {
            return this;
        } else {
            throw new RuntimeException();
        }
    }

    public boolean verificationScope(final String scope) {
        // 暂无实现
        return true;
    }

    public boolean verificationSecret(final String secret) {
        return this.secret.equals(secret);
    }

    public boolean verificationRedirectUri(final String redirectUri) {
        final String[] redirectUrls = this.getRedirectUri().split(",");
        for (String url : redirectUrls) {
            if (url.equals(redirectUri)) {
                return true;
            }
        }
        return false;
    }

    public void refresh() {
        final P6eClientDb p6eClientDb = p6eClientMapper.queryById(id);
        if (p6eClientDb == null) {
            throw new NullPointerException(this.getClass() + " ==> query null." );
        } else {
            CopyUtil.run(p6eClientDb, this);
        }
    }

    @Override
    public String toString() {
        return "{"
                + "\"uniqueId\":"
                + uniqueId
                + ",\"id\":"
                + id
                + ",\"status\":"
                + status
                + ",\"name\":\""
                + name + '\"'
                + ",\"key\":\""
                + key + '\"'
                + ",\"secret\":\""
                + secret + '\"'
                + ",\"scope\":\""
                + scope + '\"'
                + ",\"redirectUrl\":\""
                + redirectUri + '\"'
                + ",\"describe\":\""
                + describe + '\"'
                + ",\"limitingRule\":\""
                + limitingRule + '\"'
                + "}";
    }

    public static P6eClientEntity fetch(String key) {
        return new P6eClientEntity(key);
    }

    public P6eTokenEntity createTokenCache() {
        final Map<String, String> map = new HashMap<>(3);
        map.put("describe", describe);
        map.put("name", name);
        map.put("id", String.valueOf(id));
        return P6eTokenEntity.create(GeneratorUtil.uuid(), String.valueOf(id), map);
    }

}
