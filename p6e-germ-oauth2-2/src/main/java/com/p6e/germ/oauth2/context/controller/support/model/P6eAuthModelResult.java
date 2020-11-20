package com.p6e.germ.oauth2.context.controller.support.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author lidashuang
 * @version 1.0
 */
@Data
public class P6eAuthModelResult implements Serializable {
    private String error;
    private String mark;
    private String voucher;
    private String publicKey;
}
