package com.p6e.germ.oauth2.service;

import com.p6e.germ.oauth2.model.dto.P6eAdminSignParamDto;
import com.p6e.germ.oauth2.model.dto.P6eAdminSignResultDto;

/**
 * 管理员账号 登录 注册 退出 刷新 功能的定于接口
 * @author lidashuang
 * @version 1.0
 */
public interface P6eAdminSignService {

    /**
     * 登录
     * @param param 参数对象
     * @return 结果对象
     */
    public P6eAdminSignResultDto in(P6eAdminSignParamDto param);

    /**
     * 注册
     * @param param 参数对象
     * @return 结果对象
     */
    public P6eAdminSignResultDto up(P6eAdminSignParamDto param);

    /**
     * 退出登录
     * @param param 参数对象
     * @return 结果对象
     */
    public P6eAdminSignResultDto out(P6eAdminSignParamDto param);

    /**
     * 刷新 token
     * @param param 参数对象
     * @return 结果对象
     */
    public P6eAdminSignResultDto refresh(P6eAdminSignParamDto param);

}
