package com.p6e.germ.oauth2.application;

import com.p6e.germ.oauth2.model.dto.*;

/**
 * @author lidashuang
 * @version 1.0
 */
public interface P6eAuthService {

    public P6eAuthDto code(P6eCodeAuthDto param);

    public P6eAuthDto simple(P6eSimpleAuthDto param);

    public P6eAuthTokenDto codeCallback(P6eCodeCallbackAuthDto param);

    public P6eAuthTokenDto client(P6eClientAuthDto param);

    public P6eAuthTokenDto password(P6ePasswordAuthDto param);

    public P6eAuthTokenDto refresh(String token, String refreshToken);

    public P6eInfoDto info(String token);
}
