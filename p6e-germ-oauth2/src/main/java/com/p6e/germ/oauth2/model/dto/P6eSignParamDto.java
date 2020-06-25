package com.p6e.germ.oauth2.model.dto;

import com.p6e.germ.oauth2.model.base.P6eBaseParamDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @version 1.0
 * @author lidashuang
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class P6eSignParamDto extends P6eBaseParamDto implements Serializable {
    private String token;
    private String refreshToken;
}
