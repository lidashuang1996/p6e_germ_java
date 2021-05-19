package com.p6e.germ.jurisdiction.infrastructure.mapper;

import com.p6e.germ.jurisdiction.model.db.P6eJurisdictionRelationGroupDb;
import java.util.List;

/**
 * @author lidashuang
 * @version 1.0
 */
public interface P6eJurisdictionRelationGroupMapper {

    /**
     * 查询条数安全组关系用户的数据
     * @param db 安全组关系用户的参数
     * @return 安全组关系用户的结果
     */
    public Long count(P6eJurisdictionRelationGroupDb db);

    /**
     * 创建安全组关系用户的数据
     * @param db 安全组关系用户的参数
     * @return 安全组关系用户的结果
     */
    public Integer createOneData(P6eJurisdictionRelationGroupDb db);

    /**
     * 条件删除安全组关系用户的数据
     * @param db 安全组关系用户的参数
     * @return 安全组关系用户的结果
     */
    public Integer deleteOneData(P6eJurisdictionRelationGroupDb db);

    /**
     * 条件查询安全组关系用户的数据
     * @param db 安全组关系用户的参数
     * @return 安全组关系用户的结果
     */
    public List<P6eJurisdictionRelationGroupDb> select(P6eJurisdictionRelationGroupDb db);

    /**
     * 条件查询安全组关系用户的数据
     * @param db 安全组关系用户的参数
     * @return 安全组关系用户的结果
     */
    public P6eJurisdictionRelationGroupDb selectOneData(P6eJurisdictionRelationGroupDb db);

}
