package com.p6e.germ.security.model.vo;

import com.p6e.germ.security.model.base.P6eBaseParamVo;
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
public class P6eOauth2AuthParamVo extends P6eBaseParamVo implements Serializable {
    private Integer id;
    private String account;
    private String password;
    private String token;
}
