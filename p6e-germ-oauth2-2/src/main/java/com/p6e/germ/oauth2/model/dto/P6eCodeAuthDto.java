package com.p6e.germ.oauth2.model.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author lidashuang
 * @version 1.0
 */
@Data
@Accessors(chain = true)
public class P6eCodeAuthDto implements Serializable {
    private String clientId;
    private String clientSecret;
    private String scope;
    private String grantType;
    private String redirectUri;
    private String code;
}
