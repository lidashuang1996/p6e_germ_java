package com.p6e.germ.oauth2.domain.keyvalue;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author lidashuang
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class P6eMarkKeyValue implements Serializable {
    private Integer id;
    private String scope;
    private String state;
    private String clientId;
    private String responseType;
    private String redirectUri;
}
