package com.p6e.germ.oauth2.domain.keyvalue;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @author lidashuang
 * @version 1.0
 */
public class P6eSecretVoucherKeyValue {

    @Data
    public static class Content implements Serializable {
        /** 公钥 */
        private String publicSecretKey;

        /** 私钥 */
        private String privateSecretKey;

        public Content() {
        }

        public Content(String publicSecretKey, String privateSecretKey) {
            this.publicSecretKey = publicSecretKey;
            this.privateSecretKey = privateSecretKey;
        }
    }

}
