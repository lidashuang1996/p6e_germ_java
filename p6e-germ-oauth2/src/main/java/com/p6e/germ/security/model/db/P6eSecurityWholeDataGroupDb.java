package com.p6e.germ.security.model.db;

import com.p6e.germ.security.model.base.P6eBaseDb;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @author lidashuang
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class P6eSecurityWholeDataGroupDb extends P6eBaseDb implements Serializable {
    private Integer groupId;
    private String groupName;
    private String groupDescribe;
    private Integer groupWeight;
    private String jurisdictionParam;
    private Integer jurisdictionId;
    private String jurisdictionCode;
    private String jurisdictionName;
    private String jurisdictionDescribe;
    private String jurisdictionContent;
}
