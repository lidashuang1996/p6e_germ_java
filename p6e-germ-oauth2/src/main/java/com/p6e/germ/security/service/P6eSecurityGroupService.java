package com.p6e.germ.security.service;

import com.p6e.germ.security.model.dto.P6eListResultDto;
import com.p6e.germ.security.model.dto.P6eSecurityGroupParamDto;
import com.p6e.germ.security.model.dto.P6eSecurityGroupResultDto;

import java.util.List;

/**
 * 保护措施-安全组操作的接口类
 * @author lidashuang
 * @version 1.0
 */
public interface P6eSecurityGroupService {

    /**
     * 创建一个安全组
     * @param param 安全组参数对象
     * @return 安全组结果对象
     */
    public P6eSecurityGroupResultDto create(P6eSecurityGroupParamDto param);

    /**
     * 更新一个安全组
     * @param param 安全组参数对象
     * @return 安全组结果对象
     */
    public P6eSecurityGroupResultDto update(P6eSecurityGroupParamDto param);

    /**
     * 删除一个安全组
     * @param param 安全组参数对象
     * @return 安全组结果对象
     */
    public P6eSecurityGroupResultDto delete(P6eSecurityGroupParamDto param);

    /**
     * 删除全部安全组
     * @return 安全组结果对象
     */
    public List<P6eSecurityGroupResultDto> clean();

    /**
     * 查询安全组数据
     * @param param 安全组参数对象
     * @return 安全组结果对象
     */
    public P6eListResultDto<P6eSecurityGroupResultDto> select(P6eSecurityGroupParamDto param);

    /**
     * 查询安全组数据
     * @param param 安全组参数对象
     * @return 安全组结果对象
     */
    public P6eSecurityGroupResultDto selectOneData(P6eSecurityGroupParamDto param);

}
