package com.p6e.germ.oauth2.application;

import com.p6e.germ.oauth2.model.dto.P6eDefaultLoginDto;
import com.p6e.germ.oauth2.model.dto.P6eLoginDto;
import com.p6e.germ.oauth2.model.dto.P6eVerificationLoginDto;

/**
 * @author lidashuang
 * @version 1.0
 */
public interface P6eLoginService {

    public P6eLoginDto verification(P6eVerificationLoginDto param);

    public P6eLoginDto defaultLogin(P6eDefaultLoginDto param);

    public P6eLoginDto qqLogin();

    public P6eLoginDto weChatLogin();

    public P6eLoginDto scanCodeLogin();
}
