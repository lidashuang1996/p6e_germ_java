package com.p6e.germ.security.cache;

import com.p6e.germ.oauth2.utils.P6eCommonUtil;
import com.p6e.germ.oauth2.utils.P6eJsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author lidashuang
 * @version 1.0
 */
@Component
public class P6eCacheRedisAuth implements P6eCacheAuth {

    private final RedisTemplate<String, String> redisTemplate;

    public static class TokenModel {
        private String uid;
        private String token;
        private String refreshToken;

        public TokenModel() {
        }

        public TokenModel(String uid, String token, String refreshToken) {
            this.uid = uid;
            this.token = token;
            this.refreshToken = refreshToken;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getRefreshToken() {
            return refreshToken;
        }

        public void setRefreshToken(String refreshToken) {
            this.refreshToken = refreshToken;
        }
    }

    @Autowired
    public P6eCacheRedisAuth(final RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public Map<String, Object> setData( final Map<String, Object> map) {
        if (map == null) {
            return null;
        }
        map.put("uid", map.get("uid"));
        final String token = P6eCommonUtil.generateUuid();
        final String refreshToken = P6eCommonUtil.generateUuid();
        map.put("token", token);
        map.put("refreshToken", refreshToken);
        map.put("expirationTime", EXPIRATION_TIME);
        final TokenModel tokenModel = new TokenModel(String.valueOf(map.get("uid")), token, refreshToken);
        redisTemplate.opsForValue().set(USER_NAME + map.get("uid"), P6eJsonUtil.toJson(map), EXPIRATION_TIME, TimeUnit.SECONDS);
        redisTemplate.opsForValue().set(TOKEN_NAME + token, P6eJsonUtil.toJson(tokenModel), EXPIRATION_TIME, TimeUnit.SECONDS);
        redisTemplate.opsForValue().set(REFRESH_TOKEN_NAME + refreshToken, P6eJsonUtil.toJson(tokenModel), EXPIRATION_TIME, TimeUnit.SECONDS);
        redisTemplate.opsForValue().set(USER_TOKEN_NAME + token, P6eJsonUtil.toJson(tokenModel), EXPIRATION_TIME, TimeUnit.SECONDS);
        redisTemplate.opsForValue().set(USER_REFRESH_TOKEN_NAME + refreshToken, P6eJsonUtil.toJson(tokenModel), EXPIRATION_TIME, TimeUnit.SECONDS);
        return map;
    }

    @Override
    public Map<String, Object> delDataByToken(final String token) {
        final String tokenContent = redisTemplate.opsForValue().get(TOKEN_NAME + token);
        if (tokenContent == null || "".equals(tokenContent)) {
            return null;
        }
        final TokenModel tokenModel = P6eJsonUtil.fromJson(tokenContent, TokenModel.class);
        if (tokenModel == null) {
            return null;
        }
        redisTemplate.delete(TOKEN_NAME + tokenModel.getToken());
        redisTemplate.delete(REFRESH_TOKEN_NAME + tokenModel.getRefreshToken());
        redisTemplate.delete(USER_TOKEN_NAME + tokenModel.getToken());
        redisTemplate.delete(USER_REFRESH_TOKEN_NAME + tokenModel.getRefreshToken());
        return result(P6eJsonUtil.fromJson(tokenContent, TokenModel.class));
    }

    @Override
    public Map<String, Object> getDataByToken(final String token) {
        final String tokenContent = redisTemplate.opsForValue().get(TOKEN_NAME + token);
        if (tokenContent == null || "".equals(tokenContent)) {
            return null;
        }
        return result(P6eJsonUtil.fromJson(tokenContent, TokenModel.class));
    }

    private Map<String, Object> result(TokenModel tokenModel) {
        if (tokenModel == null) {
            return null;
        }
        final String userContent = redisTemplate.opsForValue().get(USER_NAME + tokenModel.getUid());
        final Map<String, Object> resultMap = P6eJsonUtil.fromJsonToMap(userContent, String.class, Object.class);
        if (resultMap == null) {
            return null;
        }
        resultMap.put("uid", tokenModel.getUid());
        resultMap.put("token", tokenModel.getToken());
        resultMap.put("refreshToken", tokenModel.getRefreshToken());
        return resultMap;
    }
}
