package com.p6e.germ.oauth2.application;

import com.p6e.germ.oauth2.application.impl.P6eAuthServiceImpl;
import com.p6e.germ.oauth2.application.impl.P6eClientDataServiceImpl;
import com.p6e.germ.oauth2.application.impl.P6eLogDataServiceImpl;
import com.p6e.germ.oauth2.application.impl.P6eLoginServiceImpl;
import com.p6e.germ.oauth2.infrastructure.utils.P6eSpringUtil;

/**
 * @author lidashuang
 * @version 1.0
 */
public final class P6eApplication {

    public static P6eAuthService auth;
    public static P6eLoginService login;
    public static P6eLogDataService logData;
    public static P6eClientDataService clientData;

    public static void init() {
        auth = P6eSpringUtil.getBean(P6eAuthService.class, new P6eAuthServiceImpl());
        login = P6eSpringUtil.getBean(P6eLoginService.class, new P6eLoginServiceImpl());
        logData = P6eSpringUtil.getBean(P6eLogDataService.class, new P6eLogDataServiceImpl());
        clientData = P6eSpringUtil.getBean(P6eClientDataService.class, new P6eClientDataServiceImpl());
    }

}
