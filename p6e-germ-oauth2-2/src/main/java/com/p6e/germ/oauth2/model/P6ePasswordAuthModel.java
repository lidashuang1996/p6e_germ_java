package com.p6e.germ.oauth2.model;

import com.p6e.germ.oauth2.model.base.P6eBaseDtoResult;
import lombok.Data;

import java.io.Serializable;

/**
 * @author lidashuang
 * @version 1.0
 */
public class P6ePasswordAuthModel implements Serializable {

    @Data
    public static class VoParam implements Serializable {
        private String clientId;
        private String clientSecret;
        private String grantType;
        private String redirectUri;
        private String username;
        private String password;
    }

    @Data
    public static class VoResult implements Serializable {

    }

    @Data
    public static class DtoParam implements Serializable {
        private String clientId;
        private String clientSecret;
        private String grantType;
        private String redirectUri;
        private String username;
        private String password;
    }

    @Data
    public static class DtoResult extends P6eBaseDtoResult implements Serializable {
        private String accessToken;
        private String refreshToken;
        private String tokenType;
        private Long expiresIn;
    }
}
