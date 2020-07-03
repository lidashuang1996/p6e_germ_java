package com.p6e.germ.oauth2.mapper;

import com.p6e.germ.oauth2.model.db.P6eAdminUserDb;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 管理员用户 DB 操作
 * @author lidashuang
 * @version 1.0
 */
public interface P6eAdminUserMapper {

    /**
     * 查询账号和密码
     * @param db 查询对象
     * @return 操作结果对象
     */
    public P6eAdminUserDb selectByAccountAndPassword(@Param("DB") P6eAdminUserDb db);

    /**
     * 查询 ID
     * @param db 查询对象
     * @return 操作结果对象
     */
    public P6eAdminUserDb selectById(@Param("DB") P6eAdminUserDb db);

    /**
     * 查询账号
     * @param db 查询对象
     * @return 操作结果对象
     */
    public P6eAdminUserDb selectByAccount(@Param("DB") P6eAdminUserDb db);

    /**
     * 条件查询
     * @param db 查询对象
     * @return 操作结果对象
     */
    public List<P6eAdminUserDb> select(@Param("DB") P6eAdminUserDb db);

    /**
     * 条数查询
     * @param db 查询对象
     * @return 操作结果对象
     */
    public Long count(@Param("DB") P6eAdminUserDb db);

    /**
     * 创建一条数据
     * @param db 创建对象
     * @return 操作结果对象
     */
    public Integer createInfo(@Param("DB") P6eAdminUserDb db);

    /**
     * 创建一条数据
     * @param db 创建对象
     * @return 操作结果对象
     */
    public Integer createAuth(@Param("DB") P6eAdminUserDb db);

    /**
     * 删除一条数据
     * @param db 删除对象
     * @return 操作结果对象
     */
    public Integer delete(@Param("DB") P6eAdminUserDb db);

    /**
     * 更新一条数据
     * @param db 更新对象
     * @return 操作结果对象
     */
    public Integer update(@Param("DB") P6eAdminUserDb db);

}
