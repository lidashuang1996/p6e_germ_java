package com.p6e.germ.oauth2.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @author lidashuang
 * @version 1.0
 */
@Data
@Accessors(chain = true)
public class P6ePagingResult<T> implements Serializable {
    private Integer page;
    private Integer size;
    private Long total;
    private List<T> list;
}
