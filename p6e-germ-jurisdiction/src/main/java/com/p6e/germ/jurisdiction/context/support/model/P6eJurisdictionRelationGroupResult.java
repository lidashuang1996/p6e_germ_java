package com.p6e.germ.jurisdiction.context.support.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author lidashuang
 * @version 1.0
 */
@Data
public class P6eJurisdictionRelationGroupResult implements Serializable {
    private Integer gid;
    private Integer jurisdictionId;
    private String jurisdictionParam;
    private String createDate;
    private String updateDate;
    private String operate;
}
