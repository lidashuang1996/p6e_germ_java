package com.p6e.germ.oauth2.application;

import com.p6e.germ.oauth2.model.dto.P6eUserDataDto;

/**
 * @author lidashuang
 * @version 1.0
 */
public interface P6eUserDataService {

    /**
     * 查询用户信息
     * @param param 查询的日志参数
     * @return 查询的日志信息
     */
    public P6eUserDataDto get(P6eUserDataDto param);

    /**
     * 更新用户信息
     * @param param 查询的日志参数
     * @return 查询的日志信息
     */
    public P6eUserDataDto update(P6eUserDataDto param);

    /**
     * 删除用户信息
     * @param param 查询的日志参数
     * @return 查询的日志信息
     */
    public P6eUserDataDto delete(P6eUserDataDto param);

    /**
     * 查询多条用户信息
     * @param param 查询的日志参数
     * @return 查询的日志信息
     */
    public P6eUserDataDto select(P6eUserDataDto param);

}
