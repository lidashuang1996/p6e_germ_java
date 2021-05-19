package com.p6e.germ.oauth2.context.controller.support.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author lidashuang
 * @version 1.0
 */
@Data
public class P6eLogDataResult implements Serializable {
    private Integer id;
    private String uid;
    private String cid;
    private String type;
    private String date;
}
