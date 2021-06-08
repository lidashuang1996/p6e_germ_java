package com.p6e.germ.oauth2.application;

import com.p6e.germ.oauth2.model.*;
import com.p6e.germ.oauth2.model.P6eInfoAuthModel;

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
    public com.p6e.germ.oauth2.model.P6eAuthModel.DtoResult verification(com.p6e.germ.oauth2.model.P6eAuthModel.DtoParam param) ;

    /**
     * code 回调验证方式
     * @param param 参数
     * @return 结果
     */
    public P6eCodeAuthModel.DtoResult code(P6eCodeAuthModel.DtoParam param);

    /**
     * 客户端方式
     * @param param 参数
     * @return 结果
     */
    public P6eClientAuthModel.DtoResult client(P6eClientAuthModel.DtoParam param);

    /**
     * 密码方式
     * @param param 参数
     * @return 结果
     */
    public P6ePasswordAuthModel.DtoResult password(P6ePasswordAuthModel.DtoParam param);


    public P6eRefreshAuthModel.DtoResult refresh(P6eRefreshAuthModel.DtoParam param);

    /**
     * 通过 accessToken 获取用户的信息
     * @param accessToken accessToken 数据
     * @return 用户的信息对象
     */
    public P6eInfoAuthModel.DtoResult info(P6eInfoAuthModel.DtoParam param);



    public P6eCacheAuthModel.DtoResult setCache(P6eCacheAuthModel.DtoParam param);

    public P6eCacheAuthModel.DtoResult getCache(P6eCacheAuthModel.DtoParam param);

    public P6eCacheAuthModel.DtoResult cleanCache(P6eCacheAuthModel.DtoParam param);


}
