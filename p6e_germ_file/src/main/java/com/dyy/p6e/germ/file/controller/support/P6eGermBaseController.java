package com.dyy.p6e.germ.file.controller.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class P6eGermBaseController {

    private static final Logger logger = LoggerFactory.getLogger(P6eGermBaseController.class);

    /**
     * 获取基础的请求与的对象 ServletRequestAttributes
     * @return ServletRequestAttributes 返回的对象
     */
    private static ServletRequestAttributes getServletRequestAttributes() {
        return (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    }

    /**
     * 获取 HttpServletRequest
     * @return HttpServletRequest
     */
    public static HttpServletRequest getRequest() {
        return getServletRequestAttributes().getRequest();
    }

    /**
     * 获取 HttpServletResponse
     * @return HttpServletResponse
     */
    public static HttpServletResponse getResponse() {
        return getServletRequestAttributes().getResponse();
    }

    /**
     * 获取客户端 IP
     * @param request Http 请求的对象 HttpServletRequest
     * @return 获取到的客户端 IP
     */
    private static String obtainIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个ip值，第一个ip才是真实ip
            if (ip.contains(",")) ip = ip.split(",")[0];
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
        if (ip == null) ip = "0.0.0.0";
        return ip;
    }

    /**
     * 请求之前的日志
     * @param o 日志打印对象
     */
    protected void loggerRequest(Object o) {
        logger.info("[ IP: " + obtainIp(getRequest()) + " ClASS: "
                + obtainClass() + " ] request => " + (o == null ? "{}" : o.toString()));
    }

    /**
     * 请求之后的日志
     * @param o 日志打印对象
     */
    protected void loggerResponse(Object o) {
        logger.info("[ IP: " + obtainIp(getRequest()) + " ClASS: "
                + obtainClass() + " ] response => " + (o == null ? "{}" : o.toString()));
    }

    /**
     * 请求日志
     * @param o 日志打印对象
     */
    protected void logger(Object o) {
        logger.info("[ IP: " + obtainIp(getRequest()) + " ClASS: "
                + obtainClass() + " ] => " + (o == null ? "{}" : o.toString()));
    }

    private String obtainClass() {
        String[] names = this.getClass().getName().split("\\.");
        return names[names.length - 1];
    }

}
