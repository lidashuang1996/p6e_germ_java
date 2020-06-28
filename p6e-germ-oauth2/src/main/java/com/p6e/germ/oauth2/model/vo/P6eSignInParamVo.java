package com.p6e.germ.oauth2.model.vo;

import com.p6e.germ.oauth2.model.base.P6eBaseParamVo;
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
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class P6eSignInParamVo extends P6eBaseParamVo implements Serializable {
    private String account;
    private String password;
    private String voucher;
    private String mark;

    private String mode;
}
