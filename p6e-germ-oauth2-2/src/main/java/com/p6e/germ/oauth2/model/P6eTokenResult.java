package com.p6e.germ.oauth2.model;

import lombok.Data;
import java.io.Serializable;

/**
 * @author lidashuang
 * @version 1.0
 */
@Data
public class P6eTokenResult implements Serializable {
    private String accessToken;
    private String refreshToken;
    private String tokenType;
    private Long expiresIn;
}
