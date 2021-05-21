package com.p6e.germ.oauth2.application;

import com.p6e.germ.oauth2.model.P6eAuthModel;
import com.p6e.germ.oauth2.model.P6eAuthTokenModel;
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
    public P6eAuthModel.DtoResult verification(P6eAuthModel.DtoParam param) ;

    /**
     * code 回调验证方式
     * @param param 参数
     * @return 结果
     */
    public P6eAuthTokenModel.DtoResult code(P6eAuthTokenModel.DtoParam param);

    /**
     * 客户端方式
     * @param param 参数
     * @return 结果
     */
    public P6eAuthTokenModel.DtoResult client(P6eAuthTokenModel.DtoParam param);

    /**
     * 密码方式
     * @param param 参数
     * @return 结果
     */
    public P6eAuthTokenModel.DtoResult password(P6eAuthTokenModel.DtoParam param);


    public P6eAuthTokenModel.DtoResult refresh(P6eAuthTokenModel.DtoParam param);

    /**
     * 通过 accessToken 获取用户的信息
     * @param accessToken accessToken 数据
     * @return 用户的信息对象
     */
    public P6eInfoDto info(String accessToken);
}
