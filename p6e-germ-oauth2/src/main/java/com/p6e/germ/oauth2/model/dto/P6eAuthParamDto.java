package com.p6e.germ.oauth2.model.dto;

/**
 * @author lidashuang
 * @version 1.0
 */

import com.p6e.germ.oauth2.model.base.P6eBaseParamDto;
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
public class P6eAuthParamDto extends P6eBaseParamDto implements Serializable {
    private String responseType;
    private String clientId;
    private String redirectUri;
    private String scope;
    private String state;
}
