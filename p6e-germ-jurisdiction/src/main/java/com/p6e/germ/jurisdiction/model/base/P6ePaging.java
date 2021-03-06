package com.p6e.germ.jurisdiction.model.base;

import lombok.Data;

import java.io.Serializable;

/**
 * @author lidashuang
 * @version 1.0
 */
@Data
public class P6ePaging implements Serializable {
    private Integer page;
    private Integer size;
}
