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
    public static final String SUCCESS = "200-SUCCESS";

    /** 凭证生成失败 */
    public static final String ERROR_VOUCHER_GENERATE = "500-ERROR_VOUCHER_GENERATE";

}
