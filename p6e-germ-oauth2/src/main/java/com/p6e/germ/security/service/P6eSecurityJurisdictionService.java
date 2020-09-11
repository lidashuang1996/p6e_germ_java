package com.p6e.germ.security.service;

import com.p6e.germ.security.model.dto.*;

import java.util.List;

/**
 * @author lidashuang
 * @version 1.0
 */
public interface P6eSecurityJurisdictionService {

    /**
     * 创建一个安全组
     * @param param 安全组参数对象
     * @return 安全组结果对象
     */
    public P6eSecurityJurisdictionResultDto create(P6eSecurityJurisdictionParamDto param);

    /**
     * 更新一个安全组
     * @param param 安全组参数对象
     * @return 安全组结果对象
     */
    public P6eSecurityJurisdictionResultDto update(P6eSecurityJurisdictionParamDto param);

    /**
     * 删除一个安全组
     * @param param 安全组参数对象
     * @return 安全组结果对象
     */
    public P6eSecurityJurisdictionResultDto delete(P6eSecurityJurisdictionParamDto param);

    /**
     * 删除全部安全组
     * @return 安全组结果对象
     */
    public List<P6eSecurityJurisdictionResultDto> clean();

    /**
     * 查询安全组数据
     * @param param 安全组参数对象
     * @return 安全组结果对象
     */
    public P6eListResultDto<P6eSecurityJurisdictionResultDto> select(P6eSecurityJurisdictionParamDto param);

    /**
     * 查询安全组数据
     * @param param 安全组参数对象
     * @return 安全组结果对象
     */
    public P6eSecurityJurisdictionResultDto selectOneData(P6eSecurityJurisdictionParamDto param);

}
