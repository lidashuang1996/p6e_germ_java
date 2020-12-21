package com.p6e.germ.jurisdiction.context.support.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author lidashuang
 * @version 1.0
 */
@Data
public class P6eBaseParam implements Serializable {
    private Integer page;
    private Integer size;
}
