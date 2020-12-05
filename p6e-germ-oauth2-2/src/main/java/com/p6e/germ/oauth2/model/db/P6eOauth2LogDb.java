package com.p6e.germ.oauth2.model.db;

import com.p6e.germ.oauth2.model.base.P6eBaseDb;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author lidashuang
 * @version 1.0
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class P6eOauth2LogDb extends P6eBaseDb implements Serializable {
    private Integer id;
    private Integer uid;
    private Integer cid;
    private String type;
    private String date;
}
