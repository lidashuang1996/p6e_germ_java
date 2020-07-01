package com.p6e.germ.oauth2.model.dto;

import com.p6e.germ.oauth2.model.base.P6eBaseParamDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Map;

/**
 * @author lidashuang
 * @version 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class P6eAuthManageTokenParamDto extends P6eBaseParamDto implements Serializable {
    private String mark;
    private Map<String, Object> data;
}
