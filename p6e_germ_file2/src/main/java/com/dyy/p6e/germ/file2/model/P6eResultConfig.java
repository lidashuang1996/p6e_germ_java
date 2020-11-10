package com.dyy.p6e.germ.file2.model;

import java.io.Serializable;

/**
 * 请求结果的配置文件
 * @author LiDaShuang
 * @version 1.0
 */
public final class P6eResultConfig implements Serializable {
    /** 参数异常 */
    public static final String ERROR_PARAM_EXCEPTION = "400-ERROR_PARAM_EXCEPTION";

    /** 文件太大 */
    public static final String ERROR_FILE_SIZE_EXCEPTION = "400-ERROR_FILE_SIZE_EXCEPTION";
    /** 文件后缀名不符合 */
    public static final String ERROR_FILE_SUFFIX_EXCEPTION = "400-ERROR_FILE_SUFFIX_EXCEPTION";


    /** 资源不存在 */
    public static final String RESOURCES_NO_EXIST = "RESOURCES_NO_EXIST";
    /** 资源不存在 */
    public static final String ERROR_RESOURCES_NO_EXIST = "400-ERROR_RESOURCES_NO_EXIST";
    /** 资源操作错误 */
    public static final String ERROR_RESOURCES_OPERATION = "500-ERROR_RESOURCES_OPERATION";
    /** 资源存在关联数据 */
    public static final String ERROR_RESOURCES_EXISTENCE_RELATION_DATA = "400-ERROR_RESOURCES_EXISTENCE_RELATION_DATA";
    /** 服务器内部出现异常 */
    public static final String ERROR_SERVICE_INSIDE = "500-ERROR_SERVICE_INSIDE";
    /** 该功能已被禁用 */
    public static final String ERROR_FUNCTION_DISABLE = "400-ERROR_FUNCTION_DISABLE";
    /** 认证出现异常 */
    public static final String ERROR_AUTH = "400-ERROR_AUTH";


    public static final String ERROR_404_ERROR_NOT_FOUND = "404-ERROR_NOT_FOUND";
    public static final String ERROR_405_ERROR_METHOD_NOT_ALLOWED = "405-ERROR_METHOD_NOT_ALLOWED";


    public static final String ERROR_400_ERROR_PARAM_EXCEPTION = "400-ERROR_PARAM_EXCEPTION";



    /** 账号已经存在 */
    public static final String ERROR_ACCOUNT_EXISTENCE = "400-ERROR_ACCOUNT_EXISTENCE";
    /** 账号密码错误 */
    public static final String ERROR_ACCOUNT_OR_PASSWORD = "400-ERROR_ACCOUNT_OR_PASSWORD";

    /** 认证出现异常 */
    public static final String AUTH_NO_EXISTENCE = "AUTH_NO_EXISTENCE";
    /** 认证出现异常 */
    public static final String ERROR_AUTH_NO_EXISTENCE = "400-ERROR_AUTH_NO_EXISTENCE";
    public static final String JURISDICTION_NO_EXISTENCE = "JURISDICTION_NO_EXISTENCE";
    /** 权限出现异常 */
    public static final String ERROR_JURISDICTION_NO_EXISTENCE = "400-ERROR_JURISDICTION_NO_EXISTENCE";

    /** TOKEN出现异常 */
    public static final String ERROR_TOKEN_NO_EXISTENCE = "400-ERROR_TOKEN_NO_EXISTENCE";

    /** 成功 */
    public static final String SUCCESS = "200-SUCCESS";

    /** 凭证生成失败 */
    public static final String ERROR_VOUCHER_GENERATE = "500-ERROR_VOUCHER_GENERATE";

    /** 凭证生成失败 */
    // public static final String ERROR_ACCOUNT_OR_PASSWORD = "500-ERROR_ACCOUNT_OR_PASSWORD";

    /** 默认数据无法删除 */
    public static final String ERROR_BAN_DEFAULT_DATA_OPERATE = "400-ERROR_BAN_DEFAULT_DATA_OPERATE";


}
