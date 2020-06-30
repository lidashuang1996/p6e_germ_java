package com.p6e.germ.oauth2.model.dto;

import com.p6e.germ.oauth2.model.base.P6eBaseResultDto;
import lombok.Data;

import java.io.Serializable;

/**
 * @author lidashuang
 * @version 1.0
 */
@Data
public class P6eTokenResultDto extends P6eBaseResultDto implements Serializable {
    private String access_token;
    private String token_type;
    private Integer expires_in;
    private String refresh_token;
    private String scope;



}
