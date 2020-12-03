package com.p6e.germ.oauth2.infrastructure.utils;

import com.p6e.germ.oauth2.application.P6eApplication;
import com.p6e.germ.oauth2.infrastructure.cache.P6eCache;
import org.springframework.context.ApplicationContext;

/**
 * ApplicationContext
 * @author lidashuang
 * @version 1.0
 */
public final class P6eSpringUtil {

    /** 全局 ApplicationContext 对象 */
    private static ApplicationContext APPLICATION;

    /**
     * 初始化 ApplicationContext 对象
     * @param context ApplicationContext 对象
     */
    public static void init(final ApplicationContext context) {
        APPLICATION = context;
        P6eCache.init();
        P6eApplication.init();
    }

    /**
     * 获取 Spring 管理的 Java Bean 对象
     * @param tClass Java Bean 的 Class
     * @param <T> Java Bean 的 Class 的类型
     * @return 获取的 JAVA Bean 对象
     */
    public static <T> T getBean(final Class<T> tClass) {
        if (APPLICATION == null) {
            throw new NullPointerException(P6eSpringUtil.class + " getBean() ==> APPLICATION param null.");
        } else {
            try {
                return APPLICATION.getBean(tClass);
            } catch (Exception e) {
                return null;
            }
        }
    }

    public static <T> T getBean(final Class<T> tClass, T def) {
        if (APPLICATION == null) {
            return def;
        } else {
            try {
                return APPLICATION.getBean(tClass);
            } catch (Exception e) {
                return def;
            }
        }
    }

}
