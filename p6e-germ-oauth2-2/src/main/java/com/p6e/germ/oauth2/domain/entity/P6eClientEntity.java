package com.p6e.germ.oauth2.domain.entity;

import com.p6e.germ.common.utils.P6eCopyUtil;
import com.p6e.germ.common.utils.P6eJsonUtil;
import com.p6e.germ.common.utils.P6eSpringUtil;
import com.p6e.germ.oauth2.domain.keyvalue.P6eClientKeyValue;
import com.p6e.germ.oauth2.infrastructure.cache.IP6eCacheClient;
import com.p6e.germ.oauth2.infrastructure.cache.P6eCache;
import com.p6e.germ.oauth2.infrastructure.mapper.P6eOauth2ClientMapper;

/**
 * 客户端的实体
 * @author lidashuang
 * @version 1.0
 */
public class P6eClientEntity {

    /** DB 对象 */
    private P6eClientKeyValue.Content data;

    /** 注入缓存服务 */
    private final IP6eCacheClient cache = P6eCache.client;

    /** 注入服务 */
    private final P6eOauth2ClientMapper mapper = P6eSpringUtil.getBean(P6eOauth2ClientMapper.class);

    public static P6eClientEntity get(P6eClientKeyValue.Id id) {
        return new P6eClientEntity(id);
    }

    public static P6eClientEntity get(P6eClientKeyValue.Key key) {
        return new P6eClientEntity(key);
    }

    public static P6eClientEntity create(P6eClientKeyValue.Content data) {
        return new P6eClientEntity(data);
    }

    /**
     * 构造创建
     * @param id id
     */
    private P6eClientEntity(P6eClientKeyValue.Id id) {
        try {
            final String content = cache.getDbId(String.valueOf(id));
            if (content == null || "".equals(content)) {
                // DB 查询数据
                this.data = P6eCopyUtil.run(mapper.queryById(id.getId()), P6eClientKeyValue.Content.class);
                // 写入缓存数据
                final String cacheContent = P6eJsonUtil.toJson(this.data);
                cache.setDbId(String.valueOf(this.data.getId()), cacheContent);
                cache.setDbKey(this.data.getKey(), cacheContent);
            } else {
                this.data = P6eJsonUtil.fromJson(content, P6eClientKeyValue.Content.class);
            }
        } catch (Exception e) {
            // 出现异常就删除 id
            this.data = null;
            cache.delDbId(String.valueOf(id.getId()));
        }
        if (this.data == null) {
            throw new NullPointerException(this.getClass() + " construction data ==> NullPointerException.");
        }
    }

    /**
     * 构造创建
     * @param key key
     */
    private P6eClientEntity(P6eClientKeyValue.Key key) {
        // 去缓存中获取如果没有再去DB读取然后缓存
        try {
            final String content = cache.getDbKey(key.getKey());
            if (content == null || "".equals(content)) {
                // DB 查询数据
                this.data = P6eCopyUtil.run(mapper.queryByKey(key.getKey()), P6eClientKeyValue.Content.class);
                // 写入缓存数据
                final String cacheContent = P6eJsonUtil.toJson(this.data);
                cache.setDbId(String.valueOf(this.data.getId()), cacheContent);
                cache.setDbKey(this.data.getKey(), cacheContent);
            } else {
                this.data = P6eJsonUtil.fromJson(content, P6eClientKeyValue.Content.class);
            }
        } catch (Exception e) {
            // 出现异常就删除 key
            this.data = null;
            cache.delDbKey(key.getKey());
        }
        if (this.data == null) {
            throw new NullPointerException(this.getClass() + " construction data ==> NullPointerException.");
        }
    }

    private P6eClientEntity(P6eClientKeyValue.Content data) {
        this.data = data;
        if (this.data == null) {
            throw new RuntimeException();
        }
    }

    /**
     * 返回内部数据
     * @return 内部数据
     */
    public P6eClientKeyValue.Content getData() {
        return data;
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


    public boolean isEnable() {
        return true;
    }

    /**
     * 验证 secret 参数
     * @param secret 参数
     * @return 验证结果
     */
    public boolean verificationSecret(final String secret) {
        return data.getSecret().equals(secret);
    }

    /**
     * 验证重定向参数
     * @param redirectUri 参数
     * @return 验证结果
     */
    public boolean verificationRedirectUri(final String redirectUri) {
        // 重定向的 URL 的分割
        final String[] redirectUrls = data.getRedirectUri().split(",");
        for (String url : redirectUrls) {
            if (url.equals(redirectUri)) {
                return true;
            }
        }
        return false;
    }


}
