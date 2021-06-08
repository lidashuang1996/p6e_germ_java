package com.p6e.germ.oauth2.model;

import com.p6e.germ.oauth2.model.base.P6eBaseDtoResult;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author lidashuang
 * @version 1.0
 */
public class P6eAuthModel implements Serializable {

    @Data
    public static class VoParam implements Serializable {
        private String responseType;
        private String clientId;
        private String redirectUri;
        private String scope;
        private String state;
    }

    @Data
    public static class VoResult implements Serializable {
        private String mark;
        private String type = "sign";
    }

    @Data
    public static class DtoParam implements Serializable {
        private String responseType;
        private String clientId;
        private String redirectUri;
        private String scope;
        private String state;

    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    public static class DtoResult extends P6eBaseDtoResult implements Serializable {
        private String mark;
    }
}
