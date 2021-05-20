package com.p6e.germ.oauth2.model.dto;

import com.p6e.germ.oauth2.model.base.P6eResultDto2;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Map;

/**
 * @author lidashuang
 * @version 1.0
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class P6eInfoDto extends P6eResultDto2<P6eInfoDto> implements Serializable {
    private Map<String, String> data;
}
