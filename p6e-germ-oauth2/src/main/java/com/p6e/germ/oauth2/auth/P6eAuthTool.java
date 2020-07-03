package com.p6e.germ.oauth2.auth;

import com.p6e.germ.oauth2.model.P6eResultConfig;
import com.p6e.germ.oauth2.model.P6eResultModel;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * @author lidashuang
 * @version 1.0
 */
public final class P6eAuthTool {

    /**
     * 通过代理对象获取当前代理执行的方法上面的注解
     * @param pjp 代理对象
     * @param tClass 注解对象
     * @param <T> 注解对象的类型
     * @return 注解对象
     */
    public static <T extends Annotation> T getMethodAnnotation(ProceedingJoinPoint pjp, Class<T> tClass) {
        try {
            // 当前执行的方法对象
            final Signature signature = pjp.getSignature();
            // 当前执行的 method 名称
            final String methodName = signature.getName();
            if (signature instanceof MethodSignature) {
                // 当前执行的 method 对象
                final Method method = pjp.getTarget().getClass()
                        .getMethod(methodName, ((MethodSignature) signature).getParameterTypes());
                // 读取注解对象
                return method.getAnnotation(tClass);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getAuthContent(String headerName, String headerAgreement) {
        // 读取请求头中的参数
        final ServletRequestAttributes servletRequestAttributes
                = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (servletRequestAttributes != null) {
            // 读取请求头部的 TOKEN 数据
            final String authContent = servletRequestAttributes.getRequest().getHeader(headerName);
            // 判断协议是否正确
            if (authContent != null
                    && authContent.toUpperCase().startsWith(headerAgreement)
                    && authContent.length() > headerAgreement.length() + 1) {
                return authContent.substring(headerAgreement.length() + 1);
            }
        }
        return null;
    }

}
