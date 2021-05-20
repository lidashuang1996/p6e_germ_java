package com.p6e.germ.oauth2.application;

import com.p6e.germ.oauth2.model.P6eLoginModel;
import com.p6e.germ.oauth2.model.P6eVerificationLoginModel;
import com.p6e.germ.oauth2.model.P6eVoucherModel;
import com.p6e.germ.oauth2.model.dto.*;

/**
 * 登录服务
 * @author lidashuang
 * @version 1.0
 */
public interface P6eLoginService {

    /**
     * 验证的用户的 ACCESS TOKEN 数据
     * @param param 验证对象
     * @return 结果对象
     */
    public P6eVerificationLoginModel.DtoResult verification(P6eVerificationLoginModel.DtoParam param);

    /**
     * 获取凭证信息
     * 用于用户的登录密码的加密
     * @return 凭证对象
     */
    public P6eVoucherModel.DtoResult defaultVoucher();

    /**
     * 默认登陆（账号密码登录）
     * @param param 账号密码登录对象
     * @return 登录结果对象
     */
    public P6eLoginModel.AccountPasswordDtoResult accountPassword(P6eLoginModel.AccountPasswordDtoParam param);

    /**
     * 短信验证码信息
     * @param param 账号密码登录对象
     * @return 登录结果对象
     */
    public String smsInfo(P6eSmsInfoDto param);

    /**
     * 短信验证码登录
     * @param param 账号密码登录对象
     * @return 登录结果对象
     */
    public P6eLoginDto smsCodeLogin(P6eSmsCodeLoginDto param);

    /**
     * 邮箱验证码信息
     * @param param 账号密码登录对象
     * @return 登录结果对象
     */
    public String emailInfo(P6eEmailInfoDto param);

    /**
     * 邮箱验证码登录
     * @param param 账号密码登录对象
     * @return 登录结果对象
     */
    public P6eLoginDto emailCodeLogin(P6eEmailCodeLoginDto param);

    /**
     * 获取 QQ 登录的信息
     * @param mark 记号
     * @param display 显示参数
     * @return 获取的信息
     */
    public P6eUrlLoginDto qqInfo(String mark, String display);

    /**
     * QQ 登录
     * @param param QQ登陆认证信息
     * @return 登录结果对象
     */
    public P6eLoginDto qqLogin(P6eQqLoginDto param);

    /**
     * 获取微信登录的信息
     * @param mark 记号
     * @return 获取的信息
     */
    public P6eUrlLoginDto weChatInfo(String mark);

    /**
     * 微信登录
     * @param param 微信登陆认证信息
     * @return 登录结果对象
     */
    public P6eLoginDto weChatLogin(P6eWeChatLoginDto param);

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
