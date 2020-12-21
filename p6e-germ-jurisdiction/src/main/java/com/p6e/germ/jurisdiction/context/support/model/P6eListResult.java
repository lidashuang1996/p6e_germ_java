package com.p6e.germ.jurisdiction.context.support.model;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author lidashuang
 * @version 1.0
 */
@Data
public class P6eListResult<T> implements Serializable {
    private Long total;
    private Integer page;
    private Integer size;
    private List<T> list;
}
