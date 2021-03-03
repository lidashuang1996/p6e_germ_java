package com.p6e.germ.jurisdiction.infrastructure.mapper;

import com.p6e.germ.jurisdiction.model.db.P6eWholeJurisdictionGroupDb;
import com.p6e.germ.jurisdiction.model.db.P6eWholeJurisdictionUserDb;

import java.util.List;

/**
 * @author lidashuang
 * @version 1.0
 */
public interface P6eJurisdictionWholeDataMapper {

    /**
     * 根据 USER ID 查询全部的权限的数据
     * @param db USER 参数
     * @return USER 结果
     */
    public List<P6eWholeJurisdictionUserDb> user(P6eWholeJurisdictionUserDb db);

    /**
     * 根据组 ID 查询全部的权限的数据
     * @param db 组参数
     * @return 组查询结果
     */
    public List<P6eWholeJurisdictionGroupDb> group(P6eWholeJurisdictionGroupDb db);
}
