package com.p6e.germ.oauth2.model.dto;

import com.p6e.germ.oauth2.model.base.P6eBaseResultDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author lidashuang
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class P6eClientResultDto extends P6eBaseResultDto implements Serializable {
    private Integer id;
    private String clientName;
    private String clientId;
    private String clientSecret;
    private String clientScope;
    private String clientRedirectUri;
    private Integer clientAccessTokenStamp;
    private Integer clientAccessRefreshTokenStamp;
    private String clientDescribe;
    private String clientLimitingRule;
}
