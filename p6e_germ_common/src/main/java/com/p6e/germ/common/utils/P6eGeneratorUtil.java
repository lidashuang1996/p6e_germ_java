package com.p6e.germ.common.utils;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 生成序号的帮助类
 * @author lidashuang
 * @version 1.0
 */
public final class P6eGeneratorUtil {

    private static final int MIN_RANDOM_LENGTH = 1;
    private static final int MAX_RANDOM_LENGTH = 19;
    private static final ThreadLocalRandom THREAD_LOCAL_RANDOM = ThreadLocalRandom.current();
    /**
     * 生成 UUID 数据
     * @return UUID
     */
    public static String uuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static String random() {
        return random(6);
    }

    public static String random(int len) {
        if (len < MIN_RANDOM_LENGTH || len > MAX_RANDOM_LENGTH) {
            return "";
        } else {
            long l = 10;
            final StringBuilder r = new StringBuilder();
            for (int i = 0; i < len; i++) {
                l *= 10;
                r.append("0");
            }
            r.append(THREAD_LOCAL_RANDOM.nextLong(0, l));
            return r.substring(r.length() - len);
        }
    }

}
