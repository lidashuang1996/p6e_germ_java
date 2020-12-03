package com.p6e.germ.oauth2.application;

import com.p6e.germ.oauth2.application.impl.P6eAuthServiceImpl;
import com.p6e.germ.oauth2.application.impl.P6eLoginServiceImpl;
import com.p6e.germ.oauth2.infrastructure.utils.P6eSpringUtil;

/**
 * @author lidashuang
 * @version 1.0
 */
public final class P6eApplication {

    public static P6eAuthService auth;
    public static P6eLoginService login;

    public static void init() {
        auth = P6eSpringUtil.getBean(P6eAuthService.class, new P6eAuthServiceImpl());
        login = P6eSpringUtil.getBean(P6eLoginService.class, new P6eLoginServiceImpl());
    }

}
