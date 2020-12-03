package com.p6e.germ.oauth2.model.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author lidashuang
 * @version 1.0
 */
@Data
public class P6ePasswordAuthDto implements Serializable {
    private String clientId;
    private String clientSecret;
    private String scope;
    private String grantType;
    private String redirectUri;
    private String password;
    private String account;
}
