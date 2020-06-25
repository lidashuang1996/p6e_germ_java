package com.p6e.germ.oauth2.model.db;

import com.p6e.germ.oauth2.model.base.P6eBaseDb;
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
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class P6eClientDb extends P6eBaseDb implements Serializable {
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
