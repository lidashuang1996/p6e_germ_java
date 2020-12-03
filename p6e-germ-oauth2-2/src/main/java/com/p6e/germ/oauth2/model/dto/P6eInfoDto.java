package com.p6e.germ.oauth2.model.dto;

import com.p6e.germ.oauth2.model.base.P6eResultDto;
import lombok.Data;

import java.util.Map;

/**
 * @author lidashuang
 * @version 1.0
 */
@Data
public class P6eInfoDto extends P6eResultDto {
    private Map<String, String> data;
}
