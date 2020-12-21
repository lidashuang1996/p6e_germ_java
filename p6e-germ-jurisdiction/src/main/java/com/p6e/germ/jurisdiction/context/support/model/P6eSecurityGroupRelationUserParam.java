package com.p6e.germ.jurisdiction.context.support.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author lidashuang
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class P6eSecurityGroupRelationUserParam extends P6eBaseParam implements Serializable {
    private Integer uid;
    private Integer gid;
}
