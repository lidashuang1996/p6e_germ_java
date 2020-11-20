package com.p6e.germ.oauth2.context.controller.support.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author lidashuang
 * @version 1.0
 */
@Data
public class P6eTokenModelParam implements Serializable {
    private String code;
    private String grant_type;
    private String redirect_uri;
    private String client_id;
    private String client_secret;
}
