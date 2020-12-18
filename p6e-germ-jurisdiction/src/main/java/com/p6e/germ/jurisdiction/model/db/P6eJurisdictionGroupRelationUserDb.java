package com.p6e.germ.jurisdiction.model.db;

import com.p6e.germ.jurisdiction.model.base.P6eBaseDb;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author lidashuang
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class P6eJurisdictionGroupRelationUserDb extends P6eBaseDb implements Serializable {
    private Integer uid;
    private Integer gid;
    private String createDate;
    private String updateDate;
    private String operate;
}
