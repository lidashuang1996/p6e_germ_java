package com.p6e.germ.oauth2.context.controller.support.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author lidashuang
 * @version 1.0
 */
@Data
public class P6eCodeLoginParam implements Serializable {
    private String code;
    private String mark;
    private String accessToken;
}