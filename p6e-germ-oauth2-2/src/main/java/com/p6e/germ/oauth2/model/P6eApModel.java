package com.p6e.germ.oauth2.model;

import com.p6e.germ.oauth2.model.base.P6eBaseDtoResult;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Map;

/**
 * @author lidashuang
 * @version 1.0
 */
public class P6eApModel implements Serializable {

    @Data
    public static class VoParam implements Serializable {
        private String account;
        private String password;
        private String voucher;
        private String mark;
    }



    @Data
    public static class VoResult implements Serializable {
        private String accessToken;
        private String refreshToken;
        private String tokenType;
        private Long expiresIn;

        private String code;
        private String state;
        private String clientId;
        private String responseType;
        private String redirectUri;
        private Integer status;
        private String icon;
        private String name;
        private String describe;
    }

    @Data
    public static class DtoParam implements Serializable {
        private String account;
        private String password;
        private String voucher;
        private String mark;
    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    public static class DtoResult extends P6eBaseDtoResult implements Serializable {
        private String accessToken;
        private String refreshToken;
        private String tokenType;
        private Long expiresIn;

        private String code;
        private String state;
        private String clientId;
        private String responseType;
        private String redirectUri;
        private Integer status;
        private String icon;
        private String name;
        private String describe;

    }

    @Data
    public static class VerificationVoParam implements Serializable {
        private String mark;
        private String accessToken;
    }

    @Data
    public static class VerificationDtoParam implements Serializable {
        private String mark;
        private String accessToken;
    }

    @Data
    public static class VerificationDtoResult extends DtoResult implements Serializable {
        private String code;
        private String clientId;
        private String scope;
        private String state;
        private String responseType;
        private String redirectUri;
        private String accessToken;
        private String refreshToken;
        private String tokenType;
        private Long expiresIn;
    }


    @Data
    public static class DtoResult2 implements Serializable {
        private String code;
        private String clientId;
        private String scope;
        private String state;
        private String responseType;
        private String redirectUri;
        private String accessToken;
        private String refreshToken;
        private String tokenType;
        private Long expiresIn;
    }


}
