package com.p6e.germ.oauth2.model.dto;

import com.p6e.germ.oauth2.model.base.P6eBaseResultDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * @author lidashuang
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class P6eAuthManageTokenResultDto extends P6eBaseResultDto implements Serializable {
    private String redirectUri;

    private String access_token;
    private String token_type;
    private Integer expires_in;
    private String refresh_token;
    private String scope;

    public String toUri() {
        try {
            return redirectUri
                    + "?access_token=" + URLEncoder.encode(access_token == null ? "" : access_token, "UTF-8")
                    + "&token_type=" + URLEncoder.encode(token_type == null ? "" : token_type, "UTF-8")
                    + "&expires_in=" + (expires_in == null ? "" : expires_in)
                    + "&refresh_token=" + URLEncoder.encode(refresh_token == null ? "" : refresh_token, "UTF-8")
                    + "&scope=" + URLEncoder.encode(scope == null ? "" : scope, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
