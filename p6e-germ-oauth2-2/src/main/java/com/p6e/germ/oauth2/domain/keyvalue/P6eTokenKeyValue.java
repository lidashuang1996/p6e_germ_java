package com.p6e.germ.oauth2.domain.keyvalue;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lidashuang
 * @version 1.0
 */
public class P6eTokenKeyValue {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AccessToken implements Serializable {
        private String content;
    }

    @Data
    public static class RefreshToken implements Serializable {
        private String content;
    }

    @Data
    @AllArgsConstructor
    public static class Content implements Serializable {
        private String accessToken;
        private String refreshToken;
        private String tokenType;
        private Long expiresIn;

        public Map<String, String> toMap() {
            final Map<String, String> result = new HashMap<>(4);
            result.put("token_type", tokenType);
            result.put("access_token", accessToken);
            result.put("refresh_token", refreshToken);
            result.put("expires_in", String.valueOf(expiresIn));
            return result;
        }
    }

    @Data
    @AllArgsConstructor
    public static class Voucher implements Serializable {
        private String id;
        private String accessToken;
        private String refreshToken;
    }

    @Data
    @AllArgsConstructor
    public static class CodeParam implements Serializable {
        private String content;
    }

    @Data
    @AllArgsConstructor
    public static class DataParam implements Serializable {
        private String id;
        private Map<String, String> content;
    }


}
