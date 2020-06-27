package com.p6e.germ.oauth2.service.impl;

import com.p6e.germ.oauth2.cache.IP6eCacheVoucher;
import com.p6e.germ.oauth2.model.dto.P6eVoucherResultDto;
import com.p6e.germ.oauth2.service.P6eVoucherService;
import com.p6e.germ.oauth2.utils.RsaUtil;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author lidashuang
 * @version 1.0
 */
public class P6eVoucherServiceImpl implements P6eVoucherService {

    @Resource
    private IP6eCacheVoucher p6eCacheVoucher;

    @Override
    public P6eVoucherResultDto generate() {
        try {
            // 初始化密钥的方法
            final Map<String, Object> rsaMap = RsaUtil.initRsaKey();
            // 读取公钥私钥的方法
            final String rsaPublicKeyStr = RsaUtil.getRsaPublicKeyStr(rsaMap);
            final String rsaPrivateKeyStr = RsaUtil.getRsaPrivateKeyStr(rsaMap);
            // 下发公钥和保存编号
            final String v = p6eCacheVoucher.set("{\"publicKey\": \""
                    + rsaPublicKeyStr + "\", \"privateKey\": \"" + rsaPrivateKeyStr + "\"}");
            return new P6eVoucherResultDto(v, rsaPublicKeyStr);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
