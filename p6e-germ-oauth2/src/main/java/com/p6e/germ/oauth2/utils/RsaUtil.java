package com.p6e.germ.oauth2.utils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * RSA 加密的帮助类
 * @author lidashuang
 * @version 1.0
 */
public class RsaUtil {

    /** RSA 加密方式 */
    private static final String RSA_KEY_ALGORITHM = "RSA";
    private static final String RSA_SIGNATURE_ALGORITHM = "MD5withRSA";
    /** RSA 公有的密钥 */
    public static final String RSA_PUBLIC_KEY = "RSAPublicKey";
    /** RSA 私有的密钥 */
    public static final String RSA_PRIVATE_KEY = "RSAPrivateKey";
    /** RSA最大加密明文大小 */
    private static final int RSA_MAX_ENCRYPT_BLOCK = 117;
    /** RSA最大解密密文大小 */
    private static final int RSA_MAX_DECRYPT_BLOCK = 128;

    /**
     * 获得公钥字符串
     * @param keyMap 公钥私钥的字典
     * @return 公钥字符串
     */
    private static String getRsaPublicKeyStr(final Map<String, Object> keyMap) {
        // 获得map中的公钥对象 转为key对象
        final Key key = (Key) keyMap.get(RSA_PUBLIC_KEY);
        // 编码返回字符串
        return new String((new BASE64Encoder()).encodeBuffer(key.getEncoded()).getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 获得私钥字符串
     * @param keyMap 公钥私钥的字典
     * @return 私钥字符串
     */
    private static String getRsaPrivateKeyStr(final Map<String, Object> keyMap) {
        // 获得map中的私钥对象 转为key对象
        final Key key = (Key) keyMap.get(RSA_PRIVATE_KEY);
        // 编码返回字符串
        return new String((new BASE64Encoder()).encodeBuffer(key.getEncoded()).getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 获取公钥
     * @param key 公钥
     * @return 公钥对象
     * @throws Exception 异常
     */
    private static PublicKey getRsaPublicKey(final String key) throws Exception {
        final byte[] keyBytes = (new BASE64Decoder()).decodeBuffer(key);
        final X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        final KeyFactory keyFactory = KeyFactory.getInstance(RSA_KEY_ALGORITHM);
        return keyFactory.generatePublic(keySpec);
    }

    /**
     * 获取私钥
     * @param key 公钥
     * @return 私钥对象
     * @throws Exception 异常
     */
    private static PrivateKey getRsaPrivateKey(final String key) throws Exception {
        final byte[] keyBytes = (new BASE64Decoder()).decodeBuffer(key);
        final PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        final KeyFactory keyFactory = KeyFactory.getInstance(RSA_KEY_ALGORITHM);
        return keyFactory.generatePrivate(keySpec);
    }

    /**
     * RSA 公私钥加密
     * @param plainTextStr 加密的内容
     * @param publicKeyStr 公钥的字符串
     * @return 加密后的内容
     * @throws Exception 异常
     */
    public static String rsaEncrypt(final String plainTextStr, final String publicKeyStr) throws Exception {
        final byte[] plainText = plainTextStr.getBytes(StandardCharsets.UTF_8);
        final PublicKey publicKey = getRsaPublicKey(publicKeyStr);
        final Cipher cipher = Cipher.getInstance(RSA_KEY_ALGORITHM);
        final int inputLen = plainText.length;
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return (new BASE64Encoder()).encodeBuffer(subsection(inputLen, cipher, plainText, RSA_MAX_ENCRYPT_BLOCK));
    }

    /**
     * RSA 公私钥加密
     * @param encryptTextHex 解密的内容
     * @param privateKeyStr 私钥的字符串
     * @return 解密后的内容
     * @throws Exception 异常
     */
    public static String rsaDecrypt(final String encryptTextHex, final String privateKeyStr) throws Exception {
        final byte[] encryptText = (new BASE64Decoder()).decodeBuffer(encryptTextHex);
        final PrivateKey privateKey = getRsaPrivateKey(privateKeyStr);
        final Cipher cipher = Cipher.getInstance(RSA_KEY_ALGORITHM);
        final int inputLen = encryptText.length;
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return new String(subsection(inputLen, cipher, encryptText, RSA_MAX_DECRYPT_BLOCK));
    }

    /**
     * 分段处理 加密 / 解密
     * @param inputLen 内容长度
     * @param cipher Cipher 对象
     * @param content 待处理的内容
     * @param max 最大的长度
     * @return 加密 / 解密 的内容
     * @throws Exception 异常
     */
    private static byte[] subsection(int inputLen, Cipher cipher, byte[] content, int max) throws Exception {
        ByteArrayOutputStream out = null;
        try {
            int offSet = 0;
            byte[] cache;
            int i = 0;
            out = new ByteArrayOutputStream();
            // 对数据分段解密
            while(inputLen - offSet > 0){
                if(inputLen - offSet > max){
                    cache = cipher.doFinal(content, offSet, max);
                } else{
                    cache = cipher.doFinal(content, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * max;
            }
            return out.toByteArray();
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    /**
     * 初始化的密钥
     * @return 公钥私钥的合集
     * @throws Exception 异常
     */
    public static Map<String, Object> initRsaKey() throws Exception {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(RSA_KEY_ALGORITHM);
        keyPairGen.initialize(1024);
        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPublicKey publicKey = (RSAPublicKey)keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey)keyPair.getPrivate();
        Map<String, Object> keyMap = new HashMap<>(2);
        keyMap.put(RSA_PUBLIC_KEY, publicKey);
        keyMap.put(RSA_PRIVATE_KEY, privateKey);
        return keyMap;
    }

    /**
     * 测试的方法
     */
    public static void test() {
        Map<String, Object> keyMap;
        String cipherText;
        String input = "1234567890中文测试AbcD";
        try {
            keyMap = initRsaKey();
            String publicKey = getRsaPublicKeyStr(keyMap);
            System.out.println("公钥------------------");
            System.out.println(publicKey);
            System.out.println("length: " + publicKey.length());

            String privateKey = getRsaPrivateKeyStr(keyMap);
            System.out.println("私钥------------------");
            System.out.println(privateKey);
            System.out.println("length: " + privateKey.length());

            System.out.println("测试可行性-------------------");
            System.out.println("明文=======" + input);
            System.out.println("length: " + input.length());

            cipherText = rsaEncrypt(input, publicKey);
            //加密后的东西 
            System.out.println("密文=======" + cipherText);
            System.out.println("length: " + cipherText.length());

            //开始解密 
            String plainText = rsaDecrypt(cipherText, privateKey);
            System.out.println("解密后明文===== " + plainText);
            System.out.println("验证签名-----------");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
