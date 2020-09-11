package com.p6e.germ.security.mapper;

import com.p6e.germ.security.model.db.P6eSecurityJurisdictionDb;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author lidashuang
 * @version 1.0
 */
public interface P6eSecurityJurisdictionMapper {

    /**
     * 创建一个权限
     * @param param 权限参数对象
     * @return 权限结果对象
     */
    public Integer create(@Param("DB") P6eSecurityJurisdictionDb param);

    /**
     * 更新一个权限
     * @param param 权限参数对象
     * @return 权限结果对象
     */
    public Integer update(@Param("DB") P6eSecurityJurisdictionDb param);

    /**
     * 删除一个权限
     * @param param 权限参数对象
     * @return 权限结果对象
     */
    public Integer delete(@Param("DB") P6eSecurityJurisdictionDb param);

    /**
     * 查询权限数据
     * @param param 权限参数对象
     * @return 权限结果对象
     */
    public List<P6eSecurityJurisdictionDb> select(@Param("DB") P6eSecurityJurisdictionDb param);

    /**
     * 查询权限数据
     * @param param 权限参数对象
     * @return 权限结果对象
     */
    public P6eSecurityJurisdictionDb selectOneData(@Param("DB") P6eSecurityJurisdictionDb param);

    /**
     * 查询数据的条数
     * @param paramDb 权限参数对象
     * @return 权限结果对象
     */
    public Long count(@Param("DB") P6eSecurityJurisdictionDb paramDb);
}
