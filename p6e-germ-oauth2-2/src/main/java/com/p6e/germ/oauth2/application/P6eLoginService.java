package com.p6e.germ.oauth2.application;

import com.p6e.germ.oauth2.model.dto.*;

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
     * 生成扫码登录的基础信息
     * @param mark 客户端的基本信息
     * @return 生成扫码登录凭证信息对象
     */
    public P6eGenerateCodeLoginDto generateCode(String mark);

    /**
     * 扫码登录
     * @param param 扫描登录凭证和认证信息
     * @return 登录结果对象
     */
    public P6eLoginDto codeLogin(P6eCodeLoginDto param);

    /**
     * 获取扫码登录数据
     * @param code CODE 标记
     * @return 登录结果对象
     */
    public P6eLoginDto getCodeLogin(String code);
}
