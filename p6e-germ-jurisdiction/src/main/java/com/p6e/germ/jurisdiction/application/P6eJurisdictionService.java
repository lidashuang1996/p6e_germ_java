package com.p6e.germ.jurisdiction.application;

import com.p6e.germ.jurisdiction.model.dto.P6eJurisdictionGroupRelationUserDto;
import com.p6e.germ.jurisdiction.model.dto.P6eJurisdictionDto;
import com.p6e.germ.jurisdiction.model.dto.P6eJurisdictionGroupDto;
import com.p6e.germ.jurisdiction.model.dto.P6eJurisdictionRelationGroupDto;

/**
 * @author lidashuang
 * @version 1.0
 */
public interface P6eJurisdictionService {

    /**
     * 查询权限信息
     * @param param 操作参数
     * @return 操作结果
     */
    public P6eJurisdictionDto selectJurisdiction(P6eJurisdictionDto param);

    /**
     * 查询一条权限信息
     * @param param 操作参数
     * @return 操作结果
     */
    public P6eJurisdictionDto selectOneJurisdiction(P6eJurisdictionDto param);

    /**
     * 创建权限信息
     * @param param 操作参数
     * @return 操作结果
     */
    public P6eJurisdictionDto createJurisdiction(P6eJurisdictionDto param);

    /**
     * 删除权限信息
     * @param param 操作参数
     * @return 操作结果
     */
    public P6eJurisdictionDto deleteJurisdiction(P6eJurisdictionDto param);

    /**
     * 更新权限信息
     * @param param 操作参数
     * @return 操作结果
     */
    public P6eJurisdictionDto updateJurisdiction(P6eJurisdictionDto param);

    /**
     * 查询权限组数据
     * @param param 操作参数
     * @return 操作结果
     */
    public P6eJurisdictionGroupDto selectJurisdictionGroup(P6eJurisdictionGroupDto param);

    /**
     * 查询一条权限组数据
     * @param param 操作参数
     * @return 操作结果
     */
    public P6eJurisdictionGroupDto selectOneJurisdictionGroup(P6eJurisdictionGroupDto param);

    /**
     * 创建权限组数据
     * @param param 操作参数
     * @return 操作结果
     */
    public P6eJurisdictionGroupDto createJurisdictionGroup(P6eJurisdictionGroupDto param);

    /**
     * 删除权限组数据
     * @param param 操作参数
     * @return 操作结果
     */
    public P6eJurisdictionGroupDto deleteJurisdictionGroup(P6eJurisdictionGroupDto param);

    /**
     * 更新权限组数据
     * @param param 操作参数
     * @return 操作结果
     */
    public P6eJurisdictionGroupDto updateJurisdictionGroup(P6eJurisdictionGroupDto param);

    /**
     * 查询权限关联组数据
     * @param param 操作参数
     * @return 操作结果
     */
    public P6eJurisdictionRelationGroupDto selectJurisdictionRelationGroup(P6eJurisdictionRelationGroupDto param);

    /**
     * 查询权限关联组数据
     * @param param 操作参数
     * @return 操作结果
     */
    public P6eJurisdictionRelationGroupDto selectOneJurisdictionRelationGroup(P6eJurisdictionRelationGroupDto param);

    /**
     * 创建权限关联组数据
     * @param param 操作参数
     * @return 操作结果
     */
    public P6eJurisdictionRelationGroupDto createJurisdictionRelationGroup(P6eJurisdictionRelationGroupDto param);

    /**
     * 查询权限关联组数据
     * @param param 操作参数
     * @return 操作结果
     */
    public P6eJurisdictionRelationGroupDto deleteJurisdictionRelationGroup(P6eJurisdictionRelationGroupDto param);

    /**
     * 查询一条权限组关联用户数据
     * @param param 操作参数
     * @return 操作结果
     */
    public P6eJurisdictionGroupRelationUserDto selectJurisdictionGroupRelationUser(P6eJurisdictionGroupRelationUserDto param);

    /**
     * 删除权限组关联用户数据
     * @param param 操作参数
     * @return 操作结果
     */
    public P6eJurisdictionGroupRelationUserDto selectOneJurisdictionGroupRelationUser(P6eJurisdictionGroupRelationUserDto param);

    /**
     * 创建权删除权限组关联用户数据限关联组数据
     * @param param 操作参数
     * @return 操作结果
     */
    public P6eJurisdictionGroupRelationUserDto createJurisdictionGroupRelationUser(P6eJurisdictionGroupRelationUserDto param);

    /**
     * 删除权限组关联用户数据
     * @param param 操作参数
     * @return 操作结果
     */
    public P6eJurisdictionGroupRelationUserDto deleteJurisdictionGroupRelationUser(P6eJurisdictionGroupRelationUserDto param);

}
