package com.p6e.germ.oauth2.mybatis;

import com.p6e.germ.oauth2.model.base.P6eBaseDb;
import org.springframework.util.DigestUtils;
import java.nio.charset.StandardCharsets;
import java.util.regex.Pattern;

/**
 * 该类为 MyBatis 工具类
 * @author LiDaShuang
 * @version 1.0
 */
public final class MyBatisTool {

    /**
     * 对数据库的 password 字段进行加密操作
     * 采用独特的加密方式---详情见下面
     * 这样做的目的防止（降低）密码被暴力破解
     * @param pwd 待加密的内容
     * @return 加密后的内容
     */
    public static String encryption(String pwd) {
        // 带加密的内容 md5 加密一次
        final String md5Pwd = DigestUtils.md5DigestAsHex(pwd.getBytes(StandardCharsets.UTF_8));
        // 截取 0-8 和 8 以后的内容
        final String md5Pwd1 = md5Pwd.substring(0, 8);
        final String md5Pwd2 = md5Pwd.substring(8);
        // 对 0-8 的内容再 md5 加密一次，8 以后的内容不进行加密
        return DigestUtils.md5DigestAsHex(md5Pwd1.getBytes(StandardCharsets.UTF_8)) + md5Pwd2;
    }

    public static int[] obtainPagingData(P6eBaseDb db) {
        if (db == null) {
            return new int[] { 0, 0 };
        } else {
            final int page = db.getPage();
            final int size = db.getSize();
            return new int[] { (page / size) + 1, size };
        }
    }

    private static final Pattern PATTERN_PHONE
            = Pattern.compile("^[1][3-5,7-8][0-9]{9}$");

    private static final Pattern PATTERN_EMAIL
            = Pattern.compile("^([a-z0-9A-Z]+[-|.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$");

    public static boolean isPhone(String content) {
        return PATTERN_PHONE.matcher(content).matches();
    }

    public static boolean isEmail(String content) {
        return PATTERN_EMAIL.matcher(content).matches();
    }

    public static void main(String[] args) {
        System.out.println(encryption("123456"));
    }

}
