package com.p6e.germ.oauth2.domain.keyvalue;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author lidashuang
 * @version 1.0
 */
@Getter
@NoArgsConstructor
public class P6eDefaultAuthKeyValue extends P6eTokenKeyValue implements Serializable {
    private String redirectUri;
    private String code;
    private String state;

    public P6eDefaultAuthKeyValue(String accessToken, String refreshToken,
                                  String tokenType, Long expiresIn,
                                  String redirectUri, String code, String state) {
        super(accessToken, refreshToken, tokenType, expiresIn);
        this.redirectUri = redirectUri;
        this.code = code;
        this.state = state;
    }
}
