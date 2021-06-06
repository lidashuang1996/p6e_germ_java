package com.p6e.germ.oauth2.domain.entity;

import com.p6e.germ.common.utils.P6eGeneratorUtil;
import com.p6e.germ.common.utils.P6eJsonUtil;
import com.p6e.germ.common.utils.P6eRsaUtil;
import com.p6e.germ.oauth2.domain.keyvalue.P6eSecretVoucherKeyValue;
import com.p6e.germ.oauth2.infrastructure.cache.IP6eCacheSecretVoucher;
import com.p6e.germ.oauth2.infrastructure.cache.P6eCache;
import java.security.KeyPair;

/**
 * @author lidashuang
 * @version 1.0
 */
public class P6eSecretVoucherEntity {

    /** KEY */
    private final String key;

    /** 公钥 // 私钥 */
    private final P6eSecretVoucherKeyValue.Content value;

    /** 缓存服务对象 */
    private static final IP6eCacheSecretVoucher CACHE_VOUCHER = P6eCache.voucher;

    public static P6eSecretVoucherEntity get(String key) {
        try {
            if (key != null) {
                final String content = CACHE_VOUCHER.get(key);
                if (content != null) {
                    final P6eSecretVoucherKeyValue.Content value =
                            P6eJsonUtil.fromJson(content, P6eSecretVoucherKeyValue.Content.class);
                    if (value != null) {
                        return new P6eSecretVoucherEntity(key, value);
                    }
                }
            }
            throw new Exception();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static P6eSecretVoucherEntity create() {
        try {
            final String key = P6eGeneratorUtil.uuid();
            final KeyPair keyPair = P6eRsaUtil.initKey();
            final String publicSecretKey = P6eRsaUtil.getPublicKey(keyPair);
            final String privateSecretKey = P6eRsaUtil.getPrivateKey(keyPair);
            return new P6eSecretVoucherEntity(key,
                    new P6eSecretVoucherKeyValue.Content(publicSecretKey, privateSecretKey));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 构造创建
     */
    private P6eSecretVoucherEntity(String key, P6eSecretVoucherKeyValue.Content value) {
        this.key = key;
        this.value = value;
    }

    /**
     * 执行 (解密输出文本内容)
     */
    public String execute(String content) throws Exception {
        return P6eRsaUtil.decrypt(P6eRsaUtil.loadingPrivateKey(value.getPrivateSecretKey()), content);
    }

    /**
     * 缓存
     */
    public P6eSecretVoucherEntity cache() {
        CACHE_VOUCHER.set(key, P6eJsonUtil.toJson(value));
        return this;
    }

    /**
     * 清除缓存
     */
    public void clean() {
        CACHE_VOUCHER.del(key);
    }

    /**
     * 获取公钥
     * @return 公钥
     */
    public String getPrivateSecretKey() {
        return value.getPrivateSecretKey().replaceAll("\n", "");
    }

    /**
     * 获取私钥
     * @return 私钥
     */
    public String getPublicSecretKey() {
        return value.getPublicSecretKey().replaceAll("\n", "");
    }

    /**
     * 获取凭证编号
     * @return 凭证编号
     */
    public String getKey() {
        return key;
    }
}
