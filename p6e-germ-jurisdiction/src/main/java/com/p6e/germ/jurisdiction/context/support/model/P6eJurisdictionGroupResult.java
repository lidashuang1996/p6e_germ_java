package com.p6e.germ.jurisdiction.context.support.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author lidashuang
 * @version 1.0
 */
@Data
public class P6eJurisdictionGroupResult implements Serializable {
    private Integer id;
    private String name;
    private String describe;
    private Integer weight;
    private String createDate;
    private String updateDate;
    private String operate;
}
