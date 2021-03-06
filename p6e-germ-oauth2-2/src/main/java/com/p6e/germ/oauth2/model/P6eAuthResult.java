package com.p6e.germ.oauth2.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author lidashuang
 * @version 1.0
 */
@Data
public class P6eAuthResult implements Serializable {
    private Integer id;
    private String icon;
    private String name;
    private String describe;
    private String mark;
}
