package com.p6e.germ.oauth2.mapper;

import com.p6e.germ.oauth2.model.db.P6eLogDb;
import org.apache.ibatis.annotations.Param;

/**
 * 日志表操作接口
 * @author lidashuang
 * @version 1.0
 */
public interface P6eLogMapper {

    /**
     * 创建一条日志数据保存到数据库
     * @param db 日志数据参数对象
     * @return 保存后的日志数据结果对象
     */
    public int create(@Param("DB") P6eLogDb db);

}
