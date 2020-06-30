package com.p6e.germ.oauth2.service;

import com.p6e.germ.oauth2.model.dto.P6eUserParamDto;
import com.p6e.germ.oauth2.model.dto.P6eUserResultDto;

/**
 * 用户服务层
 * @author lidashuang
 * @version 1.0
 */
public interface P6eUserService {

    /**
     * 查询账号信息，以及和平台的联系
     * @param param 查询参数
     * @return 结果参数
     */
    public P6eUserResultDto select(P6eUserParamDto param);

}
