package com.p6e.germ.oauth2.model;

import java.io.Serializable;

/**
 * 请求结果的封装
 * @author LiDaShuang
 * @version 1.0
 */
public class P6eResultModel implements Serializable {
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

    public static P6eResultModel build(String content) {
        return new P6eResultModel(content);
    }

    public static P6eResultModel build(String content, Object data) {
        return new P6eResultModel(content, data);
    }

    public static P6eResultModel build(int code, String message) {
        return new P6eResultModel(code, message, null);
    }

    public static P6eResultModel build(int code, String message, Object data) {
        return new P6eResultModel(code, message, data);
    }

    private P6eResultModel() { }

    private P6eResultModel(String content) {
        this(content, null);
    }

    private P6eResultModel(String content, Object data) {
        try {
            // 这里采用符号分割要返回的数据
            String[] ps = content.split("-");
            // 读取 code 数据
            int code = Integer.parseInt(ps[0]);
            // 读取消息的数据
            String message = ps[1];
            this.setCode(code);
            this.setData(data);
            this.setMessage(message);
        } catch (Exception e) {
            this.setCode(500);
            this.setData(null);
            this.setMessage("Parsing return string exceptions ...");
        }
    }

    private P6eResultModel(Integer code, String message, Object data) {
        this.setCode(code);
        this.setMessage(message);
        this.setData(data);
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

    public void setData(Object data) {
        this.data = data;
    }
}

