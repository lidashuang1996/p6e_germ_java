package com.p6e.germ.jurisdiction.model;

/**
 * @author lidashuang
 * @version 1.0
 */
public enum P6eErrorModel {
    /**
     * 存在关联数据
     */
    RELATION_DATA_EXCEPTION(-22220),
    /**
     * SQL 异常
     */
    SQL_EXCEPTION(0),
    /**
     * 资源不存在
     */
    RESOURCES_EXIST(201),
    /**
     * 资源不存在
     */
    RESOURCES_NO_EXIST(1),
    /**
     * DB 操作异常
     */
    DATABASE_EXCEPTION(2),
    /**
     * 参数异常
     */
    PARAMETER_EXCEPTION(3),
    /**
     * 过期异常
     */
    EXPIRATION_EXCEPTION(4),
    /**
     * 服务异常
     */
    SERVICE_EXCEPTION(5),
    /**
     * 账号密码
     */
    ACCOUNT_OR_PASSWORD(6),
    /**
     * 方法异常
     */
    HTTP_METHOD_EXCEPTION(11),
    /**
     * 验证码错误
     */
    VERIFICATION_CODE_EXCEPTION(12),
    /**
     * 服务未启动
     */
    SERVICE_NOT_ENABLE(20),
    /**
     * 非法设备 Illegal device
     */
    ILLEGAL_DEVICE(1000);


    private final Integer code;

    P6eErrorModel(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
