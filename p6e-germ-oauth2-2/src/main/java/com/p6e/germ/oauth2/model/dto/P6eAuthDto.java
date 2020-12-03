package com.p6e.germ.oauth2.model.dto;

import com.p6e.germ.oauth2.model.base.P6eResultDto;
import lombok.Data;

import java.io.Serializable;

/**
 * @author lidashuang
 * @version 1.0
 */
@Data
public class P6eAuthDto extends P6eResultDto implements Serializable {
    private Integer id;
    private String icon;
    private String name;
    private String describe;

    private String voucher;
    private String mark;
    private String publicKey;
}
