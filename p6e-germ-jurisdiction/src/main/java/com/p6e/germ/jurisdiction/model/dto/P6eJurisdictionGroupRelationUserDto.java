package com.p6e.germ.jurisdiction.model.dto;

import com.p6e.germ.jurisdiction.model.base.P6eBaseDb;
import com.p6e.germ.jurisdiction.model.base.P6eResultDto;
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
public class P6eJurisdictionGroupRelationUserDto extends P6eResultDto<P6eJurisdictionGroupRelationUserDto> implements Serializable {
    private Integer uid;
    private Integer gid;
    private String createDate;
    private String updateDate;
    private String operate;
}
