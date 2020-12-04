package com.p6e.germ.oauth2.application;

import com.p6e.germ.oauth2.model.dto.P6eLogDataDto;

/**
 * 日志数据服务
 * @author lidashuang
 * @version 1.0
 */
public interface P6eLogDataService {

    /**
     * 查询一条日志信息
     * @param param 查询的日志参数
     * @return 查询的日志信息
     */
    public P6eLogDataDto get(P6eLogDataDto param);

    /**
     * 查询多条日志信息
     * @param param 查询的日志参数
     * @return 查询的日志信息
     */
    public P6eLogDataDto select(P6eLogDataDto param);
}
