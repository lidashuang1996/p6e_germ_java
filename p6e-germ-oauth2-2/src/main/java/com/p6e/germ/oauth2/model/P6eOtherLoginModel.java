package com.p6e.germ.oauth2.model;

import com.p6e.germ.oauth2.model.base.P6eBaseDtoResult;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author lidashuang
 * @version 1.0
 */
public class P6eOtherLoginModel implements Serializable {

    @Data
    public static class VoParam implements Serializable {
        private String mark;
        private String code;
        private String state;
    }

    @Data
    public static class VoResult implements Serializable {
        private String content;
        private String code;

        private String accessToken;
        private String refreshToken;
        private String tokenType;
        private Long expiresIn;

        private String scope;
        private String state;
        private String clientId;
        private String responseType;
        private String redirectUri;
        private Integer status;
        private String icon;
        private String name;
        private String key;
        private String secret;
        private String describe;
        private String limitingRule;
    }

    @Data
    @Accessors(chain = true)
    public static class DtoParam implements Serializable {
        private String mark;
        private String code;
        private String state;
    }

    @Data
    public static class DtoResult extends P6eBaseDtoResult implements Serializable {
        private String content;
        private String code;


        private String accessToken;
        private String refreshToken;
        private String tokenType;
        private Long expiresIn;

        private String scope;
        private String state;
        private String clientId;
        private String responseType;
        private String redirectUri;
        private Integer status;
        private String icon;
        private String name;
        private String key;
        private String secret;
        private String describe;
        private String limitingRule;
    }
}
