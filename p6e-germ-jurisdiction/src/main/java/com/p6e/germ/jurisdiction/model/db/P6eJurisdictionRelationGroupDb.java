package com.p6e.germ.jurisdiction.model.db;


import com.p6e.germ.jurisdiction.model.base.P6eBaseDb;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author lidashuang
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class P6eJurisdictionRelationGroupDb extends P6eBaseDb implements Serializable {
    private Integer gid;
    private Integer jurisdictionId;
    private String jurisdictionParam;
    private String createDate;
    private String updateDate;
    private String operate;
}
