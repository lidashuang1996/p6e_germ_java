package com.p6e.germ.jurisdiction.infrastructure.mapper;

import com.p6e.germ.jurisdiction.model.db.P6eJurisdictionGroupDb;
import java.util.List;

/**
 * @author lidashuang
 * @version 1.0
 */
public interface P6eJurisdictionGroupMapper {

    /**
     * 查询全部的数据的个数
     * @param db 安全组参数
     * @return 数据的个数
     */
    public Long count(P6eJurisdictionGroupDb db);

    /**
     * 创建一条安全组的数据
     * @param db 安全组参数
     * @return 安全组的结果
     */
    public Integer create(P6eJurisdictionGroupDb db);

    /**
     * 修改一条安全组的数据
     * @param db 安全组参数
     * @return 安全组的结果
     */
    public Integer update(P6eJurisdictionGroupDb db);

    /**
     * 删除一条安全组的数据
     * @param db 安全组参数
     * @return 安全组的结果
     */
    public Integer delete(P6eJurisdictionGroupDb db);

    /**
     * 条件查询安全组的数据
     * @param db 安全组参数
     * @return 安全组的结果
     */
    public List<P6eJurisdictionGroupDb> select(P6eJurisdictionGroupDb db);

    /**
     * 条件查询一条安全组的数据
     * @param db 安全组参数
     * @return 安全组的结果
     */
    public P6eJurisdictionGroupDb selectOneData(P6eJurisdictionGroupDb db);

}
