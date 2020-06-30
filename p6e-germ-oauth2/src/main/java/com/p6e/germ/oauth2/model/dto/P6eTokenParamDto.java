package com.p6e.germ.oauth2.model.dto;

import lombok.Data;

import java.util.Map;

/**
 * @author lidashuang
 * @version 1.0
 */
@Data
public class P6eTokenParamDto {
    private String accessToken;
    private Map<String, String> data;

    public P6eTokenParamDto() { }

    public P6eTokenParamDto(String accessToken) {
        this.accessToken = accessToken;
    }
}
