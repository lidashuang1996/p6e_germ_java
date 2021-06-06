package com.p6e.germ.oauth2.domain.entity;

import com.p6e.germ.common.utils.P6eGeneratorUtil;
import com.p6e.germ.common.utils.P6eJsonUtil;
import com.p6e.germ.oauth2.domain.keyvalue.P6eTokenKeyValue;
import com.p6e.germ.oauth2.infrastructure.cache.IP6eCacheToken;
import com.p6e.germ.oauth2.infrastructure.cache.P6eCache;

import java.io.Serializable;
import java.util.Map;

/**
 * @author lidashuang
 * @version 1.0
 */
public class P6eTokenEntity implements Serializable {

    private String key;
    private final String id;
//    private final Map<String, String> kCache;
    private final Map<String, String> data;
    private final P6eTokenKeyValue.Content content;
    private final IP6eCacheToken cache = P6eCache.token;


    public static P6eTokenEntity get(String key) {
        return new P6eTokenEntity(key);
    }

    public static P6eTokenEntity get(P6eTokenKeyValue.AccessToken token) {
        return new P6eTokenEntity(token);
    }

    public static P6eTokenEntity get(P6eTokenKeyValue.RefreshToken token) {
        return new P6eTokenEntity(token);
    }

    public static P6eTokenEntity create(P6eTokenKeyValue.CodeParam codeParam, P6eTokenKeyValue.DataParam dataParam) {
        return new P6eTokenEntity(codeParam, dataParam);
    }

    private P6eTokenEntity(String key) {
        try {
            final P6eTokenKeyValue.Content content = P6eJsonUtil.fromJson(cache.get(key), P6eTokenKeyValue.Content.class);
            final P6eTokenKeyValue.Voucher voucher =
                    P6eJsonUtil.fromJson(cache.getAccessToken(content.getAccessToken()), P6eTokenKeyValue.Voucher.class);
            this.id = voucher.getId();
            this.data = P6eJsonUtil.fromJsonToMap(cache.getUser(voucher.getId()), String.class, String.class);
            this.content = new P6eTokenKeyValue.Content(
                    voucher.getAccessToken(),
                    voucher.getRefreshToken(),
                    "bearer",
                    IP6eCacheToken.TOKEN_TIME
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private P6eTokenEntity(P6eTokenKeyValue.AccessToken token) {
        try {
            final P6eTokenKeyValue.Voucher voucher =
                    P6eJsonUtil.fromJson(cache.getAccessToken(token.getContent()), P6eTokenKeyValue.Voucher.class);
            this.id = voucher.getId();
            this.data = P6eJsonUtil.fromJsonToMap(cache.getUser(voucher.getId()), String.class, String.class);
            this.content = new P6eTokenKeyValue.Content(
                    voucher.getAccessToken(),
                    voucher.getRefreshToken(),
                    "bearer",
                    IP6eCacheToken.TOKEN_TIME
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private P6eTokenEntity(P6eTokenKeyValue.RefreshToken token) {
        try {
            final P6eTokenKeyValue.Voucher voucher =
                    P6eJsonUtil.fromJson(cache.getRefreshToken(token.getContent()), P6eTokenKeyValue.Voucher.class);
            this.id = voucher.getId();
            this.data = P6eJsonUtil.fromJsonToMap(cache.getUser(voucher.getId()), String.class, String.class);
            this.content = new P6eTokenKeyValue.Content(
                    voucher.getAccessToken(),
                    voucher.getRefreshToken(),
                    "bearer",
                    IP6eCacheToken.TOKEN_TIME
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private P6eTokenEntity(P6eTokenKeyValue.CodeParam codeParam, P6eTokenKeyValue.DataParam dataParam) {
        this.key = P6eGeneratorUtil.uuid();
        this.id = dataParam.getId();
        this.data = dataParam.getContent();
        this.content = new P6eTokenKeyValue.Content(
                P6eGeneratorUtil.uuid(),
                P6eGeneratorUtil.uuid(),
                "bearer",
                IP6eCacheToken.TOKEN_TIME
        );
    }


    /**
     * 缓存
     */
    public P6eTokenEntity cache() {
        // 缓存认证信息
        cache.set(key, P6eJsonUtil.toJson(content));
        // 缓存用户信息
        cache.setUser(id, P6eJsonUtil.toJson(data));
        // 写入缓存数据
        final P6eTokenKeyValue.Voucher voucher =
                new P6eTokenKeyValue.Voucher(id, content.getAccessToken(), content.getRefreshToken());
        cache.setAccessToken(content.getAccessToken(), P6eJsonUtil.toJson(voucher));
        cache.setRefreshToken(content.getRefreshToken(), P6eJsonUtil.toJson(voucher));
        return this;
    }

    /**
     * 删除 access token 的内容
     */
    public void delAccessToken() {
        cache.delAccessToken(content.getAccessToken());
    }

    /**
     * 删除 refresh token 的内容
     */
    public void delRefreshToken() {
        cache.delRefreshToken(content.getRefreshToken());
    }

    /**
     * 设置 access token 的过期时间戳
     * @param time 时间戳
     */
    public void setAccessTokenExpirationTime(long time) {
        cache.setAccessTokenExpirationTime(content.getAccessToken(), time);
    }

    /**
     * 设置 refresh token 的过期时间戳
     * @param time 时间戳
     */
    public void setRefreshTokenExpirationTime(long time) {
        cache.setRefreshTokenExpirationTime(content.getRefreshToken(), time);
    }

    /**
     * 验证 refresh token 数据
     * @param refresh refresh token 数据
     * @return 是否相同
     */
    public boolean verificationRefreshToken(String refresh) {
        return content.getRefreshToken().equals(refresh);
    }

    /**
     * 获取 KEY
     * @return KEY 数据
     */
    public String getKey() {
        return key;
    }

    /**
     * 返回缓存数据
     * @return 缓存数据对象
     */
    public Map<String, String> getData() {
        return data;
    }

    /**
     * 获取模型
     * @return 模型对象
     */
    public P6eTokenKeyValue.Content getContent() {
        return content;
    }

    /**
     * 刷新对象
     * @return 本身对象
     */
    public P6eTokenEntity refresh() {
        cache.del(key);
        cache.delAccessToken(content.getAccessToken());
        cache.delRefreshToken(content.getRefreshToken());
        return new P6eTokenEntity(null, null).cache();
    }
}
