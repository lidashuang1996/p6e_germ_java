package com.p6e.germ.jurisdiction.application;

import com.p6e.germ.jurisdiction.model.P6eJurisdictionGroupModel;
import com.p6e.germ.jurisdiction.model.P6eJurisdictionListModel;
import com.p6e.germ.jurisdiction.model.P6eJurisdictionModel;
import com.p6e.germ.jurisdiction.model.P6eJurisdictionRelationGroupModel;

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
    public P6eJurisdictionListModel.DtoResult selectListJurisdictionData(P6eJurisdictionListModel.DtoParam param);

    /**
     * 查询一条权限信息
     * @param param 操作参数
     * @return 操作结果
     */
    public P6eJurisdictionModel.DtoResult selectOneJurisdictionData(P6eJurisdictionModel.DtoParam param);

    /**
     * 创建权限信息
     * @param param 操作参数
     * @return 操作结果
     */
    public P6eJurisdictionModel.DtoResult createOneJurisdictionData(P6eJurisdictionModel.DtoParam param);

    /**
     * 删除权限信息
     * @param param 操作参数
     * @return 操作结果
     */
    public P6eJurisdictionModel.DtoResult deleteOneJurisdictionData(P6eJurisdictionModel.DtoParam param);

    /**
     * 更新权限信息
     * @param param 操作参数
     * @return 操作结果
     */
    public P6eJurisdictionModel.DtoResult updateOneJurisdictionData(P6eJurisdictionModel.DtoParam param);

    /**
     * 查询权限组数据
     * @param param 操作参数
     * @return 操作结果
     */
    public P6eJurisdictionGroupModel.DtoResult selectJurisdictionGroupData(P6eJurisdictionGroupModel.DtoParam param);

    /**
     * 查询一条权限组数据
     * @param param 操作参数
     * @return 操作结果
     */
    public P6eJurisdictionGroupModel.DtoResult selectOneJurisdictionGroupData(P6eJurisdictionGroupModel.DtoParam param);

    /**
     * 创建权限组数据
     * @param param 操作参数
     * @return 操作结果
     */
    public P6eJurisdictionGroupModel.DtoResult createJurisdictionGroupData(P6eJurisdictionGroupModel.DtoParam param);

    /**
     * 删除权限组数据
     * @param param 操作参数
     * @return 操作结果
     */
    public P6eJurisdictionGroupModel.DtoResult deleteJurisdictionGroupData(P6eJurisdictionGroupModel.DtoParam param);

    /**
     * 更新权限组数据
     * @param param 操作参数
     * @return 操作结果
     */
    public P6eJurisdictionGroupModel.DtoResult updateJurisdictionGroupData(P6eJurisdictionGroupModel.DtoParam param);


    /**
     * 查询权限关联组数据
     * @param param 操作参数
     * @return 操作结果
     */
    public P6eJurisdictionRelationGroupModel.DtoResult selectJurisdictionRelationGroup(P6eJurisdictionRelationGroupModel.DtoParam param);

    /**
     * 查询权限关联组数据
     * @param param 操作参数
     * @return 操作结果
     */
    public P6eJurisdictionRelationGroupModel.DtoResult selectOneJurisdictionRelationGroup(P6eJurisdictionRelationGroupModel.DtoParam param);

    /**
     * 创建权限关联组数据
     * @param param 操作参数
     * @return 操作结果
     */
    public P6eJurisdictionRelationGroupModel.DtoResult createJurisdictionRelationGroup(P6eJurisdictionRelationGroupModel.DtoParam param);

    /**
     * 查询权限关联组数据
     * @param param 操作参数
     * @return 操作结果
     */
    public P6eJurisdictionRelationGroupModel.DtoResult deleteJurisdictionRelationGroup(P6eJurisdictionRelationGroupModel.DtoParam param);

}
