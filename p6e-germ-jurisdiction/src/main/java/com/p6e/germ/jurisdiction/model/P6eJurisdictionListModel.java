package com.p6e.germ.jurisdiction.model;

import com.p6e.germ.jurisdiction.model.base.P6eBaseDto;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author lidashuang
 * @version 1.0
 */
public class P6eJurisdictionListModel {

    @Data
    @Accessors(chain = true)
    public static class DtoParam implements Serializable {
        private Integer id;
        private String code;
        private String name;
        private String describe;
        private String content;
        private String operate;
    }

    public static class DtoResult extends P6eBaseDto implements Serializable {

    }

    public static class VoParam implements Serializable {

    }

    public static class VoResult implements Serializable {

    }

}
