package com.p6e.germ.oauth2.domain.keyvalue;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lidashuang
 * @version 1.0
 */
public class P6eUserKeyValue implements Serializable {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Account implements Serializable {
        private String content;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Qq implements Serializable {
        private String content;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class WeChat implements Serializable {
        private String content;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Sina implements Serializable {
        private String content;
    }

    @Data
    public static class Content implements Serializable {
        private Integer id;
        private String email;
        private String phone;
        private String password;
        private String qq;
        private String weChat;

        public Map<String, String> toMap() {
            final Map<String, String> r = new HashMap<>(5);
            r.put("id", String.valueOf(id));
            r.put("email", String.valueOf(email));
            r.put("phone", String.valueOf(phone));
            r.put("qq", String.valueOf(qq));
            r.put("weChat", String.valueOf(weChat));
            return r;
        }
    }
}
