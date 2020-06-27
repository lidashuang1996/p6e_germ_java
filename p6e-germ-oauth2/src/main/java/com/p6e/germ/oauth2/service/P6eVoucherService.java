package com.p6e.germ.oauth2.service;

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
    public String generate();

}
