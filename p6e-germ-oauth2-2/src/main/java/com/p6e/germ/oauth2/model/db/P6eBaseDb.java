package com.p6e.germ.oauth2.model.db;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author lidashuang
 * @version 1.0
 */
@Data
@Accessors(chain = true)
public class P6eBaseDb implements Serializable {
    private Integer page;
    private Integer size;
    private Integer start;
    private Integer end;
    private String search;
}
