package com.p6e.germ.oauth2.model.dto;

import lombok.Data;

/**
 * @author lidashuang
 * @version 1.0
 */
@Data
public class P6eCodeAuthDto {
    private String responseType;
    private String clientId;
    private String redirectUri;
    private String scope;
    private String state;
}
