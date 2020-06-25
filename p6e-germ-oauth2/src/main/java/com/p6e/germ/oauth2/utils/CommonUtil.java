package com.p6e.germ.oauth2.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * 常见的工具类
 * @author LiDaShuang
 * @version 1.0
 */
public final class CommonUtil {

    public static String generateUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "").toLowerCase();
    }

    public static String nowDate() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
