package com.p6e.germ.oauth2.infrastructure.repository.mapper;

import com.p6e.germ.oauth2.model.db.P6eOauth2ClientDb;

import java.util.List;

/**
 * 客户端数据对象
 * @author lidashuang
 * @version 1.0
 */
public interface P6eOauth2ClientMapper {

    /**
     * 查询总条数数据
     * @param db 参数对象
     * @return 条数
     */
    public long count(P6eOauth2ClientDb db);

    /**
     * 更具 ID 查询数据
     * @param id 数据的 ID
     * @return 查询结果对象
     */
    public P6eOauth2ClientDb queryById(Integer id);

    /**
     * 更具 KEY 查询数据
     * @param key 数据的 KEY
     * @return 查询结果对象
     */
    public P6eOauth2ClientDb queryByKey(String key);

    /**
     * 全部查询数据
     * @param db 参数对象
     * @return 查询结果对象
     */
    public List<P6eOauth2ClientDb> queryAll(P6eOauth2ClientDb db);

    /**
     * 创建数据
     * @param db 参数对象
     * @return 条数
     */
    public int create(P6eOauth2ClientDb db);

    /**
     * 修改数据
     * @param db 参数对象
     * @return 条数
     */
    public int update(P6eOauth2ClientDb db);

    /**
     * 删除数据
     * @param id 删除数据的 ID
     * @return 条数
     */
    public int delete(Integer id);

}
