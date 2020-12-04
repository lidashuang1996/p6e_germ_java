package com.p6e.germ.oauth2.model.base;

import com.p6e.germ.oauth2.model.P6eModel;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author lidashuang
 * @version 1.0
 */
@Data
public class P6eResultDto<T> extends P6ePaging implements Serializable {
    private P6eModel.Error error;
    private List<T> list;
    private Long total;
}
