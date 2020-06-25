package com.p6e.germ.oauth2.service;

import com.p6e.germ.oauth2.model.dto.P6eSignParamDto;
import com.p6e.germ.oauth2.model.dto.P6eSignResultDto;

/**
 * 正常登录的服务
 * @author lidashuang
 * @version 1.0
 */
public interface P6eSignService {

    /**
     * 登录的方法
     * @param param 登录的请求参数
     * @return 操作后返回的对象
     */
    public P6eSignResultDto in(P6eSignParamDto param);

    /**
     * 获取数据的方法
     * @param param 刷新的请求参数
     * @return 操作后返回的对象
     */
    public P6eSignResultDto get(P6eSignParamDto param);

    /**
     * 刷新登录的方法
     * @param param 刷新的请求参数
     * @return 操作后返回的对象
     */
    public P6eSignResultDto refresh(P6eSignParamDto param);

    /**
     * 退出登录的方法
     * @param param 推出登录的请求参数
     * @return 操作后返回的对象
     */
    public P6eSignResultDto out(P6eSignParamDto param);

}
