package com.p6e.germ.oauth2.context.controller.support.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author lidashuang
 * @version 1.0
 */
@Data
public class P6eAuthModelParam implements Serializable {
    private String response_type;
    private String client_id;
    private String redirect_uri;
    private String scope;
    private String state;
}
