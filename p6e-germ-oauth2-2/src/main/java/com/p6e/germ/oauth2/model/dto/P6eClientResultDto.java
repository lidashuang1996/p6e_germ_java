package com.p6e.germ.oauth2.model.dto;

import com.p6e.germ.oauth2.model.base.P6eResultDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * @author lidashuang
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class P6eClientResultDto extends P6eResultDto implements Serializable {
    private Integer id;
    private Integer status;
    private String icon;
    private String name;
    private String key;
    private String secret;
    private String scope;
    private String redirectUri;
    private String describe;
    private String limitingRule;


}
