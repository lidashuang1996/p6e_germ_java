package com.p6e.germ.security.mapper;

import com.p6e.germ.security.model.db.P6eOauth2UserDb;
import org.apache.ibatis.annotations.Param;

/**
 * @author lidashuang
 * @version 1.0
 */
public interface P6eOauth2UserMapper2 {

    /**
     * 根据账号密码查询数据
     * @param db 参数对象
     * @return 查询对象
     */
    public P6eOauth2UserDb selectByAccountAndPassword(@Param("DB") P6eOauth2UserDb db);

}
