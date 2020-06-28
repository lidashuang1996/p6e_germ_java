package com.p6e.germ.oauth2.service;

import com.p6e.germ.oauth2.model.dto.P6eVoucherParamDto;
import com.p6e.germ.oauth2.model.dto.P6eVoucherResultDto;

/**
 * 凭证的服务
 * 下发 AES 加密的公钥
 * @author lidashuang
 * @version 1.0
 */
public interface P6eVoucherService {

    /**
     * 生成密码公钥令牌的方法
     * @return 公钥字符串
     */
    public P6eVoucherResultDto generate();

    /**
     * 解密密码的方法
     * @param param 解密前的参数
     * @return 解密后的内容
     */
    public P6eVoucherResultDto verify(P6eVoucherParamDto param);

}
