package com.p6e.germ.oauth2.service;

import com.p6e.germ.oauth2.model.dto.P6eLogParamDto;
import com.p6e.germ.oauth2.model.dto.P6eLogResultDto;

/**
 * 日志服务
 * @author lidashuang
 * @version 1.0
 */
public interface P6eLogService {

    /**
     * 创建一条日志到数据库我
     * @param param 添加数据的对象
     * @return 返回数据的对象
     */
    public P6eLogResultDto create(P6eLogParamDto param);

}
