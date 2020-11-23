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
public class P6eTokenKeyValue implements Serializable {
    private String accessToken;
    private String refreshToken;
    private String tokenType;
    private Long expiresIn;
}
