package com.dyy.p6e.germ.file2.controller.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

/**
 * 基础的 controller 类
 * @author lidashuang
 * @version 1.0
 */
public class P6eBaseController {

    /**
     * 注入的日志对象
     */
    protected static final Logger LOGGER = LoggerFactory.getLogger(P6eBaseController.class);

    /**
     * 获取列表的数据转为字符串
     * @param data 数据列表
     * @return 结果数据内容
     */
    private static String obtainData(List<String> data) {
        if (data == null) {
            return "";
        } else {
            final StringBuilder sb = new StringBuilder();
            for (String d : data) {
                sb.append(d).append(",");
            }
            return sb.substring(0, sb.length() - 1);
        }
    }

    /**
     * 获取客户端 IP
     * @param request Http 请求的对象 ServerHttpRequest
     * @return 获取到的客户端 IP
     */
    protected static String obtainIp(ServerHttpRequest request) {
        final String f = ",";
        final String unknown = "UNKNOWN";
        final HttpHeaders httpHeaders = request.getHeaders();
        String ip = obtainData(httpHeaders.get("x-forwarded-for"));
        if (ip != null && ip.length() != 0 && !unknown.equalsIgnoreCase(ip)) {
            if (ip.contains(f)) {
                ip = ip.split(f)[0];
            }
        }
        if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
            ip = obtainData(httpHeaders.get("Proxy-Client-IP"));
        }
        if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
            ip = obtainData(httpHeaders.get("WL-Proxy-Client-IP"));
        }
        if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
            ip = obtainData(httpHeaders.get("HTTP_CLIENT_IP"));
        }
        if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
            ip = obtainData(httpHeaders.get("HTTP_X_FORWARDED_FOR"));
        }
        if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
            ip = obtainData(httpHeaders.get("X-Real-IP"));
        }
        if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
            ip = "0.0.0.0";
        }
        return ip;
    }

    /**
     * HTTP 请求的基础信息
     * @return 基础信息内容
     */
    public static String logBaseInfo(ServerHttpRequest request) {
        try {
            return "[ " + obtainIp(request) + " ] " + request.getMethodValue() + " " + URLDecoder.decode(request.getURI().toString(), "UTF-8") + " ==> ";
        } catch (UnsupportedEncodingException e) {
            return "[ " + obtainIp(request) + " ] " + request.getMethodValue() + " " + request.getURI().toString() + " ==> ";
        }
    }

}
