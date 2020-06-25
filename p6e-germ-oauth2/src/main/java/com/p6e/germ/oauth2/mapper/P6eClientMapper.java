package com.p6e.germ.oauth2.mapper;

import com.p6e.germ.oauth2.model.db.P6eClientDb;
import org.apache.ibatis.annotations.Param;

/**
 * @author lidashuang
 * @version 1.0
 */
public interface P6eClientMapper {

    /**
     * 条件查询一条数据
     * @param db 参数对象
     * @return 结果对象
     */
    public P6eClientDb selectOne(@Param("DB") P6eClientDb db);

}

