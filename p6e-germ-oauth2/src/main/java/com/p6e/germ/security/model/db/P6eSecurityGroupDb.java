package com.p6e.germ.security.model.db;

import com.p6e.germ.security.model.base.P6eBaseDb;
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
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class P6eSecurityGroupDb extends P6eBaseDb implements Serializable {
    private Integer id;
    private String name;
    private String describe;
    private Integer weight;
    private String createDate;
    private String updateDate;
    private String operate;
}
