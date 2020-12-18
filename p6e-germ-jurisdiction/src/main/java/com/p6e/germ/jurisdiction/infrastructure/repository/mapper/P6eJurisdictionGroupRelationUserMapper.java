package com.p6e.germ.jurisdiction.infrastructure.repository.mapper;

import com.p6e.germ.jurisdiction.model.db.P6eJurisdictionGroupRelationUserDb;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author lidashuang
 * @version 1.0
 */
public interface P6eJurisdictionGroupRelationUserMapper {

    /**
     * 查询条数安全组关系用户的数据
     * @param db 安全组关系用户的参数
     * @return 安全组关系用户的结果
     */
    public Long count(P6eJurisdictionGroupRelationUserDb db);

    /**
     * 创建安全组关系用户的数据
     * @param db 安全组关系用户的参数
     * @return 安全组关系用户的结果
     */
    public Integer create(P6eJurisdictionGroupRelationUserDb db);

    /**
     * 条件删除安全组关系用户的数据
     * @param db 安全组关系用户的参数
     * @return 安全组关系用户的结果
     */
    public Integer delete(P6eJurisdictionGroupRelationUserDb db);

    /**
     * 条件查询安全组关系用户的数据
     * @param db 安全组关系用户的参数
     * @return 安全组关系用户的结果
     */
    public List<P6eJurisdictionGroupRelationUserDb> select(P6eJurisdictionGroupRelationUserDb db);

    /**
     * 条件查询安全组关系用户的数据
     * @param db 安全组关系用户的参数
     * @return 安全组关系用户的结果
     */
    public P6eJurisdictionGroupRelationUserDb selectOneData(P6eJurisdictionGroupRelationUserDb db);

}

