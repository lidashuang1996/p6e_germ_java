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
@AllArgsConstructor
public class P6eAuthKeyValue implements Serializable {
    private String clientId;
    private String scope;
    private String state;
    private String responseType;
    private String redirectUri;
}
