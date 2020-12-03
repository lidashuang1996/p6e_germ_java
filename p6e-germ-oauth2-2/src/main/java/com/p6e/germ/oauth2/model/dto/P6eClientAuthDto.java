package com.p6e.germ.oauth2.model.dto;

import lombok.Data;

/**
 * @author lidashuang
 * @version 1.0
 */
@Data
public class P6eClientAuthDto {
    private String clientId;
    private String clientSecret;
    private String scope;
    private String grantType;
    private String redirectUri;
}
