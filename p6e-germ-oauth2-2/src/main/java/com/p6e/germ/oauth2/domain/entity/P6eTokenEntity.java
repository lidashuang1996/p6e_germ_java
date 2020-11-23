package com.p6e.germ.oauth2.domain.entity;

import com.p6e.germ.oauth2.infrastructure.cache.IP6eCacheToken;
import com.p6e.germ.oauth2.infrastructure.exception.ParamException;
import com.p6e.germ.oauth2.infrastructure.utils.GeneratorUtil;
import com.p6e.germ.oauth2.infrastructure.utils.JsonUtil;
import com.p6e.germ.oauth2.infrastructure.utils.SpringUtil;
import lombok.Getter;

import java.io.Serializable;
import java.util.Map;

/**
 * @author lidashuang
 * @version 1.0
 */
public class P6eTokenEntity implements Serializable {

    /** 唯一标记 */
    private final String uniqueId;

    /** 缓存的内容 */
    @Getter
    private String key;

    /** 缓存的内容 */
    @Getter
    private Model model;

    /** 缓存的内容 */
    @Getter
    private final String uid;

    /** 缓存的内容 */
    @Getter
    private final Map<String, String> value;

    /** 注入缓存对象 */
    private final IP6eCacheToken p6eCacheToken = SpringUtil.getBean(IP6eCacheToken.class);

    /**
     * 获取的方式获取
     * @param key key
     * @return P6eAuthEntity 对象
     */
    public static P6eTokenEntity fetch(String key) {
        return new P6eTokenEntity(key);
    }


    /**
     * 获取的方式获取
     * @param accessToken 生成的 token 数据
     * @return P6eAuthEntity 对象
     */
    public static P6eTokenEntity fetchAccessToken(String accessToken) {
        return new P6eTokenEntity(accessToken, "ACCESS_TOKEN");
    }

    /**
     * 获取的方式获取
     * @param refreshToken 生成的 token 数据
     * @return P6eAuthEntity 对象
     */
    public static P6eTokenEntity fetchRefreshToken(String refreshToken) {
        return new P6eTokenEntity(refreshToken, "REFRESH_TOKEN");
    }


    /**
     * 创建的方式获取
     * @param key key
     * @param value value
     * @return P6eAuthEntity 对象
     */
    public static P6eTokenEntity create(String key, String uid, Map<String, String> value) {
        return new P6eTokenEntity(key, uid, value);
    }

    /**
     * 构造创建
     * @param key key
     */
    private P6eTokenEntity(String key) {
        this.key = key;
        final String content = p6eCacheToken.get(key);
        if (content == null) {
            throw new NullPointerException(this.getClass() + " construction fetch key ==> NullPointerException.");
        }
        this.model = JsonUtil.fromJson(content, Model.class);
        if (this.model == null) {
            throw new NullPointerException(this.getClass() + " construction fetch key ==> NullPointerException.");
        }
        this.uid = p6eCacheToken.getAccessToken(this.model.getAccessToken());
        if (this.uid == null) {
            throw new NullPointerException(this.getClass() + " construction fetch key ==> NullPointerException.");
        }
        final String user = p6eCacheToken.getUser(this.uid);
        if (user == null) {
            throw new NullPointerException(this.getClass() + " construction fetch key ==> NullPointerException.");
        }
        this.value = JsonUtil.fromJsonToMap(user, String.class, String.class);
        this.uniqueId = GeneratorUtil.uuid();
    }

    /**
     * 构造创建
     * @param token token
     * @param type 类型
     */
    private P6eTokenEntity(String token, String type) {
        switch (type) {
            case "ACCESS_TOKEN":
                this.uid = p6eCacheToken.getAccessToken(token);
                break;
            case "REFRESH_TOKEN":
                this.uid = p6eCacheToken.getRefreshToken(token);
                break;
            default:
                throw new ParamException();
        }
        if (this.uid == null) {
            throw new NullPointerException();
        }
        final String content = p6eCacheToken.getUser(this.uid);
        if (content == null) {
            throw new NullPointerException();
        }
        this.value = JsonUtil.fromJsonToMap(content, String.class, String.class);
        if (this.value == null) {
            throw new NullPointerException();
        }
        this.uniqueId = GeneratorUtil.uuid();
    }

    /**
     * 构造创建
     * @param key key
     * @param value value
     */
    private P6eTokenEntity(String key, String uid, Map<String, String> value) {
        this.key = key;
        this.uid = uid;
        this.value = value;
        this.model = new Model(
                GeneratorUtil.uuid(),
                GeneratorUtil.uuid(),
                "bearer",
                IP6eCacheToken.TOKEN_TIME
        );
        this.uniqueId = GeneratorUtil.uuid();
        this.cache();
    }

    /**
     * 缓存
     */
    public void cache() {
        // 缓存认证信息
        p6eCacheToken.set(key, JsonUtil.toJson(model));
        // 缓存用户信息
        p6eCacheToken.setUser(uid, JsonUtil.toJson(value));
        // 缓存 token 对应用户信息
        p6eCacheToken.setAccessToken(model.getAccessToken(), uid);
        p6eCacheToken.setRefreshToken(model.getRefreshToken(), uid);
    }

    /**
     * 删除 refresh token 的内容
     */
    public void delRefreshToken() {
        p6eCacheToken.delRefreshToken(this.model.getRefreshToken());
    }

    /**
     * 设置 access token 的过期时间戳
     * @param time 时间戳
     */
    public void setAccessTokenExpirationTime(long time) {
        p6eCacheToken.setAccessTokenExpirationTime(this.model.getAccessToken(), time);
    }

    /**
     * 模型
     */
    public static class Model {
        private String accessToken;
        private String refreshToken;
        private String tokenType;
        private Long expiresIn;

        public Model() {
        }

        public Model(String accessToken, String refreshToken, String tokenType, Long expiresIn) {
            this.accessToken = accessToken;
            this.refreshToken = refreshToken;
            this.tokenType = tokenType;
            this.expiresIn = expiresIn;
        }

        public String getAccessToken() {
            return accessToken;
        }

        public String getRefreshToken() {
            return refreshToken;
        }

        public String getTokenType() {
            return tokenType;
        }

        public Long getExpiresIn() {
            return expiresIn;
        }
    }

}
