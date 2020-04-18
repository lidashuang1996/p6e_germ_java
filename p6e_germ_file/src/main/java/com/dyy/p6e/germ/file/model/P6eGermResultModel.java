package com.dyy.p6e.germ.file.model;

import com.dyy.p6e.germ.file.controller.support.P6eGermBaseController;

public class P6eGermResultModel {

    private Integer code;
    private String message;
    private Object data;

    private P6eGermResultModel() { }

    private P6eGermResultModel(Integer code, String message) {
        this.setCode(code);
        this.setMessage(message);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
        P6eGermBaseController.getResponse().setStatus(code);
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

    public static P6eGermResultModel create(String content) {
        return create(content, null);
    }

    public static P6eGermResultModel create(String content, Object data) {
        P6eGermResultModel model = new P6eGermResultModel();
        try {
            String[] contents = content.split("-");
            model.setCode(Integer.valueOf(contents[0]));
            model.setMessage(contents[1]);
            model.setData(data);
        } catch (Exception e) {
            model.setCode(500);
            model.setMessage("Result model create error.");
        }
        return model;
    }

    public static P6eGermResultModel create(int code, String message) {
        return new P6eGermResultModel(code, message);
    }

    @Override
    public String toString() {
        return "{" + "\"code\":" +
                code +
                ",\"message\":\"" +
                message + '\"' +
                ",\"data\":" +
                data +
                '}';
    }
}
