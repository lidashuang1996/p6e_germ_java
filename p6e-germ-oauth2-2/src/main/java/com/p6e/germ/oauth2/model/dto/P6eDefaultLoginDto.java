package com.p6e.germ.oauth2.model.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author lidashuang
 * @version 1.0
 */
@Data
public class P6eDefaultLoginDto implements Serializable {
    private String account;
    private String password;
    private String voucher;
    private String mark;
}
