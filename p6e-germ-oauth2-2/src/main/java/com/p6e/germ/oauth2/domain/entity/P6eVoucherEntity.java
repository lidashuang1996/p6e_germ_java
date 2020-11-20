package com.p6e.germ.oauth2.domain.entity;

import com.p6e.germ.oauth2.infrastructure.cache.IP6eCacheVoucher;
import com.p6e.germ.oauth2.infrastructure.exception.AnalysisException;
import com.p6e.germ.oauth2.infrastructure.utils.GeneratorUtil;
import com.p6e.germ.oauth2.infrastructure.utils.JsonUtil;
import com.p6e.germ.oauth2.infrastructure.utils.RsaUtil;
import com.p6e.germ.oauth2.infrastructure.utils.SpringUtil;
import lombok.Getter;

import java.io.Serializable;
import java.security.KeyPair;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lidashuang
 * @version 1.0
 */
public class P6eVoucherEntity implements Serializable {

    /** 唯一标记 */
    private final String uniqueId;

    /** KEY */
    @Getter
    private final String key;

    /** 公钥 */
    @Getter
    private final String publicSecretKey;

    /** 私钥 */
    @Getter
    private final String privateSecretKey;

    /** 缓存服务对象 */
    private final IP6eCacheVoucher p6eCacheVoucher = SpringUtil.getBean(IP6eCacheVoucher.class);

    /**
     * 读取方式获取对象
     * @param key 缓存 key
     * @return P6eVoucherEntity 对象
     */
    public static P6eVoucherEntity fetch(String key) {
        return new P6eVoucherEntity(key, true);
    }

    /**
     * 创建方式获取对象
     * @param key 缓存 key
     * @return P6eVoucherEntity 对象
     */
    public static P6eVoucherEntity create(String key) {
        return new P6eVoucherEntity(key, false);
    }

    /**
     * 构造创建
     * @param key 缓存 key
     * @param isFetch 是否采用读取的方式
     */
    private P6eVoucherEntity(String key, boolean isFetch) {
        try {
            this.key = key;
            if (isFetch) {
                final String voucher = p6eCacheVoucher.get(key);
                if (voucher == null) {
                    throw new NullPointerException(this.getClass() + " construction fetch data ==> NullPointerException.");
                } else {
                    final Map<String, String> map = JsonUtil.fromJsonToMap(voucher, String.class, String.class);
                    publicSecretKey = map.get("publicSecretKey");
                    privateSecretKey = map.get("privateSecretKey");
                    if (publicSecretKey == null || privateSecretKey == null) {
                        throw new NullPointerException(this.getClass() + " construction fetch key ==> NullPointerException.");
                    }
                }
            } else {
                final KeyPair keyPair = RsaUtil.initKey();
                publicSecretKey = RsaUtil.getPublicKey(keyPair);
                privateSecretKey = RsaUtil.getPrivateKey(keyPair);
                if (publicSecretKey == null || privateSecretKey == null) {
                    throw new NullPointerException(this.getClass() + " construction fetch key ==> NullPointerException.");
                }
                this.cache();
            }
            // 生成
            uniqueId = GeneratorUtil.uuid();
        } catch (Exception e) {
            throw new AnalysisException(this.getClass() + " construction ==> AnalysisException.");
        }
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
    public void cache() {
        final Map<String, String> map = new HashMap<>(2);
        map.put("publicSecretKey", publicSecretKey);
        map.put("privateSecretKey", privateSecretKey);
        p6eCacheVoucher.set(key, JsonUtil.toJson(map));
    }

    /**
     * 清除缓存
     */
    public void clean() {
        p6eCacheVoucher.del(key);
    }

}
