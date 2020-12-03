package com.p6e.germ.oauth2.model.dto;

import com.p6e.germ.oauth2.model.base.P6ePaging;
import lombok.Data;

import java.io.Serializable;

/**
 * @author lidashuang
 * @version 1.0
 */
@Data
public class P6eClientParamDto extends P6ePaging implements Serializable {
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
