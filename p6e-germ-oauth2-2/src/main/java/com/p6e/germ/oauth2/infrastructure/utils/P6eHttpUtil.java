package com.p6e.germ.oauth2.infrastructure.utils;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * @author lidashuang
 * @version 1.0
 */
public final class P6eHttpUtil {

    /** 全局创建 OK HTTP CLIENT 对象 */
    private static final OkHttpClient OK_HTTP_CLIENT = new OkHttpClient();

    /**
     * 发送 GET 请求
     * @param url 请求的 URL 路径
     * @return 请求的结果
     */
    public static String doGet(String url) {
        try {
            final Request request = new Request.Builder().url(url).build();
            final Response response = OK_HTTP_CLIENT.newCall(request).execute();
            final ResponseBody responseBody = response.body();
            if (responseBody == null) {
                return null;
            } else {
                return responseBody.string();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public static String doPost() {
        return null;
    }

}
