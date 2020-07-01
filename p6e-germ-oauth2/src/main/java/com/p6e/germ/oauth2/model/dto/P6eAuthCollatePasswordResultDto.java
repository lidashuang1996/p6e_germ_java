package com.p6e.germ.oauth2.model.dto;

import com.p6e.germ.oauth2.model.base.P6eBaseResultDto;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * @author lidashuang
 * @version 1.0
 */
@Data
public class P6eAuthCollatePasswordResultDto extends P6eBaseResultDto implements Serializable {
    private Map<String, String> data;
}
