package com.p6e.germ.security.model.vo;

import com.p6e.germ.security.model.base.P6eBaseResultVo;
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
public class P6eSecurityGroupRelationUserResultVo extends P6eBaseResultVo implements Serializable {
    private Integer uid;
    private Integer gid;
    private String createDate;
    private String updateDate;
    private String operate;
}
