package com.p6e.germ.oauth2.model.db;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author lidashuang
 * @version 1.0
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class P6eOauth2ClientDb extends P6eBaseDb implements Serializable {
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
