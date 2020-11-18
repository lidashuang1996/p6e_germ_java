package com.p6e.germ.oauth2.infrastructure.utils;

import org.springframework.context.ApplicationContext;

/**
 * ApplicationContext
 * @author lidashuang
 * @version 1.0
 */
public final class SpringUtil {

    /** 全局 ApplicationContext 对象 */
    private static ApplicationContext APPLICATION;

    /**
     * 初始化 ApplicationContext 对象
     * @param context ApplicationContext 对象
     */
    public static void init(final ApplicationContext context) {
        APPLICATION = context;
    }

    /**
     * 获取 Spring 管理的 Java Bean 对象
     * @param tClass Java Bean 的 Class
     * @param <T> Java Bean 的 Class 的类型
     * @return 获取的 JAVA Bean 对象
     */
    public static <T> T getBean(final Class<T> tClass) {
        if (APPLICATION == null) {
            throw new NullPointerException(SpringUtil.class + " getBean() ==> APPLICATION param null.");
        } else {
            return APPLICATION.getBean(tClass);
        }
    }

}
