package com.p6e.germ.oauth2.domain.entity;

import com.p6e.germ.oauth2.infrastructure.repository.db.P6eClientDb;
import com.p6e.germ.oauth2.infrastructure.repository.mapper.P6eClientMapper;
import com.p6e.germ.oauth2.infrastructure.utils.CopyUtil;
import com.p6e.germ.oauth2.infrastructure.utils.JsonUtil;
import com.p6e.germ.oauth2.infrastructure.utils.SpringUtil;
import lombok.Getter;

import java.io.Serializable;

/**
 * @author lidashuang
 * @version 1.0
 */
public class P6eClientEntity implements Serializable {

    /** 注入映射 */
    private final P6eClientMapper p6eClientMapper = SpringUtil.getBean(P6eClientMapper.class);
    /** 唯一标记 ID */
    private Long uniqueId;

    /** 值对象 */
    @Getter
    private Integer id;
    @Getter
    private Integer status;
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

    public P6eClientEntity(final Integer id) {
        if (id == null) {
            throw new NullPointerException();
        }
        uniqueId = 123L;
        final P6eClientDb p6eClientDb = p6eClientMapper.queryById(id);
        if (p6eClientDb == null) {
            throw new NullPointerException(this.getClass() + " ==> query null." );
        } else {
            CopyUtil.run(p6eClientDb, this);
        }
    }

    public P6eClientEntity(final String key) {
        if (key == null) {
            throw new NullPointerException();
        }
        uniqueId = 123L;
        final P6eClientDb p6eClientDb = p6eClientMapper.queryByKey(key);
        if (p6eClientDb == null) {
            throw new NullPointerException(this.getClass() + " ==> query null." );
        } else {
            CopyUtil.run(p6eClientDb, this);
            System.out.println(toString());
        }
    }

    public P6eClientEntity(final P6eClientDb db) {
        if (db == null) {
            throw new NullPointerException();
        }
        uniqueId = 123L;
        CopyUtil.run(db, this);
    }

    public P6eClientEntity(final Integer status, final String name, final String describe,
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
            uniqueId = 123L;
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
}
