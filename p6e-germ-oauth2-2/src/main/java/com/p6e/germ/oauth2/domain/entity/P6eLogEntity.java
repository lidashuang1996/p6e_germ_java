package com.p6e.germ.oauth2.domain.entity;

import com.p6e.germ.oauth2.model.db.P6eOauth2LogDb;
import com.p6e.germ.oauth2.infrastructure.mapper.P6eOauth2LogMapper;
import com.p6e.germ.common.utils.P6eSpringUtil;

import java.util.List;

/**
 * @author lidashuang
 * @version 1.0
 */
public class P6eLogEntity {

    /** DB 对象 */
    private P6eOauth2LogDb p6eOauth2LogDb;

    /** 注入服务 */
    private final P6eOauth2LogMapper p6eOauth2LogMapper = P6eSpringUtil.getBean(P6eOauth2LogMapper.class);

    /**
     * 构造方法
     * @param id 编号
     */
    public P6eLogEntity(Integer id) {
        this.p6eOauth2LogDb = p6eOauth2LogMapper.queryById(id);
        if (this.p6eOauth2LogDb == null) {
            throw new NullPointerException(this.getClass() + " construction data ==> NullPointerException.");
        }
    }

    /**
     * 构造方法
     * @param p6eOauth2LogDb DB 对象
     */
    public P6eLogEntity(P6eOauth2LogDb p6eOauth2LogDb) {
        this.p6eOauth2LogDb = p6eOauth2LogDb;
    }

    /**
     * 创建数据
     * @return 创建的数据对象
     */
    public P6eOauth2LogDb create() {
        if (p6eOauth2LogMapper.create(p6eOauth2LogDb) > 0) {
            p6eOauth2LogDb = p6eOauth2LogMapper.queryById(p6eOauth2LogDb.getId());
            return p6eOauth2LogDb;
        } else {
            return null;
        }
    }

    /**
     * 查询多条数据
     * @return 查询的数据内容对象
     */
    public List<P6eOauth2LogDb> select() {
        return p6eOauth2LogMapper.queryAll(p6eOauth2LogDb);
    }

    /**
     * 获取数据条数
     * @return 条数
     */
    public Long count() {
        return p6eOauth2LogMapper.count(p6eOauth2LogDb);
    }

    /**
     * 获取 DB 数据
     * @return DB 对象
     */
    public P6eOauth2LogDb get() {
        return p6eOauth2LogDb;
    }
}
