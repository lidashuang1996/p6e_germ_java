package com.p6e.germ.security.service;

import com.p6e.germ.security.model.dto.P6eOauth2AuthParamDto;
import com.p6e.germ.security.model.dto.P6eOauth2AuthResultDto;

/**
 * @author lidashuang
 * @version 1.0
 */
public interface P6eOauth2AuthService {

    /**
     * 认证
     * @param param 认证参数对象
     * @return 认证结果对象
     */
    P6eOauth2AuthResultDto auth(P6eOauth2AuthParamDto param);

    /**
     * 认证信息
     * @param param 认证参数对象
     * @return 认证结果对象
     */
    P6eOauth2AuthResultDto info(P6eOauth2AuthParamDto param);

    /**
     * 认证刷新
     * @param param 认证参数对象
     * @return 认证结果对象
     */
    P6eOauth2AuthResultDto refresh(P6eOauth2AuthParamDto param);

    /**
     * 认证退出
     * @param param 认证参数对象
     * @return 认证结果对象
     */
    P6eOauth2AuthResultDto logout(P6eOauth2AuthParamDto param);

}
