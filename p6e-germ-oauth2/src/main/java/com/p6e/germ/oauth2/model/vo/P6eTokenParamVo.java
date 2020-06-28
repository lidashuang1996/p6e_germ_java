package com.p6e.germ.oauth2.model.vo;

import lombok.Data;

/**
 * @author lidashuang
 * @version 1.0
 */
@Data
public class P6eTokenParamVo {

    // - `grant_type=authorization_code` -这告诉令牌端点应用程序正在使用授权码授予类型。
    //        - `code` -该应用程序包括在重定向中提供的授权代码。
    //        - `redirect_uri`-请求代码时使用的重定向URI。某些API不需要此参数，因此您需要仔细检查要访问的特定API的文档。
    //        - `client_id` -应用程序的客户端ID。
    //        - `client_secret`-应用程序的客户端机密。这样可以确保获取访问令牌的请求仅来自应用程序，而不是来自可能已经拦截了授权代码的潜在攻击者。

    private String grant_type;
    private String code;
    private String redirect_uri;
    private String client_id;
    private String client_secret;

}
