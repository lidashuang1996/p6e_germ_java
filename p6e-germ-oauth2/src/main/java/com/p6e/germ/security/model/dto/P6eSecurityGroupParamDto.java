package com.p6e.germ.security.model.dto;

import com.p6e.germ.security.model.base.P6eBaseDb;
import com.p6e.germ.security.model.base.P6eBaseParamDto;
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
public class P6eSecurityGroupParamDto extends P6eBaseParamDto implements Serializable {

    private Integer id;
    private String name;
    private String describe;
    private Integer weight;

}
