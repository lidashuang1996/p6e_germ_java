package com.p6e.germ.oauth2.mapper;

import com.p6e.germ.oauth2.model.db.P6eOauth2AdminUserDb;
import org.apache.ibatis.annotations.Param;

/**
 * @author lidashuang
 * @version 1.0
 */
public interface P6eOauth2AdminUserMapper {

    /**
     * 查询 账号/密码 是否匹配
     * @param db 查询参数对象
     * @return 查询结果对象
     */
    public P6eOauth2AdminUserDb select(@Param("DB") P6eOauth2AdminUserDb db);

}
