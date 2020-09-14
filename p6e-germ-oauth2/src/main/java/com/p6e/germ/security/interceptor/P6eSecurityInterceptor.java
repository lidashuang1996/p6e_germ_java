package com.p6e.germ.security.interceptor;

import com.p6e.germ.security.achieve.P6eSecurity;
import com.p6e.germ.security.achieve.P6eSecurityModel;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lidashuang
 * @version 1.0
 */
@Aspect
@Order(10)
@Component
public class P6eSecurityInterceptor {

    /** 注入日志系统 */
    private static final Logger LOGGER = LoggerFactory.getLogger(P6eSecurityInterceptor.class);
    private static final List<String> WHITE_LIST = new ArrayList<>();
    private static final List<String> BLOCK_LISt = new ArrayList<>();

    private static final String HEADER_AUTH_NAME = "";

    @Autowired
    public P6eSecurityInterceptor() {

    }

    private static boolean isList(final String path, final List<String> list) {
        final String[] ps = path.split("\\.");
        for (final String content : list) {
            boolean b = true;
            final String[] cs = content.split("\\.");
            if (cs.length != ps.length && !"*".equals(cs[cs.length - 1])) {
                break;
            }
            int len = Math.min(ps.length, cs.length);
            for (int i = 0; i < len; i++) {
                if (!(ps[i].equals(cs[i]) || "*".equals(cs[i]))) {
                    b = false;
                    break;
                }
            }
            if (b) {
                return true;
            }
        }
        return false;
    }

    private static boolean isBlockList(final String path) {
        return isList(path, BLOCK_LISt);
    }

    private static boolean isWhiteList(final String path) {
        return isList(path, WHITE_LIST);
    }

    private static Object[] injectionData(final JoinPoint jp, final P6eSecurityModel model) {
        // 获取参数参数值
        final Object[] args = jp.getArgs();
        // 参数遍历
        for (int i = 0; i < args.length; i++) {
            if (args[i] instanceof P6eSecurityModel) {
                args[i] = model;
            }
        }
        return args;
    }

    /**
     * spring aop 切面前置增强
     * @param jp 增强代理对象
     */
    private void before(final JoinPoint jp) {
    }

    /**
     * spring aop 切面后置增强
     * @param jp 增强代理对象
     */
    private void after(final JoinPoint jp) {
    }

    /**
     * spring aop 切面后置增强，不管结果如何都执行
     * @param ret 后置增强返回对象
     */
    private void afterReturning(final Object ret) {
    }

    /**
     * 拦截器增强过程中可能出现的异常
     * @param jp 增强代理对象
     */
    private void afterThrows(final JoinPoint jp){
    }

    /**
     * spring aop 切面环绕增强
     * @param pjp 切面对象
     * @return 动态代理后续执行的内容
     * @throws Throwable 后续执行过程中可能出现的问题
     */
    private Object around(final ProceedingJoinPoint pjp) throws Throwable {
        final ServletRequestAttributes attributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            // 存在 attributes 是个 web 请求
            final HttpServletRequest request = attributes.getRequest();
            // 获取请求的方法路径
            final String path = pjp.getThis().getClass()
                    .getName().split("\\$\\$")[0] + "." + pjp.getSignature().getName() + "()";
            if (isBlockList(path)) {
                // 是黑名单
                // 认证没通过
                return HttpServletResponse.SC_CONTINUE;
            } else {
                // 不是是黑名单
                // 获取的注解对象
                final P6eSecurity security = ((MethodSignature)
                        pjp.getSignature()).getMethod().getAnnotation(P6eSecurity.class);
                if (security == null) {
                    // 没有开启的认证模式
                    return pjp.proceed(injectionData(pjp, null));
                } else {
                    // 获取数据
                    P6eSecurityModel model;
                    try {
                        // 获取 token
                        final String token = request.getHeader(HEADER_AUTH_NAME);
                        if (token == null) {
                            model = null;
                        } else {
                            model = new P6eSecurityModel();
                            model.setGroupModelList(new ArrayList<>());
                            model.setJurisdictionModelList(new ArrayList<>());
                        }
                    } catch (Exception e) {
                        // 忽略异常
                        model = null;
                    }
                    // 开启认证模式
                    if (isWhiteList(path)) {
                        return pjp.proceed(injectionData(pjp, model));
                    } else {
                        if (model == null) {
                            // 认证没通过
                            return HttpServletResponse.SC_CONTINUE;
                        } else {
                            // 认证通过
                            return pjp.proceed(injectionData(pjp, model));
                        }
                    }
                }
            }
        } else {
            // 不存在 attributes 直接跑出异常
            throw new Exception("ServletRequestAttributes is null.");
        }
    }


    /**
     * 声明拦截器范围的注解
     */
    @Pointcut("execution(public * com.p6e.germ.security.controller.*.*(..))")
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
