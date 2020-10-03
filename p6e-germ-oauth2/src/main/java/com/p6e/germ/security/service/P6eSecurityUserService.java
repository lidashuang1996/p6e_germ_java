package com.p6e.germ.security.service;


import com.p6e.germ.security.model.dto.P6eListResultDto;
import com.p6e.germ.security.model.dto.P6eSecurityUserParamDto;
import com.p6e.germ.security.model.dto.P6eSecurityUserResultDto;

/**
 * 用户操作的接口类
 * @author lidashuang
 * @version 1.0
 */
public interface P6eSecurityUserService {

    /**
     * 创建一个用户
     * @param param 用户参数对象
     * @return 用户结果对象
     */
    public P6eSecurityUserResultDto create(P6eSecurityUserParamDto param);

    /**
     * 更新一个用户
     * @param param 用户参数对象
     * @return 用户结果对象
     */
    public P6eSecurityUserResultDto update(P6eSecurityUserParamDto param);

    /**
     * 删除一个用户
     * @param param 用户参数对象
     * @return 用户结果对象
     */
    public P6eSecurityUserResultDto delete(P6eSecurityUserParamDto param);

    /**
     * 查询用户数据
     * @param param 用户参数对象
     * @return 用户结果对象
     */
    public P6eListResultDto<P6eSecurityUserResultDto> select(P6eSecurityUserParamDto param);

    /**
     * 查询用户数据
     * @param param 用户参数对象
     * @return 用户结果对象
     */
    public P6eSecurityUserResultDto selectOneData(P6eSecurityUserParamDto param);

}
