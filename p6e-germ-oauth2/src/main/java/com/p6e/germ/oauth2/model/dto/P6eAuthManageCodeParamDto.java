package com.p6e.germ.oauth2.model.dto;

import lombok.Data;

import java.util.Map;

/**
 * @author lidashuang
 * @version 1.0
 */
@Data
public class P6eAuthManageCodeParamDto {
    private String mark;
    private Map<String, Object> data;
}
