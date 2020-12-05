package com.p6e.germ.oauth2.model.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author lidashuang
 * @version 1.0
 */
@Data
public class P6eVerificationAuthDto implements Serializable {
    private String responseType;
    private String clientId;
    private String redirectUri;
    private String scope;
    private String state;
}
