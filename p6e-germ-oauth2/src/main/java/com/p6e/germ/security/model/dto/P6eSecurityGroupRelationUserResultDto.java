package com.p6e.germ.security.model.dto;

import com.p6e.germ.security.model.base.P6eBaseResultDto;
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
public class P6eSecurityGroupRelationUserResultDto extends P6eBaseResultDto implements Serializable {
    private Integer uid;
    private Integer gid;
    private String createDate;
    private String updateDate;
    private String operate;
}
