package com.p6e.germ.oauth2.application;

import com.p6e.germ.common.utils.P6eSpringUtil;
import com.p6e.germ.oauth2.application.impl.*;

/**
 * 应用服务的管理中心
 * @author lidashuang
 * @version 1.0
 */
public final class P6eApplication {

    /** 认证服务 */
    public static P6eAuthService auth;
    /** 登录服务 */
    public static P6eLoginService login;
    /** 日志服务 */
    public static P6eLogDataService logData;
    /** 用户服务 */
    public static P6eUserDataService userData;
    /** 客户端服务 */
    public static P6eClientDataService clientData;

    /**
     * 初始化的操作
     */
    public static void init() {
        auth = P6eSpringUtil.getBean(P6eAuthService.class, new P6eAuthServiceImpl());
        login = P6eSpringUtil.getBean(P6eLoginService.class, new P6eLoginServiceImpl());
        logData = P6eSpringUtil.getBean(P6eLogDataService.class, new P6eLogDataServiceImpl());
        userData = P6eSpringUtil.getBean(P6eUserDataService.class, new P6eUserDataServiceImpl());
        clientData = P6eSpringUtil.getBean(P6eClientDataService.class, new P6eClientDataServiceImpl());
    }

}
