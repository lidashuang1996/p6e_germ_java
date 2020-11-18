package com.p6e.germ.oauth2.infrastructure.utils;

import java.util.UUID;

/**
 * @author lidashuang
 * @version 1.0
 */
public final class GeneratorUtil {

    public static String uuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

}
