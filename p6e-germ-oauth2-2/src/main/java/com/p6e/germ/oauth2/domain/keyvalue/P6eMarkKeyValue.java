package com.p6e.germ.oauth2.domain.keyvalue;

import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lidashuang
 * @version 1.0
 */
public class P6eMarkKeyValue implements Serializable {

    @Data
    public static class Content implements Serializable {
        private String state;
        private String clientId;
        private String responseType;
        private String redirectUri;
        private Integer status;
        private String icon;
        private String name;
        private String describe;

        public Map<String, String> toMap() {
            final Map<String, String> r = new HashMap<>();
            r.put("state", state);
            r.put("clientId", clientId);
            r.put("redirectUri", redirectUri);
            r.put("responseType", responseType);
            r.put("status", String.valueOf(status));
            r.put("icon", icon);
            r.put("name", name);
            r.put("describe", describe);
            return r;
        }
    }
}
