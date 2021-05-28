package com.p6e.germ.oauth2.model;

import com.p6e.germ.oauth2.model.base.P6eBaseDtoResult;
import lombok.Data;

import java.io.Serializable;

/**
 * @author lidashuang
 * @version 1.0
 */
public class P6eAuthTokenModel implements Serializable {

    @Data
    public static class VoParam implements Serializable {
        private String clientId;
        private String clientSecret;
        private String scope;
        private String grantType;
        private String redirectUri;
        private String code;
        private String username;
        private String password;


        private String accessToken;
        private String refreshToken;
    }

    @Data
    public static class VoResult implements Serializable {

    }

    @Data
    public static class DtoParam implements Serializable {
        private String clientId;
        private String clientSecret;
        private String scope;
        private String grantType;
        private String redirectUri;
        private String code;
        private String username;
        private String password;


        private String accessToken;
        private String refreshToken;
    }

    @Data
    public static class DtoResult extends P6eBaseDtoResult implements Serializable {
        private String accessToken;
        private String refreshToken;
        private String tokenType;
        private Long expiresIn;
    }
}
