package com.p6e.germ.security.service;

import com.p6e.germ.security.model.dto.P6eListResultDto;
import com.p6e.germ.security.model.dto.P6eSecurityGroupRelationUserParamDto;
import com.p6e.germ.security.model.dto.P6eSecurityGroupRelationUserResultDto;

import java.util.List;

/**
 * @author lidashuang
 * @version 1.0
 */
public interface P6eSecurityGroupRelationUserService {

    /**
     * 查询安全组关联用户数据
     * @param param 安全组关联用户参数对象
     * @return 安全组关联用户结果对象
     */
    public P6eListResultDto<P6eSecurityGroupRelationUserResultDto> select(P6eSecurityGroupRelationUserParamDto param);

    /**
     * 新增安全组关联用户数据
     * @param param 安全组关联用户参数对象
     * @return 安全组关联用户结果对象
     */
    public P6eSecurityGroupRelationUserResultDto create(P6eSecurityGroupRelationUserParamDto param);

    /**
     * 删除安全组关联用户数据
     * @param param 安全组关联用户参数对象
     * @return 安全组关联用户结果对象
     */
    public List<P6eSecurityGroupRelationUserResultDto> delete(P6eSecurityGroupRelationUserParamDto param);
}
