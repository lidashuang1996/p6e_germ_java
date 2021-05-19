package com.p6e.germ.jurisdiction.context.support;

import com.p6e.germ.common.config.P6eConfig;
import com.p6e.germ.common.http.P6eHttpServlet;
import com.p6e.germ.common.utils.P6eIpUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Arrays;

/**
 * @author lidashuang
 * @version 1.0
 */
@Aspect
@Order(10)
@Component
public class P6eBaseVoucherConfig {

    /**
     * 读取配置文件对象
     */
    @Resource
    private P6eConfig config;

    /**
     * 获取拦截的包的路径方法
     */
    @Pointcut("execution(* com.p6e.germ.jurisdiction.context.controller.*.* (..))")
    public void interceptor() {}

    /**
     * 前增强
     * 读取 interceptor() 方法上面的注解
     * @param jp 参数对象
     */
    @Before("interceptor()")
    public void beforeInterceptor(final JoinPoint jp) {
        before(jp);
    }

    /**
     * 前运行结束增强
     * 读取 interceptor() 方法上面的注解
     * @param ret 参数对象
     */
    @AfterReturning(returning = "ret", pointcut = "interceptor()")
    public void afterReturningInterceptor(final Object ret) {
        afterReturning(ret);
    }

    /**
     * 后出现异常增强
     * 读取 interceptor() 方法上面的注解
     * @param jp 参数对象
     */
    @AfterThrowing("interceptor()")
    public void afterThrowsInterceptor(final JoinPoint jp){
        afterThrows(jp);
    }

    /**
     * 后增强
     * 读取 interceptor() 方法上面的注解
     * @param jp 参数对象
     */
    @After("interceptor()")
    public void afterInterceptor(final JoinPoint jp){
        after(jp);
    }

    /**
     * 环绕增强
     * 读取 interceptor() 方法上面的注解
     * @param pjp 参数对象
     * @return 环绕增强的结果
     * @throws Throwable 可能出现的异常
     */
    @Around("interceptor()")
    public Object aroundInterceptor(final ProceedingJoinPoint pjp) throws Throwable {
        return around(pjp);
    }

    /**
     * spring aop 切面前置增强
     * @param jp 增强代理对象
     */
    public void before(final JoinPoint jp) {
    }

    /**
     * spring aop 切面后置增强，不管结果如何都执行
     * @param ret 后置增强返回对象
     */
    public void afterReturning(final Object ret) {
    }

    /**
     * 拦截器增强过程中可能出现的异常
     * @param jp 增强代理对象
     */
    public void afterThrows(final JoinPoint jp) {
    }

    /**
     * spring aop 切面后置增强
     * @param jp 增强代理对象
     */
    public void after(final JoinPoint jp) {
    }

    /**
     * spring aop 切面环绕增强
     * @param pjp 切面对象
     * @return 动态代理后续执行的内容
     * @throws Throwable 后续执行过程中可能出现的问题
     */
    public Object around(final ProceedingJoinPoint pjp) throws Throwable {
        // 获取 HTTP 对象
        P6eHttpServlet httpServlet;
        try {
            httpServlet = P6eHttpServlet.newInstance();
        } catch (Exception e) {
            // 不知道是不是 HTTP 请求所以直接返回为 null
            return null;
        }
        // HTTP 请求对象
        final HttpServletRequest httpServletRequest = httpServlet.getHttpServletRequest();
        final HttpServletResponse httpServletResponse = httpServlet.getHttpServletResponse();
        try {
            // 读取配置文件数据
            final String[] vIps = config.getJurisdiction().getVoucher().getIp();
            final String[] vTokens = config.getJurisdiction().getVoucher().getToken();
            if (httpServletRequest != null) {
                final String ip = P6eIpUtil.get(httpServletRequest);
                final String token = httpServletRequest.getParameter("token");
                // 判断是否认证通过
                if (Arrays.asList(vIps).contains(ip) || Arrays.asList(vTokens).contains(token)) {
                    return pjp.proceed();
                }
            }
            result(httpServletResponse, "410", "IDENTITY_AUTH_FAIL");
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            result(httpServletResponse, "500", "SERVICE_INTERNAL_EXCEPTION");
            return null;
        }
    }

    /**
     * 结果处理
     * @param httpServletResponse HTTP 返回对象
     * @param code 状态码
     * @param message 状态消息
     */
    private void result(final HttpServletResponse httpServletResponse, final String code, final String message) {
        PrintWriter printWriter = null;
        try {
            if (httpServletResponse != null) {
                httpServletResponse.setHeader("Content-Type", "application/json;charset=UTF-8");
                printWriter = httpServletResponse.getWriter();
                printWriter.write("{\"code\":" + code + ",\"message\":\"" + message + "\"}");
                printWriter.flush();
            }
        } catch (Exception ee) {
            ee.printStackTrace();
        } finally {
            if (printWriter != null) {
                printWriter.close();
            }
        }
    }

}
