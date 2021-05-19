package com.p6e.germ.oauth2.infrastructure.mapper;

import com.p6e.germ.oauth2.model.db.P6eOauth2UserDb;

import java.util.List;

/**
 * 用户数据对象
 * @author lidashuang
 * @version 1.0
 */
public interface P6eOauth2UserMapper {
    /**
     * 查询总条数数据
     * @param db 参数对象
     * @return 条数
     */
    public Long count(P6eOauth2UserDb db);

    /**
     * 更具 ID 查询数据
     * @param id 数据的 ID
     * @return 查询结果对象
     */
    public P6eOauth2UserDb queryById(Integer id);

    /**
     * 更具 ACCOUNT 查询数据
     * @param account 数据的 account
     * @return 查询结果对象
     */
    public P6eOauth2UserDb queryByAccount(String account);

    /**
     * 更具 ACCOUNT 查询数据
     * @param qq 数据的 qq
     * @return 查询结果对象
     */
    public P6eOauth2UserDb queryByQq(String qq);

    /**
     * 全部查询数据
     * @param db 参数对象
     * @return 查询结果对象
     */
    public List<P6eOauth2UserDb> queryAll(P6eOauth2UserDb db);

    /**
     * 创建数据
     * @param db 参数对象
     * @return 条数
     */
    public int create(P6eOauth2UserDb db);

    /**
     * 修改数据
     * @param db 参数对象
     * @return 条数
     */
    public int update(P6eOauth2UserDb db);

    /**
     * 删除数据
     * @param id 删除数据的 ID
     * @return 条数
     */
    public int delete(Integer id);
}
