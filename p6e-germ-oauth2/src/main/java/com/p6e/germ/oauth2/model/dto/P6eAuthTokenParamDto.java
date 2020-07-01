package com.p6e.germ.oauth2.model.dto;

import com.p6e.germ.oauth2.model.base.P6eBaseParamDto;
import lombok.Data;

import java.io.Serializable;

/**
 * @author lidashuang
 * @version 1.0
 */
@Data
public class P6eAuthTokenParamDto extends P6eBaseParamDto implements Serializable {
    private String client_id;
    private String redirect_uri;
}
