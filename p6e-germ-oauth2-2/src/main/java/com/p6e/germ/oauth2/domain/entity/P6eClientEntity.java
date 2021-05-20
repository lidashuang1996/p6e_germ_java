package com.p6e.germ.oauth2.domain.entity;

import com.p6e.germ.common.utils.P6eGeneratorUtil;
import com.p6e.germ.common.utils.P6eJsonUtil;
import com.p6e.germ.common.utils.P6eSpringUtil;
import com.p6e.germ.oauth2.infrastructure.cache.IP6eCacheClient;
import com.p6e.germ.oauth2.infrastructure.cache.P6eCache;
import com.p6e.germ.oauth2.model.db.P6eOauth2ClientDb;
import com.p6e.germ.oauth2.infrastructure.mapper.P6eOauth2ClientMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 客户端的实体
 * @author lidashuang
 * @version 1.0
 */
public class P6eClientEntity {

    /** DB 对象 */
    private P6eOauth2ClientDb p6eOauth2ClientDb;

    /** 注入缓存服务 */
    private final IP6eCacheClient p6eCacheClient = P6eCache.client;

    /** 注入服务 */
    private final P6eOauth2ClientMapper p6eOauth2ClientMapper = P6eSpringUtil.getBean(P6eOauth2ClientMapper.class);

    public static P6eClientEntity get(int id) {
        return new P6eClientEntity(id);
    }

    public static P6eClientEntity get(String id) {
        return new P6eClientEntity(id);
    }


    /**
     * 构造创建
     * @param id id
     */
    private P6eClientEntity(Integer id) {
        try {
            final String content = p6eCacheClient.getDbId(String.valueOf(id));
            if (content == null || "".equals(content)) {
                // DB 查询数据
                this.p6eOauth2ClientDb = p6eOauth2ClientMapper.queryById(id);
                // 写入缓存数据
                final String cache = P6eJsonUtil.toJson(this.p6eOauth2ClientDb);
                p6eCacheClient.setDbId(String.valueOf(this.p6eOauth2ClientDb.getId()), cache);
                p6eCacheClient.setDbKey(this.p6eOauth2ClientDb.getKey(), cache);
            } else {
                this.p6eOauth2ClientDb = P6eJsonUtil.fromJson(content, P6eOauth2ClientDb.class);
            }
        } catch (Exception e) {
            // 出现异常就删除 id
            this.p6eOauth2ClientDb = null;
            p6eCacheClient.delDbId(String.valueOf(id));
        }
        if (this.p6eOauth2ClientDb == null) {
            throw new NullPointerException(this.getClass() + " construction data ==> NullPointerException.");
        }
    }

    /**
     * 构造创建
     * @param key key
     */
    private P6eClientEntity(String key) {
        // 去缓存中获取如果没有再去DB读取然后缓存
        try {
            final String content = p6eCacheClient.getDbKey(key);
            if (content == null || "".equals(content)) {
                // DB 查询数据
                this.p6eOauth2ClientDb = p6eOauth2ClientMapper.queryByKey(key);
                // 写入缓存数据
                final String cache = P6eJsonUtil.toJson(this.p6eOauth2ClientDb);
                p6eCacheClient.setDbId(String.valueOf(this.p6eOauth2ClientDb.getId()), cache);
                p6eCacheClient.setDbKey(this.p6eOauth2ClientDb.getKey(), cache);
            } else {
                this.p6eOauth2ClientDb = P6eJsonUtil.fromJson(content, P6eOauth2ClientDb.class);
            }
        } catch (Exception e) {
            // 出现异常就删除 key
            this.p6eOauth2ClientDb = null;
            p6eCacheClient.delDbKey(key);
        }
        if (this.p6eOauth2ClientDb == null) {
            throw new NullPointerException(this.getClass() + " construction data ==> NullPointerException.");
        }
    }

    /**
     * 构造创建
     * @param p6eOauth2ClientDb DB 对象
     */
    public P6eClientEntity(P6eOauth2ClientDb p6eOauth2ClientDb) {
        // 写入 DB 数据
        this.p6eOauth2ClientDb = p6eOauth2ClientDb;
        if (this.p6eOauth2ClientDb == null) {
            throw new NullPointerException(this.getClass() + " construction data ==> NullPointerException.");
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
        // 生产随机的编号，写入为 key 和 secret 数据
        p6eOauth2ClientDb.setKey(P6eGeneratorUtil.uuid());
        p6eOauth2ClientDb.setSecret(P6eGeneratorUtil.uuid());
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
     * 查询条数
     * @return 条数
     */
    public Long count() {
        return p6eOauth2ClientMapper.count(p6eOauth2ClientDb);
    }

    /**
     * 查询多条数据
     * @return 查询多条数据的列表
     */
    public List<P6eOauth2ClientDb> select() {
        return p6eOauth2ClientMapper.queryAll(p6eOauth2ClientDb);
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
        // 重定向的 URL 的分割
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
