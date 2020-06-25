package com.p6e.germ.oauth2.interceptor;

import com.p6e.germ.oauth2.controller.support.P6eBaseController;
import com.p6e.germ.oauth2.model.P6eResultModel;
import com.p6e.germ.oauth2.model.base.P6eBaseParamVo;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Spring AOP LOG
 * 使用 spring aop 切面去实现请求和返回日志的打印
 * @author LiDaShuang
 * @version 1.0
 */
@Aspect
@Component
public class P6eHttpLogInterceptor {

    /** 注入日志系统 */
    private static final Logger logger = LoggerFactory.getLogger(P6eHttpLogInterceptor.class);

    /** Http 请求的日志对象 */
    private static class HttpLogModel {
        private String url;
        private String path;
        private Object body;
        private Object result;
        private String ip;
        private String startDateTime;
        private String endDateTime;

        @Override
        public String toString() {
            return "{"
                    + "\"url\":\""
                    + url + '\"'
                    + ",\"path\":\""
                    + path + '\"'
                    + ",\"body\":"
                    + body
                    + ",\"result\":"
                    + result
                    + ",\"ip\":\""
                    + ip + '\"'
                    + ",\"startDateTime\":\""
                    + startDateTime + '\"'
                    + ",\"endDateTime\":\""
                    + endDateTime + '\"'
                    + "}";
        }
    }

    /**
     * 获取请求的方法的包名类名方法名
     * @param jp 代理的对象
     * @return 包名和类名组成的路径
     */
    private static String getPath(final JoinPoint jp) {
        final String className = jp.getThis().getClass().getName();
        final String methodsName = jp.getSignature().getName();
        final String[] cs = className.split("\\$\\$");
        return cs[0] + "." + methodsName + "()";
    }

    /**
     * 获取请求的 URL
     * @return 请求的 URL 地址
     */
    private static String getUrl() {
        final ServletRequestAttributes attributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            final HttpServletRequest request = attributes.getRequest();
            final StringBuffer stringBuffer = request.getRequestURL();
            if (request.getQueryString() != null) {
                // 如果请求的参数不为 null，就把请求的参数添加到请求的 url 后面
                stringBuffer.append("?").append(request.getQueryString());
            }
            // 得到最后的请求的 url 地址
            return stringBuffer.toString();
        } else {
            return null;
        }
    }

    /** 创建的 http 日志对象 */
    private HttpLogModel httpLogModel;

    /**
     * spring aop 切面前置增强
     * @param jp 增强代理对象
     */
    private void before(final JoinPoint jp) {
        // 创建对象
        httpLogModel = new HttpLogModel();
        // 获取请求的 url
        httpLogModel.url = getUrl();
        // 获取请求的路径
        httpLogModel.path = getPath(jp);
        // 获取请求的 IP 地址
        httpLogModel.ip = P6eBaseController.obtainIp();
        // 获取开始的时间
        httpLogModel.startDateTime = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
        // 获取请求的参数
        final List<Object> body = new ArrayList<>();
        httpLogModel.body = body;

        final Object[] jpArgs = jp.getArgs();
        for (Object jpArg : jpArgs) {
            // 我的程序的请求参数都是继承于 P6eBaseParamVo 的
            if (jpArg instanceof P6eBaseParamVo) {
                body.add(jpArg);
            }
        }
    }

    /**
     * spring aop 切面后置增强
     * @param jp 增强代理对象
     */
    private void after(final JoinPoint jp) { }

    /**
     * spring aop 切面后置增强，不管结果如何都执行
     * @param ret 后置增强返回对象
     */
    private void afterReturning(final Object ret) {
        if (ret instanceof P6eResultModel && httpLogModel != null) {
            httpLogModel.result = ret;
            httpLogModel.endDateTime = LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
            logger.info(httpLogModel.toString());
        }
        // 如果压力测试时候对内存成为阶梯状可以在考虑在这里 GC 一下
        // System.gc(); // GC 一下 ？ Spring 管理了吗 ？
    }

    /**
     * 拦截器增强过程中可能出现的遗产
     * @param jp 增强代理对象
     */
    private void afterThrows(final JoinPoint jp){ }

    /**
     * spring aop 切面环绕增强
     * @param pjp 切面对象
     * @return 动态代理后续执行的内容
     * @throws Throwable 后续执行过程中可能出现的问题
     */
    private Object around(final ProceedingJoinPoint pjp) throws Throwable {
        return pjp.proceed();
    }


    /**
     * 声明拦截器范围的注解
     */
    @Pointcut("execution(public * com.p6e.germ.oauth2.controller.*.*(..))")
    private void interceptor(){}

    @Before("interceptor()")
    public void beforeInterceptor(final JoinPoint joinPoint) {
        before(joinPoint);
    }

    @AfterReturning(returning = "ret", pointcut = "interceptor()")
    public void afterReturningInterceptor(final Object ret) {
        afterReturning(ret);
    }

    @AfterThrowing("interceptor()")
    public void afterThrowsInterceptor(final JoinPoint jp){
        afterThrows(jp);
    }

    @After("interceptor()")
    public void afterInterceptor(final JoinPoint jp){
        after(jp);
    }

    @Around("interceptor()")
    public Object aroundInterceptor(final ProceedingJoinPoint pjp) throws Throwable {
        return around(pjp);
    }

}
