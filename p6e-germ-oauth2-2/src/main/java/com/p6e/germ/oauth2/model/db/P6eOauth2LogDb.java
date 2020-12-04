package com.p6e.germ.oauth2.model.db;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author lidashuang
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class P6eOauth2LogDb extends P6eBaseDb implements Serializable {
    private Integer id;
    private String uid;
    private String cid;
    private String type;
    private String date;
}
