package com.p6e.germ.jurisdiction.infrastructure.mapper;

import com.p6e.germ.jurisdiction.model.db.P6eJurisdictionDb;

import java.util.List;

/**
 * 权限查询的映射类
 * @author lidashuang
 * @version 1.0
 */
public interface P6eJurisdictionMapper {

    /**
     * 查询权限数据
     * @param db 权限参数对象
     * @return 权限结果对象
     */
    public P6eJurisdictionDb selectOneData(P6eJurisdictionDb db);

    /**
     * 创建一个权限
     * @param db 权限参数对象
     * @return 权限结果对象
     */
    public Integer createOneData(P6eJurisdictionDb db);

    /**
     * 更新一个权限
     * @param db 权限参数对象
     * @return 权限结果对象
     */
    public Integer updateOneData(P6eJurisdictionDb db);

    /**
     * 删除一个权限
     * @param db 权限参数对象
     * @return 权限结果对象
     */
    public Integer deleteOneData(P6eJurisdictionDb db);

    /**
     * 查询权限数据
     * @param db 权限参数对象
     * @return 权限结果对象
     */
    public List<P6eJurisdictionDb> select(P6eJurisdictionDb db);

    /**
     * 查询数据的条数
     * @param db 权限参数对象
     * @return 权限结果对象
     */
    public Long count(P6eJurisdictionDb db);

}
