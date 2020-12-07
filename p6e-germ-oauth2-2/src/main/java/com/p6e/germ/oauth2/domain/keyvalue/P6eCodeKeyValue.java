package com.p6e.germ.oauth2.domain.keyvalue;

import lombok.AllArgsConstructor;
import lombok.Data;
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
public class P6eCodeKeyValue implements Serializable {
    private String mark;
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
