package com.p6e.germ.oauth2.domain.entity;

import com.p6e.germ.oauth2.infrastructure.repository.db.P6eOauth2ClientDb;
import com.p6e.germ.oauth2.infrastructure.repository.mapper.P6eOauth2ClientMapper;
import com.p6e.germ.oauth2.infrastructure.utils.GeneratorUtil;
import com.p6e.germ.oauth2.infrastructure.utils.P6eSpringUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * 客户端的实体
 * @author lidashuang
 * @version 1.0
 */
public class P6eClientEntity {

    /** DB 对象 */
    private P6eOauth2ClientDb p6eOauth2ClientDb;

    /** 注入服务 */
    private final P6eOauth2ClientMapper p6eOauth2ClientMapper = P6eSpringUtil.getBean(P6eOauth2ClientMapper.class);

    /**
     * 构造创建
     * @param id id
     */
    public P6eClientEntity(Integer id) {
        this.p6eOauth2ClientDb = p6eOauth2ClientMapper.queryById(id);
        if (this.p6eOauth2ClientDb == null) {
            throw new NullPointerException();
        }
    }

    /**
     * 构造创建
     * @param key key
     */
    public P6eClientEntity(String key) {
        this.p6eOauth2ClientDb = p6eOauth2ClientMapper.queryByKey(key);
        if (this.p6eOauth2ClientDb == null) {
            throw new NullPointerException();
        }
    }

    /**
     * 构造创建
     * @param p6eOauth2ClientDb DB 对象
     */
    public P6eClientEntity(P6eOauth2ClientDb p6eOauth2ClientDb) {
        this.p6eOauth2ClientDb = p6eOauth2ClientDb;
        if (this.p6eOauth2ClientDb == null) {
            throw new NullPointerException();
        }
    }

    /**
     * 返回内部数据
     * @return 内部数据
     */
    public P6eOauth2ClientDb get() {
        return p6eOauth2ClientDb;
    }

    /**
     * 将数据写入到数据库
     * @return 创建的数据对象
     */
    public P6eOauth2ClientDb create() {
        if (p6eOauth2ClientMapper.create(p6eOauth2ClientDb) > 0) {
            p6eOauth2ClientDb = p6eOauth2ClientMapper.queryById(p6eOauth2ClientDb.getId());
            return p6eOauth2ClientDb;
        } else {
            return null;
        }
    }

    /**
     * 删除数据
     * @return 删除的数据对象
     */
    public P6eOauth2ClientDb delete() {
        if (p6eOauth2ClientMapper.delete(p6eOauth2ClientDb.getId()) > 0) {
            return p6eOauth2ClientDb;
        } else {
            return null;
        }
    }

    /**
     * 修改数据
     * @return 修改的数据对象
     */
    public P6eOauth2ClientDb update() {
        if (p6eOauth2ClientMapper.update(p6eOauth2ClientDb) > 0) {
            p6eOauth2ClientDb = p6eOauth2ClientMapper.queryById(p6eOauth2ClientDb.getId());
            return p6eOauth2ClientDb;
        } else {
            return null;
        }
    }

    /**
     * 验证 scope 的参数
     * @param scope 参数
     * @return 验证结果
     */
    public boolean verificationScope(final String scope) {
        // 暂无实现
        return true;
    }

    /**
     * 验证 secret 参数
     * @param secret 参数
     * @return 验证结果
     */
    public boolean verificationSecret(final String secret) {
        return this.p6eOauth2ClientDb.getSecret().equals(secret);
    }

    /**
     * 验证重定向参数
     * @param redirectUri 参数
     * @return 验证结果
     */
    public boolean verificationRedirectUri(final String redirectUri) {
        final String[] redirectUrls = this.p6eOauth2ClientDb.getRedirectUri().split(",");
        for (String url : redirectUrls) {
            if (url.equals(redirectUri)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 创建登录缓存认证对象
     * @return P6eTokenEntity 认证对象
     */
    public P6eTokenEntity createTokenCache() {
        final Map<String, String> map = new HashMap<>(3);
        map.put("describe", p6eOauth2ClientDb.getDescribe());
        map.put("name", p6eOauth2ClientDb.getName());
        map.put("id", String.valueOf(p6eOauth2ClientDb.getId()));
        return new P6eTokenEntity(String.valueOf(p6eOauth2ClientDb.getId()), map);
    }
}
