package com.p6e.germ.oauth2.domain.entity;

import com.p6e.germ.common.utils.P6eGeneratorUtil;
import com.p6e.germ.common.utils.P6eJsonUtil;
import com.p6e.germ.common.utils.P6eRsaUtil;
import com.p6e.germ.oauth2.infrastructure.cache.IP6eCacheVoucher;
import com.p6e.germ.oauth2.infrastructure.cache.P6eCache;
import java.security.KeyPair;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lidashuang
 * @version 1.0
 */
public class P6eSecretVoucherEntity {

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
    public P6eSecretVoucherEntity() {
        this.voucher = P6eGeneratorUtil.uuid();
    }

    /**
     * 构造创建
     * @param key 缓存 key
     */
    public P6eSecretVoucherEntity(String key) {
        this.voucher = key;
    }

    /**
     * 创建凭证
     * @return 对象
     */
    public P6eSecretVoucherEntity create() {
        try {
            final KeyPair keyPair = P6eRsaUtil.initKey();
            publicSecretKey = P6eRsaUtil.getPublicKey(keyPair);
            privateSecretKey = P6eRsaUtil.getPrivateKey(keyPair);
            if (publicSecretKey == null || privateSecretKey == null) {
                throw new NullPointerException(this.getClass() + " construction data ==> NullPointerException.");
            }
        } catch (Exception e) {
            throw new NullPointerException(e.getMessage());
        }
        return this;
    }

    /**
     * 查询凭证
     * @return 对象
     */


    public P6eSecretVoucherEntity get() {
        final String content = p6eCacheVoucher.get(voucher);
        if (content == null) {
            throw new NullPointerException(this.getClass() + " construction data ==> NullPointerException.");
        } else {
            final Map<String, String> map = P6eJsonUtil.fromJsonToMap(content, String.class, String.class);
            publicSecretKey = map.get("publicSecretKey");
            privateSecretKey = map.get("privateSecretKey");
            if (publicSecretKey == null || privateSecretKey == null) {
                throw new NullPointerException(this.getClass() + " construction data ==> NullPointerException.");
            }
        }
        return this;
    }

    /**
     * 执行 (解密输出文本内容)
     */
    public String execute(String content) throws Exception {
        return P6eRsaUtil.decrypt(P6eRsaUtil.loadingPrivateKey(privateSecretKey), content);
    }

    /**
     * 缓存
     */
    public P6eSecretVoucherEntity cache() {
        final Map<String, String> map = new HashMap<>(2);
        map.put("publicSecretKey", publicSecretKey);
        map.put("privateSecretKey", privateSecretKey);
        p6eCacheVoucher.set(voucher, P6eJsonUtil.toJson(map));
        return this;
    }

    /**
     * 清除缓存
     */
    public void clean() {
        p6eCacheVoucher.del(voucher);
    }

    /**
     * 获取公钥
     * @return 公钥
     */
    public String getPrivateSecretKey() {
        return privateSecretKey.replaceAll("\n", "");
    }

    /**
     * 获取私钥
     * @return 私钥
     */
    public String getPublicSecretKey() {
        return publicSecretKey.replaceAll("\n", "");
    }

    /**
     * 获取凭证编号
     * @return 凭证编号
     */
    public String getVoucher() {
        return voucher;
    }
}
