package com.p6e.germ.oauth2.infrastructure.utils;

import java.util.UUID;

/**
 * @author lidashuang
 * @version 1.0
 */
public final class GeneratorUtil {

    public static String uuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

//    /**
//     * 生成回调的 url 的地址
//     * @param url url 地址
//     * @param code 认证的记号
//     * @return 生成回调的 url 的地址
//     */
//    public static String callbackUrl(String url, String code) {
//        final int index = url.indexOf("?");
//        if (index > -1) {
//            if (url.length() > index) {
//                return url.substring(0, index) + "?code=" + code + "&" + url.substring(index + 1);
//            } else {
//                return url.substring(0, index) + "?code=" + code;
//            }
//        } else {
//            return url + "?code=" + code;
//        }
//    }

}
