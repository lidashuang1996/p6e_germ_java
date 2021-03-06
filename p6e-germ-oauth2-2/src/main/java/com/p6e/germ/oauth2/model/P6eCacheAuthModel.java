package com.p6e.germ.oauth2.model;

import com.p6e.germ.oauth2.model.base.P6eBaseDtoResult;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author lidashuang
 * @version 1.0
 */
public class P6eCacheAuthModel implements Serializable {

    @Data
    @Accessors(chain = true)
    public static class DtoParam implements Serializable {
        private String key;
        private String content;
    }

    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class DtoResult extends P6eBaseDtoResult implements Serializable {
        private String content;
    }
}
