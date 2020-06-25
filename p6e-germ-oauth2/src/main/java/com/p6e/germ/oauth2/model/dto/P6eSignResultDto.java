package com.p6e.germ.oauth2.model.dto;

import com.p6e.germ.oauth2.model.base.P6eBaseResultDto;
import lombok.Data;

import java.io.Serializable;

/**
 * @version 1.0
 * @auther: lidashuang
 */
@Data
public class P6eSignResultDto extends P6eBaseResultDto implements Serializable {

    private Integer id;
    private String token;
    private String refreshToken;
    private Long expiration;


}
