package com.p6e.germ.oauth2.service;

import com.p6e.germ.oauth2.model.dto.P6eClientParamDto;
import com.p6e.germ.oauth2.model.dto.P6eClientResultDto;

/**
 * @author lidashuang
 * @version 1.0
 */
public interface P6eClientService {

    /**
     * 条件查询一条数据
     * @param param 参数对象
     * @return 结果对象
     */
    public P6eClientResultDto selectOne(P6eClientParamDto param);

}
