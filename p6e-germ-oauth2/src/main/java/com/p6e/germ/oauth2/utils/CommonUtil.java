package com.p6e.germ.oauth2.utils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * 常见的工具类
 * @author LiDaShuang
 * @version 1.0
 */
public final class CommonUtil {

    /**
     * 生成 uuid 的方法
     * @return uuid 结果
     */
    public static String generateUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "").toLowerCase();
    }

    /**
     * 获取当前的时间
     * @return 当前时间的字符串
     */
    public static String nowDate() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    /** HEX 16进制的数据 */
    private static final char[]
            HEX_CHAR = {'0', '1', '2', '3', '4', '5','6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    /**
     * 将byte[]转换为16进制字符串
     * @param bytes 待转换的数组
     * @return 转换后的16进制字符串
     */
    public static String bytesToHex(byte[] bytes) {
        // 一个byte为8位，可用两个十六进制位标识
        char[] buf = new char[bytes.length * 2];
        int a;
        int index = 0;
        // 使用除与取余进行转换
        for (byte b : bytes) {
            if (b < 0) {
                a = 256 + b;
            } else {
                a = b;
            }
            buf[index++] = HEX_CHAR[a / 16];
            buf[index++] = HEX_CHAR[a % 16];
        }
        return new String(buf);
    }

    /**
     * 将16进制字符串转换为byte[]
     * @param str 16 进制的字符串
     * @return 结果 byte[]
     */
    public static byte[] hexToBytes(String str) {
        if (str == null || "".equals(str.trim())) {
            return new byte[0];
        }
        byte[] bytes = new byte[str.length() / 2];
        for (int i = 0; i < str.length() / 2; i++) {
            String subStr = str.substring(i * 2, i * 2 + 2);
            bytes[i] = (byte) Integer.parseInt(subStr, 16);
        }
        return bytes;
    }

    /**
     * 解码返回 byte
     * @param key 解码内容
     * @return 解码后的内容
     * @throws Exception 异常
     */
    public static byte[] decryptBASE64(final String key) throws Exception {
        return (new BASE64Decoder()).decodeBuffer(key);
    }

    /**
     * 编码返回字符串
     * @param key 编码内容
     * @return 编码后的内容
     */
    public static String encryptBASE64(final byte[] key) {
        return (new BASE64Encoder()).encodeBuffer(key);
    }
}