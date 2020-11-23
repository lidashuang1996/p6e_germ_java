package com.p6e.germ.oauth2.domain.keyvalue;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author lidashuang
 * @version 1.0
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class P6ePasswordModeKeyValue {
    private String account;
    private String password;
    private String grantType;
    private String redirectUri;
    private String clientId;
    private String clientSecret;
    private String scope;
}
