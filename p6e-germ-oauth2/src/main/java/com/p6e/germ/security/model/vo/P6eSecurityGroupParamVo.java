package com.p6e.germ.security.model.vo;

import com.p6e.germ.security.model.base.P6eBaseParamVo;
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
public class P6eSecurityGroupParamVo extends P6eBaseParamVo implements Serializable {

    private String name;
    private String describe;
    private Integer weight;

}
