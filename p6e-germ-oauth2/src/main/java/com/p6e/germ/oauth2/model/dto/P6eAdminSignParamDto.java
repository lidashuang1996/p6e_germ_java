package com.p6e.germ.oauth2.model.dto;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author lidashuang
 * @version 1.0
 */
@Data
@Accessors(chain = true)
public class P6eAdminSignParamDto {
    private String token;
    private String refreshToken;
}
