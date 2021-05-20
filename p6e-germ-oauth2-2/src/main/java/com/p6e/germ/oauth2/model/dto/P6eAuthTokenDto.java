package com.p6e.germ.oauth2.model.dto;

import com.p6e.germ.oauth2.model.base.P6eResultDto2;
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
public class P6eAuthTokenDto extends P6eResultDto2<P6eAuthTokenDto> implements Serializable {
    private String accessToken;
    private String refreshToken;
    private String tokenType;
    private Long expiresIn;
}
