package com.p6e.germ.oauth2.model;

import com.p6e.germ.oauth2.model.base.P6eBaseDtoResult;
import lombok.Data;

import java.io.Serializable;

/**
 * @author lidashuang
 * @version 1.0
 */
public class P6eSecretVoucherModel implements Serializable {

    @Data
    public static class VoResult implements Serializable {
        private String voucher;
        private String publicKey;
    }

    @Data
    public static class DtoResult extends P6eBaseDtoResult implements Serializable {
        private String voucher;
        private String publicKey;
    }

}
