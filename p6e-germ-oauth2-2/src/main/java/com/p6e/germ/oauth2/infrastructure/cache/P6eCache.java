package com.p6e.germ.oauth2.infrastructure.cache;

import com.p6e.germ.common.utils.P6eSpringUtil;

/**
 * @author lidashuang
 * @version 1.0
 */
public final class P6eCache {
    /** CODE 缓存 */
    public static IP6eCacheQrCode qrCode;
    /** 认证缓存 */
    public static IP6eCacheAuth auth;
    /** 授权记号缓存 */
    public static IP6eCacheMark mark;
    /** 认证缓存 */
    public static IP6eCacheToken token;
    /** 客户端缓存 */
    public static IP6eCacheClient client;
    /** 凭证缓存 */
    public static IP6eCacheSecretVoucher voucher;

    public static IP6eCacheNrCode nrCode;

    public static IP6eCacheOtherState otherState;

    /**
     * 初始化操作
     */
    public static void init() {
        otherState = P6eSpringUtil.getBean(IP6eCacheOtherState.class, new P6eCacheRedisOtherState());
        // CODE 缓存
        qrCode = P6eSpringUtil.getBean(IP6eCacheQrCode.class, new P6eCacheRedisQrCode());
        nrCode = P6eSpringUtil.getBean(IP6eCacheNrCode.class, new P6eCacheRedisNrCode());
        // 认证缓存
        auth = P6eSpringUtil.getBean(IP6eCacheAuth.class, new P6eCacheRedisAuth());
        // 授权记号缓存
        mark = P6eSpringUtil.getBean(IP6eCacheMark.class, new P6eCacheRedisMark());
        // 认证缓存
        token = P6eSpringUtil.getBean(IP6eCacheToken.class, new P6eCacheRedisToken());
        // 第方三方授权 state 数据缓存
        // 客户端的缓存
        client = P6eSpringUtil.getBean(IP6eCacheClient.class, new P6eCacheRedisClient());
        // 凭证缓存
        voucher = P6eSpringUtil.getBean(IP6eCacheSecretVoucher.class, new P6eCacheRedisSecretVoucher());
    }
}
