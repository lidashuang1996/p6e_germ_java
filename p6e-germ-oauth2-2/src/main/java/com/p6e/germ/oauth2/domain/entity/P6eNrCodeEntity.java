package com.p6e.germ.oauth2.domain.entity;

import com.google.gson.reflect.TypeToken;
import com.p6e.germ.common.utils.P6eGeneratorUtil;
import com.p6e.germ.common.utils.P6eJsonUtil;
import com.p6e.germ.oauth2.infrastructure.cache.IP6eCacheNrCode;
import com.p6e.germ.oauth2.infrastructure.cache.P6eCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lidashuang
 * @version 1.0
 */
public class P6eNrCodeEntity {
    private static final Logger LOGGER = LoggerFactory.getLogger(P6eNrCodeEntity.class);

    private static final String IP_NAME = "ip";
    private static final String CODE_NAME = "code";
    private static final String ACCOUNT_NAME = "account";

    /** 注入缓存的服务 */
    private static final IP6eCacheNrCode CACHE_NR_CODE = P6eCache.nrCode;

    private final String key;
    private final String code;
    private final String account;
    private final String ip;

    public static P6eNrCodeEntity get(String key) {
        try {
            if (key != null) {
                final String content = CACHE_NR_CODE.get(key);
                if (content != null) {
                    final Map<String, String> value =
                            P6eJsonUtil.fromJson(content, new TypeToken<Map<String,String>>(){}.getType());
                    if (value != null && value.size() > 0) {
                        return new P6eNrCodeEntity(key, value.get(CODE_NAME), value.get(ACCOUNT_NAME), value.get(IP_NAME));
                    }
                }
            }
            throw new Exception();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public static P6eNrCodeEntity create(String account, String ip) {
        final String key = P6eGeneratorUtil.uuid();
        final String code = P6eGeneratorUtil.random();
        LOGGER.info("给账号 " + account + " 发送的验证码为: " + code);
        return new P6eNrCodeEntity(key, code, account, ip);
    }

    private P6eNrCodeEntity(String key, String code, String account, String ip) {
        this.key = key;
        this.code = code;
        this.account = account;
        this.ip = ip;
    }

    public P6eNrCodeEntity push() {
        return this;
    }

    public P6eNrCodeEntity cache() {
        final Map<String, String> value = new HashMap<>(2);
        value.put(IP_NAME, ip);
        value.put(CODE_NAME, code);
        value.put(ACCOUNT_NAME, account);
        CACHE_NR_CODE.set(key, P6eJsonUtil.toJson(value));
        return this;
    }

    public String getKey() {
        return key;
    }

    public boolean verification(String account, String code) {
        return this.code.equals(code) && this.account.equals(account);
    }

    public P6eNrCodeEntity clean() {
        return this;
    }
}
