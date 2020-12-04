package com.p6e.germ.oauth2.context.controller.support.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author lidashuang
 * @version 1.0
 */
@Data
public class P6eTokenModelParam implements Serializable {
    private String grantType;
    private String redirectUri;
    private String clientId;
    private String clientSecret;

    private String code;
    private String scope;
    private String account;
    private String password;
}
