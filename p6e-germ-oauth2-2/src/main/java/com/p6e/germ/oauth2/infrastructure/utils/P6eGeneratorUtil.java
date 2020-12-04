package com.p6e.germ.oauth2.infrastructure.utils;

import java.util.UUID;

/**
 * 生成序号的帮助类
 * @author lidashuang
 * @version 1.0
 */
public final class P6eGeneratorUtil {

    /**
     * 生成 UUID 数据
     * @return UUID
     */
    public static String uuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

}
