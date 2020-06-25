package com.p6e.germ.oauth2.model.db;

import com.p6e.germ.oauth2.model.base.P6eBaseDb;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author lidashuang
 * @version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class P6eLogDb extends P6eBaseDb implements Serializable {
    private Integer id;
    private Integer cid;
    private Integer uid;
    private String type;
    private String date;
}
