package com.p6e.germ.oauth2.application;

import com.p6e.germ.oauth2.model.*;

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
    public P6eSecretVoucherModel.DtoResult secretVoucher(P6eSecretVoucherModel.DtoParam param);


    /**
     * 验证的用户的 ACCESS TOKEN 数据
     * @param param 验证对象
     * @return 结果对象
     */
    public P6eApModel.VerificationDtoResult verification(P6eApModel.VerificationDtoParam param);

    /**
     * 默认登陆（账号密码登录）
     * @param param 账号密码登录对象
     * @return 登录结果对象
     */
    public P6eApModel.DtoResult accountPassword(P6eApModel.DtoParam param);

    /**
     * 获取 QQ 登录的信息
     * @param mark 记号
     * @param display 显示参数
     * @return 获取的信息
     */
    public P6eOtherLoginModel.DtoResult qqInfo(P6eOtherLoginModel.DtoParam param);

    /**
     * QQ 登录
     * @param param QQ登陆认证信息
     * @return 登录结果对象
     */
    public P6eOtherLoginModel.DtoResult qqLogin(P6eOtherLoginModel.DtoParam param);

    /**
     * 获取微信登录的信息
     * @param mark 记号
     * @return 获取的信息
     */
    public P6eOtherLoginModel.DtoResult weChatInfo(P6eOtherLoginModel.DtoParam param);

    /**
     * 微信登录
     * @param param 微信登陆认证信息
     * @return 登录结果对象
     */
    public P6eOtherLoginModel.DtoResult weChatLogin(P6eOtherLoginModel.DtoParam param);

    public P6eOtherLoginModel.DtoResult sinaInfo(P6eOtherLoginModel.DtoParam param);
    public P6eOtherLoginModel.DtoResult sinaLogin(P6eOtherLoginModel.DtoParam param);

    /**
     * 生成扫码登录的基础信息
     * @return 生成扫码登录凭证信息对象
     */
    public P6eQrCodeModel.DtoResult qrCode(P6eQrCodeModel.DtoParam param);

    /**
     *
     */
    public P6eNrCodeModel.DtoResult nrCode(P6eNrCodeModel.DtoParam param);

    /**
     * 扫码登录
     * @param param 扫描登录凭证和认证信息
     * @return 登录结果对象
     */
    public P6eNrCodeModel.DtoResult nrCodeLogin(P6eNrCodeModel.DtoParam param);



    public P6eQrCodeModel.DtoResult qrCodeData(P6eQrCodeModel.DtoParam param);
    /**
     * 获取扫码登录数据
     * @param code CODE 标记
     * @return 登录结果对象
     */
    public P6eQrCodeModel.DtoResult qrCodeLogin(P6eQrCodeModel.DtoParam param);
}
