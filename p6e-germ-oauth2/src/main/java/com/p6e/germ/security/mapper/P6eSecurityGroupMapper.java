package com.p6e.germ.security.mapper;

import com.p6e.germ.security.model.db.P6eSecurityGroupDb;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author lidashuang
 * @version 1.0
 */
public interface P6eSecurityGroupMapper {

    /**
     * 查询全部的数据的个数
     * @param db 安全组参数
     * @return 数据的个数
     */
    public Long count(@Param("DB") P6eSecurityGroupDb db);

    /**
     * 创建一条安全组的数据
     * @param db 安全组参数
     * @return 安全组的结果
     */
    public Integer create(@Param("DB")P6eSecurityGroupDb db);

    /**
     * 修改一条安全组的数据
     * @param db 安全组参数
     * @return 安全组的结果
     */
    public Integer update(@Param("DB") P6eSecurityGroupDb db);

    /**
     * 删除一条安全组的数据
     * @param db 安全组参数
     * @return 安全组的结果
     */
    public Integer delete(@Param("DB") P6eSecurityGroupDb db);

    /**
     * 删除全部安全组的数据
     * @return 安全组的结果
     */
    public Integer clean();

    /**
     * 条件查询安全组的数据
     * @param db 安全组参数
     * @return 安全组的结果
     */
    public List<P6eSecurityGroupDb> select(@Param("DB") P6eSecurityGroupDb db);

    /**
     * 条件查询一条安全组的数据
     * @param db 安全组参数
     * @return 安全组的结果
     */
    public P6eSecurityGroupDb selectOneData(@Param("DB") P6eSecurityGroupDb db);

}
