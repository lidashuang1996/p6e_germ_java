package com.p6e.germ.security.mapper;

import com.p6e.germ.security.model.db.P6eSecurityJurisdictionRelationGroupDb;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author lidashuang
 * @version 1.0
 */
public interface P6eSecurityJurisdictionRelationGroupMapper {

    /**
     * 查询条数安全组关系用户的数据
     * @param db 安全组关系用户的参数
     * @return 安全组关系用户的结果
     */
    public Long count(@Param("DB") P6eSecurityJurisdictionRelationGroupDb db);

    /**
     * 创建安全组关系用户的数据
     * @param db 安全组关系用户的参数
     * @return 安全组关系用户的结果
     */
    public Integer create(@Param("DB") P6eSecurityJurisdictionRelationGroupDb db);

    /**
     * 条件删除安全组关系用户的数据
     * @param db 安全组关系用户的参数
     * @return 安全组关系用户的结果
     */
    public Integer delete(@Param("DB") P6eSecurityJurisdictionRelationGroupDb db);

    /**
     * 条件查询安全组关系用户的数据
     * @param db 安全组关系用户的参数
     * @return 安全组关系用户的结果
     */
    public List<P6eSecurityJurisdictionRelationGroupDb> select(@Param("DB") P6eSecurityJurisdictionRelationGroupDb db);

    /**
     * 条件查询安全组关系用户的数据
     * @param db 安全组关系用户的参数
     * @return 安全组关系用户的结果
     */
    public P6eSecurityJurisdictionRelationGroupDb selectOneData(@Param("DB") P6eSecurityJurisdictionRelationGroupDb db);

}
