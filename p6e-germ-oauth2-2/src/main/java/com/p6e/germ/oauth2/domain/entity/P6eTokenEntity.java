package com.p6e.germ.oauth2.domain.entity;

import com.p6e.germ.common.utils.P6eGeneratorUtil;
import com.p6e.germ.common.utils.P6eJsonUtil;
import com.p6e.germ.oauth2.infrastructure.cache.IP6eCacheToken;
import com.p6e.germ.oauth2.infrastructure.cache.P6eCache;

import java.io.Serializable;
import java.util.Map;

/**
 * @author lidashuang
 * @version 1.0
 */
public class P6eTokenEntity implements Serializable {

    /** 类型 */
    public static final String ACCESS_TOKEN = "ACCESS_TOKEN";
    public static final String REFRESH_TOKEN = "REFRESH_TOKEN";

    /** 缓存的内容 */
    private String key;

    /** 缓存的内容 */
    private final Model model;

    /** 缓存的内容 */
    private final String id;

    /** 缓存的内容 */
    private final Map<String, String> value;

    /** 注入缓存对象 */
    private final IP6eCacheToken p6eCacheToken = P6eCache.token;

    /**
     * 构造创建
     * @param key key
     */
    public P6eTokenEntity(String key) {
        this.key = key;
        // 读取缓存数据
        final String modelContent = p6eCacheToken.get(key);
        if (modelContent == null) {
            throw new NullPointerException(this.getClass() + " construction data ==> NullPointerException.");
        }
        this.model = P6eJsonUtil.fromJson(modelContent, Model.class);
        if (this.model == null) {
            throw new NullPointerException(this.getClass() + " construction data ==> NullPointerException.");
        }
        // 读取缓存数据
        final String tokenContent = p6eCacheToken.getAccessToken(this.model.getAccessToken());
        if (tokenContent == null) {
            throw new NullPointerException(this.getClass() + " construction data ==> NullPointerException.");
        }
        final Data data = P6eJsonUtil.fromJson(tokenContent, Data.class);
        if (data == null) {
            throw new NullPointerException(this.getClass() + " construction data ==> NullPointerException.");
        }
        // 读取缓存数据
        this.id = data.getId();
        final String user = p6eCacheToken.getUser(this.id);
        if (user == null) {
            throw new NullPointerException(this.getClass() + " construction data ==> NullPointerException.");
        }
        this.value = P6eJsonUtil.fromJsonToMap(user, String.class, String.class);
    }

    /**
     * 构造创建
     * @param token token
     * @param type 类型
     */
    public P6eTokenEntity(String token, String type) {
        final String tokenContent;
        switch (type) {
            case "ACCESS_TOKEN":
                tokenContent = p6eCacheToken.getAccessToken(token);
                break;
            case "REFRESH_TOKEN":
                tokenContent = p6eCacheToken.getRefreshToken(token);
                break;
            default:
                throw new NullPointerException();
        }
        if (tokenContent == null) {
            throw new NullPointerException(this.getClass() + " construction data ==> NullPointerException.");
        }
        final Data data = P6eJsonUtil.fromJson(tokenContent, Data.class);
        if (data == null) {
            throw new NullPointerException(this.getClass() + " construction data ==> NullPointerException.");
        }
        this.id = data.getId();
        this.model = new Model(
                data.getAccessToken(),
                data.getRefreshToken(),
                "bearer",
                IP6eCacheToken.TOKEN_TIME
        );
        final String userContent = p6eCacheToken.getUser(data.getId());
        if (userContent == null) {
            throw new NullPointerException(this.getClass() + " construction data ==> NullPointerException.");
        }
        this.value = P6eJsonUtil.fromJsonToMap(userContent, String.class, String.class);
        if (this.value == null) {
            throw new NullPointerException(this.getClass() + " construction data ==> NullPointerException.");
        }
    }

    /**
     * 构造创建
     * @param value value
     */
    public P6eTokenEntity(String id, Map<String, String> value) {
        this.key = P6eGeneratorUtil.uuid();
        this.id = id;
        this.value = value;
        this.model = new Model(
                P6eGeneratorUtil.uuid(),
                P6eGeneratorUtil.uuid(),
                "bearer",
                IP6eCacheToken.TOKEN_TIME
        );
    }

    public static P6eTokenEntity get(String code) {
        return new P6eTokenEntity(code);
    }


    /**
     * 缓存
     */
    public P6eTokenEntity cache() {
        // 缓存认证信息
        p6eCacheToken.set(key, P6eJsonUtil.toJson(model));
        // 缓存用户信息
        p6eCacheToken.setUser(id, P6eJsonUtil.toJson(value));
        // 缓存 token 对应用户信息
        final Data token = new Data(id, model.getAccessToken(), model.getRefreshToken());
        // 写入缓存数据
        p6eCacheToken.setAccessToken(model.getAccessToken(), P6eJsonUtil.toJson(token));
        p6eCacheToken.setRefreshToken(model.getRefreshToken(), P6eJsonUtil.toJson(token));
        return this;
    }

    /**
     * 删除 access token 的内容
     */
    public void delAccessToken() {
        p6eCacheToken.delAccessToken(this.model.getAccessToken());
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
     * 设置 refresh token 的过期时间戳
     * @param time 时间戳
     */
    public void setRefreshTokenExpirationTime(long time) {
        p6eCacheToken.setRefreshTokenExpirationTime(this.model.getRefreshToken(), time);
    }

    /**
     * 验证 refresh token 数据
     * @param refresh refresh token 数据
     * @return 是否相同
     */
    public boolean verificationRefreshToken(String refresh) {
        return model.getRefreshToken().equals(refresh);
    }

    /**
     * 删除模型
     * @return 本身对象
     */
    public P6eTokenEntity delModel() {
        if (key != null) {
            p6eCacheToken.del(key);
        }
        return this;
    }

    /**
     * 获取 KEY
     * @return KEY 数据
     */
    public String getKey() {
        return key;
    }

    /**
     * 获取模型
     * @return 模型对象
     */
    public Model getModel() {
        return model;
    }

    /**
     * 返回缓存数据
     * @return 缓存数据对象
     */
    public Map<String, String> getValue() {
        return value;
    }

    /**
     * 刷新对象
     * @return 本身对象
     */
    public P6eTokenEntity refresh() {
        p6eCacheToken.del(key);
        p6eCacheToken.delAccessToken(model.getAccessToken());
        p6eCacheToken.delRefreshToken(model.getRefreshToken());
        return new P6eTokenEntity(id, value).cache();
    }

    /**
     * 重置模型
     * @return 本身对象
     */
    public P6eTokenEntity resetModel() {
        this.key = P6eGeneratorUtil.uuid();
        final Data token = new Data(id, model.getAccessToken(), model.getRefreshToken());
        p6eCacheToken.setAccessToken(model.getAccessToken(), P6eJsonUtil.toJson(token));
        p6eCacheToken.setRefreshToken(model.getRefreshToken(), P6eJsonUtil.toJson(token));
        return this;
    }

    /**
     * 模型对象
     */
    public static class Model implements Serializable {
        private String accessToken;
        private String refreshToken;
        private String tokenType;
        private Long expiresIn;

        public Model() { }

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

    /**
     * 数据对象
     */
    public static class Data implements Serializable {
        private String id;
        private String accessToken;
        private String refreshToken;

        public Data() { }

        public Data(String id, String accessToken, String refreshToken) {
            this.id = id;
            this.accessToken = accessToken;
            this.refreshToken = refreshToken;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getAccessToken() {
            return accessToken;
        }

        public void setAccessToken(String accessToken) {
            this.accessToken = accessToken;
        }

        public String getRefreshToken() {
            return refreshToken;
        }

        public void setRefreshToken(String refreshToken) {
            this.refreshToken = refreshToken;
        }
    }
}