package com.p6e.germ.jurisdiction.http;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author lidashuang
 * @version 1.0
 */
public final class P6eHttpServlet {

    /** 全局定义服务的 CLASS 对象 */
    private static Class<? extends P6eHttpServlet> CLAZZ = P6eHttpServlet.class;

    /** 替换 CLASS 对象方法 */
    public static void displace(Class<? extends P6eHttpServlet> clazz) {
        CLAZZ = clazz;
    }

    /**
     * 创建 CLASS 对象实例
     * @return CLASS 对象实例
     * @throws Exception 创建过程中出现的异常
     */
    public static P6eHttpServlet newInstance() throws Exception {
        return CLAZZ.newInstance();
    }

    private P6eHttpServlet() { }

    /**
     * 获取的 HTTP 服务
     * @return HttpServletRequest 对象
     */
    public HttpServletRequest getHttpServletRequest() {
        final ServletRequestAttributes attributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return attributes == null ? null : attributes.getRequest();
    }

    /**
     * 获取的 HTTP 服务
     * @return HttpServletResponse 对象
     */
    public HttpServletResponse getHttpServletResponse() {
        final ServletRequestAttributes attributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return attributes == null ? null : attributes.getResponse();
    }

}
