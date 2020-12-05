package com.p6e.germ.oauth2.model.dto;

import com.p6e.germ.oauth2.model.base.P6eResultDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author lidashuang
 * @version 1.0
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class P6eLoginDto extends P6eResultDto<P6eLoginDto> implements Serializable {
    private String code;
    private String clientId;
    private String scope;
    private String state;
    private String responseType;
    private String redirectUri;
    private String accessToken;
    private String refreshToken;
    private String tokenType;
    private Long expiresIn;
}
