package com.p6e.germ.oauth2.model.vo;

import lombok.Data;

/**
 * @author lidashuang
 * @version 1.0
 */
@Data
public class P6eSignInResultVo {
    private String code;
    private String uri;
    private String data;
}
