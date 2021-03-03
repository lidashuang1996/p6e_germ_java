package com.p6e.germ.common.utils;

import org.springframework.context.ApplicationContext;

/**
 * 该类为 Spring Boot 初始化类
 * 程序的执行一定要初始化的一个类
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
        // 写入的 ApplicationContext 对象
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
            throw new NullPointerException(P6eSpringUtil.class + " getBean() ==> APPLICATION param null.");
        } else {
            return APPLICATION.getBean(tClass);
        }
    }

    /**
     * 获取 Spring 管理的 Java Bean 对象，如果没有就用默认的参数替换
     * @param tClass Java Bean 的 Class
     * @param def 默认的 Java Bean 的实现
     * @param <T> Java Bean 的 Class 的类型
     * @return 获取的 JAVA Bean 对象
     */
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
