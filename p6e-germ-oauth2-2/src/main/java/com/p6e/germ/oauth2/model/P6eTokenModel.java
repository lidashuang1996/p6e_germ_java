package com.p6e.germ.oauth2.model;

import com.p6e.germ.oauth2.model.base.P6eBaseDtoResult;
import lombok.Data;

import java.io.Serializable;

/**
 * @author lidashuang
 * @version 1.0
 */
public class P6eTokenModel implements Serializable {

    @Data
    public static class VoParam implements Serializable {
        private String grantType;
        private String redirectUri;
        private String clientId;
        private String clientSecret;

        private String code;
        private String scope;
        private String account;
        private String password;

        private String accessToken;
        private String refreshToken;
    }

    @Data
    public static class VoResult implements Serializable {
        private Integer id;
        private String icon;
        private String name;
        private String describe;
        private String mark;
    }

    @Data
    public static class DtoParam implements Serializable {
        private String responseType;
        private String clientId;
        private String redirectUri;
        private String scope;
        private String state;
    }

    @Data
    public static class DtoResult extends P6eBaseDtoResult implements Serializable {
        private Integer id;
        private String icon;
        private String name;
        private String describe;
        private String mark;
    }

}
