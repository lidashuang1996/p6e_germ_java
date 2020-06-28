package com.p6e.germ.oauth2.service;

import com.p6e.germ.oauth2.model.dto.P6eAuthParamDto;
import com.p6e.germ.oauth2.model.dto.P6eAuthResultDto;

/**
 * @author lidashuang
 * @version 1.0
 */
public interface P6eAuthService {

    /**
     * 认证模式 code
     * @param param 参数对象
     * @return 结果对象
     */
    public P6eAuthResultDto code(P6eAuthParamDto param);

    /**
     * 处理认证模式 code
     * @param param 参数对象
     * @return 结果对象
     */
    public P6eAuthResultDto manageCode(P6eAuthParamDto param);

    /**
     * 处理 code 的模式
     */
    public P6eAuthResultDto collateCode(P6eAuthParamDto param);
}
