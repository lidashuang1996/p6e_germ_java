package com.p6e.germ.security.service;

import com.p6e.germ.security.model.dto.*;

import java.util.List;

/**
 * @author lidashuang
 * @version 1.0
 */
public interface P6eSecurityJurisdictionRelationGroupService {

    /**
     * 查询权限关联权限组的数据
     * @param param 权限关联权限组参数对象
     * @return 权限关联权限组结果对象
     */
    public P6eListResultDto<P6eSecurityJurisdictionRelationGroupResultDto>
                                select(P6eSecurityJurisdictionRelationGroupParamDto param);

    /**
     * 删除权限关联权限组数据
     * @param param 权限关联权限组参数对象
     * @return 权限关联权限组结果对象
     */
    public List<P6eSecurityJurisdictionRelationGroupResultDto> delete(P6eSecurityJurisdictionRelationGroupParamDto param);


}
