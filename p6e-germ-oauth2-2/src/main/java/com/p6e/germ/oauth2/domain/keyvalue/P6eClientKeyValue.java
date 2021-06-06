package com.p6e.germ.oauth2.domain.keyvalue;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lidashuang
 * @version 1.0
 */
public class P6eClientKeyValue implements Serializable {

    @Data
    public static class Id implements Serializable {
        private Integer id;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Key implements Serializable {
        private String key;
    }

    @Data
    public static class Content implements Serializable {
        private Integer id;
        private Integer status;
        private String icon;
        private String name;
        private String key;
        private String secret;
        private String scope;
        private String redirectUri;
        private String describe;
        private String limitingRule;

        public Map<String, String> toMap() {
            final Map<String, String> r = new HashMap<>(10);
            r.put("id", String.valueOf(id));
            r.put("status", String.valueOf(status));
            r.put("icon", icon);
            r.put("name", name);
            r.put("key", key);
            r.put("secret", secret);
            r.put("scope", scope);
            r.put("redirectUri", redirectUri);
            r.put("describe", describe);
            r.put("limitingRule", limitingRule);
            return r;
        }

    }
}
