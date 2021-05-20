package com.p6e.germ.oauth2.model;

/**
 * @author lidashuang
 * @version 1.0
 */
public final class P6eResultModel {

    public enum Error {
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
        SERVICE_NOT_ENABLE(20);


        private final Integer code;

        Error(Integer code) {
            this.code = code;
        }
    }

    /**
     * 返回数据的消息状态码
     */
    private Integer code;

    /**
     * 返回数据的消息内容
     *
     * message 不推荐为中文，后期如果需要支持多语言操作很麻烦
     */
    private String message;

    /**
     * 返回数据的对象
     */
    private Object data;

    public static P6eResultModel build() {
        return new P6eResultModel();
    }

    public static P6eResultModel build(Error error) {
        return new P6eResultModel(error);
    }

    public static P6eResultModel build(Object data) {
        return new P6eResultModel(data);
    }

    private P6eResultModel() {
        this.code = 0;
        this.message = "SUCCESS";
    }

    private P6eResultModel(Error error) {
        this.code = error.code;
        this.message = error.name();
    }

    private P6eResultModel(Object data) {
        this.code = 0;
        this.message = "SUCCESS";
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public P6eResultModel setData(Object data) {
        this.data = data;
        return this;
    }

}
