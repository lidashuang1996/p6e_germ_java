package com.p6e.germ.security.model.vo;

import com.p6e.germ.security.model.base.P6eBaseResultVo;
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
public class P6eSecurityJurisdictionRelationGroupResultVo extends P6eBaseResultVo implements Serializable {
    private Integer gid;
    private Integer jurisdictionId;
    private String jurisdictionParam;
    private String createDate;
    private String updateDate;
    private String operate;
}
