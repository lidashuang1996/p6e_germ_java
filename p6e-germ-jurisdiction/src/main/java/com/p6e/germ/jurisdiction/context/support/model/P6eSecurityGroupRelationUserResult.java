package com.p6e.germ.jurisdiction.context.support.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author lidashuang
 * @version 1.0
 */
@Data
public class P6eSecurityGroupRelationUserResult implements Serializable {
    private Integer uid;
    private Integer gid;
    private String createDate;
    private String updateDate;
    private String operate;
}
