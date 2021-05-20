package com.p6e.germ.oauth2.model.base;

import com.p6e.germ.oauth2.model.P6eResultModel;
import lombok.Data;

import java.io.Serializable;

/**
 * @author lidashuang
 * @version 1.0
 */
@Data
public class P6eBaseDtoResult implements Serializable {
    /** 页码 */
    private Integer page;
    /** 长度 */
    private Integer size;
    /** 错误的描述 */
    private P6eResultModel.Error error;
}
