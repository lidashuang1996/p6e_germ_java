package com.p6e.germ.oauth2.model;

import com.p6e.germ.oauth2.model.base.P6eBaseDtoResult;
import com.p6e.germ.oauth2.model.base.P6eResultDto2;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Map;

/**
 * @author lidashuang
 * @version 1.0
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class P6eInfoAuthModel extends P6eResultDto2<P6eInfoAuthModel> implements Serializable {

    @Data
    public static class VoParam implements Serializable {
        private String accessToken;
    }

    @Data
    public static class VoResult implements Serializable {
        private Map<String, String> data;
    }

    @Data
    @Accessors(chain = true)
    public static class DtoParam implements Serializable {
        private String accessToken;
    }

    @Data
    public static class DtoResult extends P6eBaseDtoResult implements Serializable {
        private Map<String, String> data;
    }
}
