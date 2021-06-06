package com.p6e.germ.oauth2.model;

import com.p6e.germ.oauth2.model.base.P6eBaseDtoResult;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author lidashuang
 * @version 1.0
 */
public class P6eCodeAuthModel implements Serializable {

    @Data
    public static class VoParam implements Serializable {
        private String clientId;
        private String clientSecret;
        private String grantType;
        private String redirectUri;
        private String code;
    }

    @Data
    public static class VoResult implements Serializable {
        private String accessToken;
        private String refreshToken;
        private String tokenType;
        private Long expiresIn;
    }

    @Data
    public static class DtoParam implements Serializable {
        private String clientId;
        private String clientSecret;
        private String grantType;
        private String redirectUri;
        private String code;
    }

    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class DtoResult extends P6eBaseDtoResult implements Serializable {
        private String accessToken;
        private String refreshToken;
        private String tokenType;
        private Long expiresIn;
    }
}
