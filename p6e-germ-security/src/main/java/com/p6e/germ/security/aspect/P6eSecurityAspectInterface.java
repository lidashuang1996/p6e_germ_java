package com.p6e.germ.security.aspect;

import com.p6e.germ.common.http.P6eHttpServlet;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * 安全验证的接口
 * 泛型 T 为用户继承扩展的安全模型类
 * @author lidashuang
 * @version 1.0
 */
public interface P6eSecurityAspectInterface<T extends P6eSecurityAspectModel> {

    /**
     * 认证信息错误
     */
    public static final String ERROR_AUTH_INFO = "ERROR_AUTH_INFO";

    /**
     * 黑名单路径错误
     */
    public static final String ERROR_BLACK_LIST_PATH = "ERROR_BLACK_LIST_PATH";

    /**
     * 用户安全验证过程
     * @param httpServlet 安全验证的 HTTP 对象
     * @param args 安全验证的切面请求参数
     * @return 用户安全验证模型类
     * @throws Throwable 用户安全验证过程可能出现的异常
     */
    public T authentication(final P6eHttpServlet httpServlet, final Object[] args) throws Throwable;

    /**
     * 出现异常的情况
     * @param httpServlet 安全验证的 HTTP 对象
     * @param error 错误的类型
     * @return 处理安全验证出现异常的结果
     * @throws Throwable 处理异常情况出现的异常
     */
    public default Object error(final P6eHttpServlet httpServlet, final String error) throws Throwable {
        PrintWriter printWriter = null;
        try {
            final HttpServletResponse httpServletResponse = httpServlet.getHttpServletResponse();
            if (httpServletResponse != null) {
                printWriter = httpServletResponse.getWriter();
                httpServletResponse.setHeader("Content-Type", "application/json;charset=UTF-8");
                switch (error) {
                    case ERROR_AUTH_INFO:
                        printWriter.write("{\"code\":401,\"message\":\"ERROR_AUTH_EXCEPTION\"}");
                        break;
                    case ERROR_BLACK_LIST_PATH:
                        printWriter.write("{\"code\":402,\"message\":\"ERROR_AUTH_BLACK_LIST_PATH\"}");
                        break;
                    default:
                        printWriter.write("{\"code\":500,\"message\":\"SERVICE_INTERNAL_EXCEPTION\"}");
                        break;
                }
                printWriter.flush();
            }
            return null;
        } catch (Exception e) {
            throw new Exception(e);
        } finally {
            if (printWriter != null) {
                printWriter.close();
            }
        }
    }
}
