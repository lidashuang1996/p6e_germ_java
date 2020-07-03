package com.p6e.germ.oauth2.auth;

import com.p6e.germ.oauth2.model.P6eResultConfig;
import com.p6e.germ.oauth2.model.P6eResultModel;
import com.p6e.germ.oauth2.model.dto.P6eTokenClientParamDto;
import com.p6e.germ.oauth2.model.dto.P6eTokenClientResultDto;
import com.p6e.germ.oauth2.service.P6eTokenService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;

/**
 * 客户端 [ CLIENT ] 认证的注解实现
 * @author lidashuang
 * @version 1.0
 */
@Aspect
@Order(100)
@Component
public class P6eAdminAuthAchieve {

    /** 请求的头部携带认证信息的头名称 */
    private static final String AUTH_HEADER_NAME = "AUTHORIZATION";

    /** 请求的头部携带认证信息的头协议 */
    private static final String AUTH_HEADER_AGREEMENT = "BEARER";

    /** 注入日志系统 */
    private static final Logger logger = LoggerFactory.getLogger(P6eAdminAuthAchieve.class);

    /** 注入 TOKEN 服务 */
    @Resource
    private P6eTokenService p6eTokenService;

    /**
     * spring aop 切面前置增强
     * @param jp 增强代理对象
     */
    private void before(final JoinPoint jp) { }

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
        // 读取方法上面的注解对象
        final P6eAdminAuth p6EAdminAuth = P6eAuthTool.getMethodAnnotation(pjp, P6eAdminAuth.class);
        if (p6EAdminAuth != null) {
            // 获取身份凭证
            final String token = P6eAuthTool.getAuthContent(AUTH_HEADER_NAME, AUTH_HEADER_AGREEMENT);
            if (token == null) {
                return P6eResultModel.build(P6eResultConfig.ERROR_AUTH);
            } else {
                final P6eTokenClientResultDto p6eTokenClientResultDto = p6eTokenService.getClient(new P6eTokenClientParamDto(token));
                if (p6eTokenClientResultDto == null || p6eTokenClientResultDto.getError() != null) {
                    return P6eResultModel.build(P6eResultConfig.ERROR_AUTH_NO_EXISTENCE);
                } else {
                    // 判断请求参数中是否含有 P6eAuthModel 对象
                    final Object[] args = pjp.getArgs();
                    if (args != null) {
                        for (Object arg : args) {
                            if (arg instanceof P6eTokenClientResultDto) {
                                break;
                            }
                        }
                    }
                    return pjp.proceed();
                }
            }
        } else {
            return pjp.proceed();
        }
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
