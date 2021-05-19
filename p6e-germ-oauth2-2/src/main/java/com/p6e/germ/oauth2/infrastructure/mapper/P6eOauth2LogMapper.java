package com.p6e.germ.oauth2.infrastructure.mapper;

import com.p6e.germ.oauth2.model.db.P6eOauth2LogDb;

import java.util.List;

/**
 * 日志数据对象
 * @author lidashuang
 * @version 1.0
 */
public interface P6eOauth2LogMapper {

    /**
     * 查询总条数数据
     * @param db 参数对象
     * @return 条数
     */
    public long count(P6eOauth2LogDb db);

    /**
     * 更具 ID 查询数据
     * @param id 数据的 ID
     * @return 查询结果对象
     */
    public P6eOauth2LogDb queryById(Integer id);

    /**
     * 全部查询数据
     * @param db 参数对象
     * @return 查询结果对象
     */
    public List<P6eOauth2LogDb> queryAll(P6eOauth2LogDb db);

    /**
     * 创建数据
     * @param db 参数对象
     * @return 条数
     */
    public int create(P6eOauth2LogDb db);
}
