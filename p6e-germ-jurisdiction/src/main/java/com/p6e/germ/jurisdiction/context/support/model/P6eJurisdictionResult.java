package com.p6e.germ.jurisdiction.context.support.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author lidashuang
 * @version 1.0
 */
@Data
public class P6eJurisdictionResult implements Serializable {
    private Integer id;
    private String code;
    private String name;
    private String describe;
    private String content;
    private String createDate;
    private String updateDate;
    private String operate;
}
