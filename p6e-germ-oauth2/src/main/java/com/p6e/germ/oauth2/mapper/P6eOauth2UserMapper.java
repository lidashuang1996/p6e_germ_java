package com.p6e.germ.oauth2.mapper;

import com.p6e.germ.oauth2.model.db.P6eOauth2UserDb;
import org.apache.ibatis.annotations.Param;

/**
 * @author lidashuang
 * @version 1.0
 */
public interface P6eOauth2UserMapper {

    /**
     * 查询账号和密码
     * @param db 查询对象
     * @return 查询结果对象
     */
    public P6eOauth2UserDb select(@Param("DB") P6eOauth2UserDb db);

}
