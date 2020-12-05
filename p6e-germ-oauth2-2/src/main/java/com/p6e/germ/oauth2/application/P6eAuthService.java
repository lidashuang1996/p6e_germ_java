package com.p6e.germ.oauth2.application;

import com.p6e.germ.oauth2.model.dto.*;

/**
 * 认证服务
 * @author lidashuang
 * @version 1.0
 */
public interface P6eAuthService {

    /**
     * 验证客户端数据并返回验证成功的客户端数据
     * @param param 客户端数据
     * @return 验证后的客户端数据
     */
    public P6eAuthDto verification(P6eVerificationAuthDto param) ;

    /**
     * code 方式
     * @param param 参数
     * @return 结果
     */
    public P6eAuthDto code(P6eCodeAuthDto param);

    /**
     * 简化方式
     * @param param 参数
     * @return 结果
     */
    public P6eAuthDto simple(P6eSimpleAuthDto param);

    /**
     * code 回调验证方式
     * @param param 参数
     * @return 结果
     */
    public P6eAuthTokenDto codeCallback(P6eCodeCallbackAuthDto param);

    /**
     * 客户端方式
     * @param param 参数
     * @return 结果
     */
    public P6eAuthTokenDto client(P6eClientAuthDto param);

    /**
     * 密码方式
     * @param param 参数
     * @return 结果
     */
    public P6eAuthTokenDto password(P6ePasswordAuthDto param);

    /**
     * 刷新认证信息
     * @param accessToken accessToken 数据
     * @param refreshToken refreshToken 数据
     * @return 刷新后的对象
     */
    public P6eAuthTokenDto refresh(String accessToken, String refreshToken);

    /**
     * 通过 accessToken 获取用户的信息
     * @param accessToken accessToken 数据
     * @return 用户的信息对象
     */
    public P6eInfoDto info(String accessToken);
}
