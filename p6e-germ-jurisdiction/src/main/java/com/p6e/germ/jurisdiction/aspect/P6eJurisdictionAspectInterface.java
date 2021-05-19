package com.p6e.germ.jurisdiction.aspect;

import com.p6e.germ.common.http.P6eHttpServlet;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * 权限验证的接口
 * 泛型 T 为用户继承扩展的权限模型类
 * @author lidashuang
 * @version 1.0
 */
public interface P6eJurisdictionAspectInterface<T extends P6eJurisdictionAspectModel> {

    /**
     * 不具备权限
     */
    public static final String ERROR_NO_JURISDICTION = "ERROR_NO_JURISDICTION";

    /**
     * 用户权限验证过程
     * @param httpServlet 权限验证的 HTTP 对象
     * @param args 权限验证的切面请求参数
     * @return 用户权限验证模型类
     * @throws Throwable 认证过程可能出现的异常
     */
    public T authentication(final P6eHttpServlet httpServlet, final Object[] args) throws Throwable;

    /**
     * 出现异常的情况
     * @param httpServlet 权限验证的 HTTP 对象
     * @param error 错误的类型
     * @return 处理权限验证出现异常的结果
     * @throws Throwable 处理异常情况出现的异常
     */
    public default Object error(final P6eHttpServlet httpServlet, final String error) throws Throwable {
        PrintWriter printWriter = null;
        try {
            final HttpServletResponse httpServletResponse = httpServlet.getHttpServletResponse();
            if (httpServletResponse != null) {
                printWriter = httpServletResponse.getWriter();
                httpServletResponse.setHeader("Content-Type", "application/json;charset=UTF-8");
                if (ERROR_NO_JURISDICTION.equals(error)) {
                    printWriter.write("{\"code\":403,\"message\":\"ERROR_NO_JURISDICTION\"}");
                } else {
                    printWriter.write("{\"code\":500,\"message\":\"SERVICE_INTERNAL_EXCEPTION\"}");
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
