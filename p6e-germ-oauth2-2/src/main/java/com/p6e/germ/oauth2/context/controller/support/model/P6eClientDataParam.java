package com.p6e.germ.oauth2.context.controller.support.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author lidashuang
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class P6eClientDataParam extends P6eBaseParam implements Serializable {
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
