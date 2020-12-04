package com.p6e.germ.oauth2.model.dto;

import com.p6e.germ.oauth2.model.base.P6eResultDto;
import lombok.Data;

import java.io.Serializable;

/**
 * @author lidashuang
 * @version 1.0
 */
@Data
public class P6eLogDataDto extends P6eResultDto<P6eLogDataDto> implements Serializable {
    private Integer id;
    private String uid;
    private String cid;
    private String type;
    private String date;
}
