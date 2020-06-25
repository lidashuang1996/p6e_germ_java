package com.p6e.germ.oauth2.model;

import java.io.Serializable;

/**
 * 请求结果的配置文件
 * @author LiDaShuang
 * @version 1.0
 */
public final class P6eResultConfig implements Serializable {
    /** 参数异常 */
    public static final String ERROR_PARAM_EXCEPTION = "400-ERROR_PARAM_EXCEPTION";
    /** 资源不存在 */
    public static final String ERROR_RESOURCES_INEXISTENCE = "400-ERROR_RESOURCES_INEXISTENCE";

    /** 服务器内部出现异常 */
    public static final String ERROR_SERVICE_INSIDE = "500-ERROR_SERVICE_INSIDE";


    /** 成功 */
    public static final String SUCCESS = "200-SUCCERR";
    public static final String SUCCERR_USERS_GET = "200-SUCCERR_USERS_GET";
    public static final String SUCCERR_USERS_LIST = "200-SUCCERR_USERS_LIST";
    public static final String SUCCERR_USERS_CREATE = "200-SUCCERR_USERS_CREATE";
    public static final String SUCCERR_USERS_DELETE = "200-SUCCERR_USERS_DELETE";
    public static final String SUCCERR_USERS_UPDATE = "200-SUCCERR_USERS_UPDATE";


    public static final String ERROR_ACCOUNT_OR_PASSOWR = "400-ERROR_ACCOUNT_OR_PASSOWR";

}
