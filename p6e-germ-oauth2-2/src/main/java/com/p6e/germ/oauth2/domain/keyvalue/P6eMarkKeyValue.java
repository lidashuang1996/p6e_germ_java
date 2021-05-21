package com.p6e.germ.oauth2.domain.keyvalue;

import lombok.Data;

import java.io.Serializable;

/**
 * @author lidashuang
 * @version 1.0
 */
public class P6eMarkKeyValue implements Serializable {

    @Data
    public static class Content implements Serializable {
        private Integer id;
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
