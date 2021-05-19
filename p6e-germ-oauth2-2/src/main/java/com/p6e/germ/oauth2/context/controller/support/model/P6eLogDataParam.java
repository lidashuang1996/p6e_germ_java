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
public class P6eLogDataParam extends P6eBaseParam implements Serializable {
    private Integer id;
    private String uid;
    private String cid;
    private String type;
}