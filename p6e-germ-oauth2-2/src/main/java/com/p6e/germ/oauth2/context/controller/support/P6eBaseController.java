package com.p6e.germ.oauth2.context.controller.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 该类为 controller base 类
 * @author LiDaShuang
 * @version 1.0
 */
public class P6eBaseController {

    /** 创建全局 Controller 的日志对象 */
    protected static final Logger LOGGER = LoggerFactory.getLogger(P6eBaseController.class);
    protected static final String HTML_CONTENT_TYPE = "text/html;charset=UTF-8";

    public static String INDEX_HTML = null;

    /**
     * 请求头内容的前缀
     */
    protected static final String AUTH_HEADER_BEARER = "Bearer ";

    protected static final int AUTH_HEADER_BEARER_LENGTH = 7;

    /** HTML 数据名称 */
    protected static final String HTML_DATA_NAME = "__DATA__";

    public static final String COOKIES_ACCESS_TOKEN_NAME = "P6E_AAA";
    public static final String COOKIES_REFRESH_TOKEN_NAME = "P6E_BBB";


    /**
     * 请求的携带认证信息的参数
     */
    protected static final String AUTH_PARAM_NAME = "access_token";

    /**
     * 请求头名称
     */
    protected static final String AUTH_HEADER_NAME = "authorization";

    /**
     * CODE 认证类型
     */
    protected static final String CODE_TYPE = "CODE";

    /**
     * 简化认证类型
     */
    protected static final String TOKEN_TYPE = "TOKEN";

    /**
     * 认证的类型 - 密码
     */
    protected static final String PASSWORD_TYPE = "PASSWORD";

    /**
     * 认证的类型 - CODE
     */
    protected static final String AUTH_CODE_TYPE = "AUTHORIZATION_CODE";

    /**
     * 认证的类型 - 客户端
     */
    protected static final String CLIENT_TYPE = "CLIENT_CREDENTIALS";

    /**
     * 客户端参数名称
     */
    protected static final String CLIENT_ID_PARAM = "client_id";

    /**
     * 客户端密钥参数名称
     */
    protected static final String CLIENT_SECRET_PARAM = "client_secret";

    /**
     * 重定向 URL 参数名称
     */
    protected static final String REDIRECT_URI_PARAM = "redirect_uri";

    /**
     * 资源类型参数名称
     */
    protected static final String RESPONSE_TYPE_PARAM = "response_type";

    /**
     * 资源类型参数名称
     */
    protected static final String GRANT_TYPE_PARAM = "grant_type";

    /**
     * 请求的携带认证信息的参数
     */
    protected static final String REFRESH_TOKEN_NAME = "refresh_token";

    /**
     * 获取基础的请求与的对象 ServletRequestAttributes
     * @return ServletRequestAttributes 返回的对象
     */
    private static ServletRequestAttributes getServletRequestAttributes() {
        return (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    }


    public static String getAccessToken() {
        return getAccessToken(null);
    }

    public static String getAccessToken(String t) {
        final HttpServletRequest request = getRequest();
        if (t == null) {
            t = request.getParameter(AUTH_PARAM_NAME);
            if (t == null) {
                final String content = request.getHeader(AUTH_HEADER_NAME);
                if (content != null && content.startsWith(AUTH_HEADER_BEARER)) {
                    t = content.substring(AUTH_HEADER_BEARER_LENGTH);
                }
                if (t == null) {
                    final Cookie[] cookies = request.getCookies();
                    for (final Cookie cookie : cookies) {
                        if (cookie.getName().equals(COOKIES_ACCESS_TOKEN_NAME)) {
                            t = cookie.getValue();
                            break;
                        }
                    }
                }
            }
        }
        return t;
    }



    /**
     * 获取 HttpServletRequest 对象
     * @return HttpServletRequest
     */
    public static HttpServletRequest getRequest() {
        return getServletRequestAttributes().getRequest();
    }

    /**
     * 获取 HttpServletResponse 对象
     * @return HttpServletResponse
     */
    public static HttpServletResponse getResponse() {
        return getServletRequestAttributes().getResponse();
    }

    /**
     * 获取IP的方法
     * @return 具体 IP 的内容
     */
    public static String obtainIp() {
        HttpServletRequest request = getRequest();
        String ip = request.getHeader("x-forwarded-for");
        if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个ip值，第一个ip才是真实ip
            if (ip.contains(",")) {
                ip = ip.split(",")[0];
            }
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (ip == null) {
            ip = "0.0.0.0";
        }
        return ip;
    }
}
