package com.p6e.germ.oauth2.model;

import com.p6e.germ.oauth2.model.base.P6eBaseDtoResult;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Map;

/**
 * @author lidashuang
 * @version 1.0
 */
public class P6eNrCodeModel implements Serializable {
    @Data
    public static class VoParam implements Serializable {
        private String mark;
        private String account;
        private String codeKey;
        private String codeContent;
    }

    @Data
    public static class VoResult implements Serializable {
        private String account;
        private String content;

        private String accessToken;
        private String refreshToken;
        private String tokenType;
        private Long expiresIn;

        private Map<String, String> extend;
    }

    @Data
    @Accessors(chain = true)
    public static class DtoParam implements Serializable {
        private String mark;
        private String account;
        private String ip;
        private String codeKey;
        private String codeContent;
    }

    @Data
    public static class DtoResult extends P6eBaseDtoResult implements Serializable {
        private String account;
        private String content;

        private String accessToken;
        private String refreshToken;
        private String tokenType;
        private Long expiresIn;

        private Map<String, String> extend;
    }
}
