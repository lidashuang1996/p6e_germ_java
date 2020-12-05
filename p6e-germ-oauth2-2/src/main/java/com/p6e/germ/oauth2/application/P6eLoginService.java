package com.p6e.germ.oauth2.application;

import com.p6e.germ.oauth2.model.dto.P6eDefaultLoginDto;
import com.p6e.germ.oauth2.model.dto.P6eLoginDto;
import com.p6e.germ.oauth2.model.dto.P6eVerificationLoginDto;
import com.p6e.germ.oauth2.model.dto.P6eVoucherDto;

/**
 * 登录服务
 * @author lidashuang
 * @version 1.0
 */
public interface P6eLoginService {

    /**
     * 获取凭证信息
     * 用于用户的登录密码的加密
     * @return 凭证对象
     */
    public P6eVoucherDto defVoucher();

    /**
     * 验证的用户的 ACCESS TOKEN 数据
     * @param param 验证对象
     * @return 结果对象
     */
    public P6eLoginDto verification(P6eVerificationLoginDto param);

    /**
     * 默认登陆（账号密码登录）
     * @param param 账号密码登录对象
     * @return 登录结果对象
     */
    public P6eLoginDto defaultLogin(P6eDefaultLoginDto param);

    /**
     * QQ 登录
     * @return 登录结果对象
     */
    public P6eLoginDto qqLogin();

    /**
     * 微信登录
     * @return 登录结果对象
     */
    public P6eLoginDto weChatLogin();

    /**
     * 扫码登录
     * @return 登录结果对象
     */
    public P6eLoginDto scanCodeLogin();
}
