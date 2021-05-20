package com.p6e.germ.oauth2.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author lidashuang
 * @version 1.0
 */
@Data
public class P6eVerificationLoginResult implements Serializable {
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
