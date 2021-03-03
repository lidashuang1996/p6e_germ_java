package com.p6e.germ.common.utils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * RSA 加密的帮助类
 * @author lidashuang
 * @version 1.0
 */
public final class P6eRsaUtil {

    /** RSA 密钥长度 */
    private static final int RSA_KEY_SIZE = 2048;
    /** RSA 加密方式 */
    private static final String RSA_ALGORITHM = "RSA";
    /** RSA最大解密密文大小 */
    private static final int RSA_MAX_DECRYPT_BLOCK = RSA_KEY_SIZE / 1024 * 128;
    /** RSA最大加密明文大小 */
    private static final int RSA_MAX_ENCRYPT_BLOCK = RSA_MAX_DECRYPT_BLOCK - 11;
    /** BASE64 加密解密对象 */
    private static final BASE64Decoder BASE_64_DECODER = new BASE64Decoder();
    private static final BASE64Encoder BASE_64_ENCODER = new BASE64Encoder();

    /**
     * 初始化公私密钥
     * @return 公私密钥对象
     * @throws Exception 出现的异常
     */
    public static KeyPair initKey() throws Exception {
        final KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(RSA_ALGORITHM);
        keyPairGenerator.initialize(RSA_KEY_SIZE);
        return keyPairGenerator.genKeyPair();
    }

    /**
     * 加密
     * @param publicKey 公钥
     * @param message 消息
     * @return 加密内容
     * @throws Exception 出现的异常
     */
    public static String encrypt(PublicKey publicKey, String message) throws Exception {
        final Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return BASE_64_ENCODER.encodeBuffer(subsection(cipher, message.getBytes(StandardCharsets.UTF_8), RSA_MAX_ENCRYPT_BLOCK));
    }

    /**
     * 解密
     * @param privateKey 私钥
     * @param message 消息
     * @return 解密内容
     * @throws Exception 出现的异常
     */
    public static String decrypt(PrivateKey privateKey, String message) throws Exception {
        final Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return new String(subsection(cipher, BASE_64_DECODER.decodeBuffer(message), RSA_MAX_DECRYPT_BLOCK));
    }

    /**
     * 读取公钥的数据
     * @param keyPair 公私密钥对象
     * @return 公钥字符串
     */
    public static String getPublicKey(KeyPair keyPair) {
        return BASE_64_ENCODER.encodeBuffer(keyPair.getPublic().getEncoded());
    }

    /**
     * 读取私钥的数据
     * @param keyPair 公私密钥对象
     * @return 私钥字符串
     */
    public static String getPrivateKey(KeyPair keyPair) {
        return BASE_64_ENCODER.encodeBuffer(keyPair.getPrivate().getEncoded());
    }

    /**
     * 加载的公钥的数据
     * @param publicKey 公钥内容
     * @return 加载的公钥对象
     * @throws Exception 出现的异常
     */
    public static PublicKey loadingPublicKey(String publicKey) throws Exception {
        return KeyFactory.getInstance(RSA_ALGORITHM).generatePublic(
                new X509EncodedKeySpec(BASE_64_DECODER.decodeBuffer(publicKey)));
    }

    /**
     * 加载的私钥的数据
     * @param privateKey 私钥内容
     * @return 加载的私钥对象
     * @throws Exception 出现的异常
     */
    public static PrivateKey loadingPrivateKey(String privateKey) throws Exception {
        return KeyFactory.getInstance(RSA_ALGORITHM).generatePrivate(
                new PKCS8EncodedKeySpec(BASE_64_DECODER.decodeBuffer(privateKey)));
    }

    /**
     * 分段处理 加密 / 解密
     * @param cipher Cipher 对象
     * @param content 待处理的内容
     * @param max 最大的长度
     * @return 加密 / 解密 的内容
     * @throws Exception 异常
     */
    private static byte[] subsection(Cipher cipher, byte[] content, int max) throws Exception {
        final int contentSize = content.length;
        ByteArrayOutputStream out = null;
        try {
            int offSet = 0;
            byte[] cache;
            int i = 0;
            out = new ByteArrayOutputStream();
            while (contentSize - offSet > 0) {
                if (contentSize - offSet > max) {
                    out.write(cipher.doFinal(content, offSet, max));
                } else{
                    out.write(cipher.doFinal(content, offSet, contentSize - offSet));
                }
                offSet = (++i) * max;
            }
            return out.toByteArray();
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }
}
