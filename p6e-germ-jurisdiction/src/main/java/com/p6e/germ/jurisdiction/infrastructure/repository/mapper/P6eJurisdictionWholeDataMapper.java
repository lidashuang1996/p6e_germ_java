//package com.p6e.germ.jurisdiction.infrastructure.repository.mapper;
//
//import com.p6e.germ.security.model.db.P6eSecurityWholeDataGroupDb;
//import com.p6e.germ.security.model.db.P6eSecurityWholeDataUserDb;
//import org.apache.ibatis.annotations.Param;
//
//import java.util.List;
//
///**
// * @author lidashuang
// * @version 1.0
// */
//public interface P6eJurisdictionWholeDataMapper {
//
//    /**
//     * 根据 USER ID 查询全部的权限的数据
//     * @param db USER 参数
//     * @return USER 结果
//     */
//    public List<P6eSecurityWholeDataUserDb> user(@Param("DB") P6eSecurityWholeDataUserDb db);
//
//    /**
//     * 根据组 ID 查询全部的权限的数据
//     * @param db 组参数
//     * @return 组查询结果
//     */
//    public List<P6eSecurityWholeDataGroupDb> group(@Param("DB") P6eSecurityWholeDataGroupDb db);
//}
