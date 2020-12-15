package com.p6e.germ.security.aspect;

import com.p6e.germ.security.model.P6eSecurityModel;
import com.p6e.germ.security.http.P6eHttpServlet;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * 安全实现的接口
 * @author lidashuang
 * @version 1.0
 */
public interface P6eSecurityAspectInterface<T extends P6eSecurityModel> {

    /**
     * 用户认证过程
     * @param p6eHttpServlet HTTP 对象
     * @param args 请求的参数
     * @return 用户认证信息
     * @throws Throwable 认证过程可能出现的异常
     */
    public T authentication(final P6eHttpServlet p6eHttpServlet, final Object[] args) throws Throwable;

    /**
     * 处理未认证过程
     * @param p6eHttpServlet HTTP 对象
     * @return 处理未认证数据返回方法
     * @throws Throwable 处理未认证出现的异常
     */
    public default Object unauthorized(final P6eHttpServlet p6eHttpServlet) throws Throwable {
        final HttpServletResponse httpServletResponse = p6eHttpServlet.getHttpServletResponse();
        if (httpServletResponse != null) {
            final PrintWriter printWriter = httpServletResponse.getWriter();
            printWriter.write("{\"code\":401,\"message\":\"ERROR_NO_AUTH\"}");
            printWriter.flush();
            printWriter.close();
        }
        return null;
    }

}
