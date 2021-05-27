package com.p6e.germ.common.utils;

import java.util.regex.Pattern;

/**
 * @author lidashuang
 * @version 1.0
 */
public final class P6eFormatUtil {

    public static boolean emailVerification(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }
        return Pattern.matches("^(\\w+([-.][A-Za-z0-9]+)*){3,18}@\\w+([-.][A-Za-z0-9]+)*\\.\\w+([-.][A-Za-z0-9]+)*$", email);
    }

    public static boolean phoneVerification(String phone) {
        if (phone == null || phone.isEmpty()) {
            return false;
        }
        return Pattern.matches("^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(17[0135678])|(18[0-9])|(19[8|9])|(16[6]))\\d{8}$", phone);
    }

}
