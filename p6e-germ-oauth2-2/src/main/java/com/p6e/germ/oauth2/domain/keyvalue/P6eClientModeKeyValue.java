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
@AllArgsConstructor
@NoArgsConstructor
public class P6eClientModeKeyValue implements Serializable {
    private String grantType;
    private String redirectUri;
    private String clientId;
    private String clientSecret;
    private String scope;
}
