package com.p6e.germ.oauth2.service.impl;

import com.google.gson.reflect.TypeToken;
import com.p6e.germ.oauth2.cache.IP6eCacheVoucher;
import com.p6e.germ.oauth2.model.dto.P6eVoucherParamDto;
import com.p6e.germ.oauth2.model.dto.P6eVoucherResultDto;
import com.p6e.germ.oauth2.service.P6eVoucherService;
import com.p6e.germ.oauth2.utils.CommonUtil;
import com.p6e.germ.oauth2.utils.GsonUtil;
import com.p6e.germ.oauth2.utils.RsaUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 凭证生成和验证服务
 * @author lidashuang
 * @version 1.0
 */
@Service
public class P6eVoucherServiceImpl implements P6eVoucherService {

    /** 注入缓存的服务 */
    @Resource
    private IP6eCacheVoucher p6eCacheVoucher;

    @Override
    public P6eVoucherResultDto generate() {
        try {
            // voucher
            final String voucher = CommonUtil.generateUUID();
            // 初始化密钥的方法
            final Map<String, Object> rsaMap = RsaUtil.initRsaKey();
            // 读取公钥私钥的方法
            final String rsaPublicKeyStr = RsaUtil.getRsaPublicKeyStr(rsaMap).replaceAll("\n", "");
            final String rsaPrivateKeyStr = RsaUtil.getRsaPrivateKeyStr(rsaMap).replaceAll("\n", "");
            // 下发公钥和保存编号
            p6eCacheVoucher.set(voucher, "{\"publicKey\": \""
                    + rsaPublicKeyStr + "\", \"privateKey\": \"" + rsaPrivateKeyStr + "\"}");
            // 处理一下数据并返回对象
            return new P6eVoucherResultDto(voucher, rsaPublicKeyStr);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public P6eVoucherResultDto verify(final P6eVoucherParamDto param) {
        try {
            // 读取缓存数据
            final String content = param.getContent();
            final String voucherContent = p6eCacheVoucher.get(param.getVoucher());
            if (voucherContent != null && content != null) {
                final Map<String, String> map
                        = GsonUtil.fromJson(voucherContent, new TypeToken<Map<String, String>>() {}.getType());
                // 缓存数据判断
                if (map != null) {
                    final String privateKey = map.get("privateKey");
                    // 解密数据并返回
                    return new P6eVoucherResultDto(RsaUtil.rsaDecrypt(content, privateKey));
                }
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
