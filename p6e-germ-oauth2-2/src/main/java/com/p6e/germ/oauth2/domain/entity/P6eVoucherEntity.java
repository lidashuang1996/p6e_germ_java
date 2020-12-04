package com.p6e.germ.oauth2.domain.entity;

import com.p6e.germ.oauth2.infrastructure.cache.IP6eCacheVoucher;
import com.p6e.germ.oauth2.infrastructure.cache.P6eCache;
import com.p6e.germ.oauth2.infrastructure.utils.GeneratorUtil;
import com.p6e.germ.oauth2.infrastructure.utils.JsonUtil;
import com.p6e.germ.oauth2.infrastructure.utils.RsaUtil;
import java.security.KeyPair;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lidashuang
 * @version 1.0
 */
public class P6eVoucherEntity {

    /** KEY */
    private final String voucher;

    /** 公钥 */
    private String publicSecretKey;

    /** 私钥 */
    private String privateSecretKey;

    /** 缓存服务对象 */
    private final IP6eCacheVoucher p6eCacheVoucher = P6eCache.voucher;

    /**
     * 构造创建
     */
    public P6eVoucherEntity() {
        this.voucher = GeneratorUtil.uuid();
    }

    /**
     * 构造创建
     * @param key 缓存 key
     */
    public P6eVoucherEntity(String key) {
        this.voucher = key;
    }

    public P6eVoucherEntity create() {
        try {
            final KeyPair keyPair = RsaUtil.initKey();
            publicSecretKey = RsaUtil.getPublicKey(keyPair);
            privateSecretKey = RsaUtil.getPrivateKey(keyPair);
            if (publicSecretKey == null || privateSecretKey == null) {
                throw new NullPointerException(this.getClass() + " construction fetch key ==> NullPointerException.");
            }
        } catch (Exception e) {
            throw new NullPointerException(e.getMessage());
        }
        return this;
    }

    public P6eVoucherEntity get() {
        final String content = p6eCacheVoucher.get(voucher);
        if (content == null) {
            throw new NullPointerException(this.getClass() + " construction fetch data ==> NullPointerException.");
        } else {
            final Map<String, String> map = JsonUtil.fromJsonToMap(content, String.class, String.class);
            publicSecretKey = map.get("publicSecretKey");
            privateSecretKey = map.get("privateSecretKey");
            if (publicSecretKey == null || privateSecretKey == null) {
                throw new NullPointerException(this.getClass() + " construction fetch key ==> NullPointerException.");
            }
        }
        return this;
    }

    /**
     * 执行 (解密输出文本内容)
     */
    public String execute(String content) throws Exception {
        return RsaUtil.decrypt(RsaUtil.loadingPrivateKey(privateSecretKey), content);
    }

    /**
     * 缓存
     */
    public P6eVoucherEntity cache() {
        final Map<String, String> map = new HashMap<>(2);
        map.put("publicSecretKey", publicSecretKey);
        map.put("privateSecretKey", privateSecretKey);
        p6eCacheVoucher.set(voucher, JsonUtil.toJson(map));
        return this;
    }

    /**
     * 清除缓存
     */
    public void clean() {
        p6eCacheVoucher.del(voucher);
    }

    public String getPrivateSecretKey() {
        return privateSecretKey.replaceAll("\n", "");
    }

    public String getPublicSecretKey() {
        return publicSecretKey.replaceAll("\n", "");
    }

    public String getVoucher() {
        return voucher;
    }
}
