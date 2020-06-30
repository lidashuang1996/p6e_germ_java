package com.p6e.germ.oauth2.model.dto;

import lombok.Data;

/**
 * @author lidashuang
 * @version 1.0
 */
@Data
public class P6eAuthManageCodeResultDto {
    private String code;
    private String redirectUri;
}
