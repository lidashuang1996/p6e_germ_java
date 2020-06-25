package com.p6e.germ.oauth2.mybatis;

import org.springframework.util.DigestUtils;
import java.nio.charset.StandardCharsets;

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

    public static void main(String[] args) {
        System.out.println(encryption("123456"));
    }

}
