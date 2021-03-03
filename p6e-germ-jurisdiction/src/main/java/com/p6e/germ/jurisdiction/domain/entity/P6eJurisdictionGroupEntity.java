package com.p6e.germ.jurisdiction.domain.entity;

import com.p6e.germ.jurisdiction.infrastructure.mapper.P6eJurisdictionGroupMapper;
import com.p6e.germ.jurisdiction.model.db.P6eJurisdictionGroupDb;
import com.p6e.germ.jurisdiction.utils.P6eSpringUtil;

import java.util.List;

/**
 * @author lidashuang
 * @version 1.0
 */
public class P6eJurisdictionGroupEntity {

    /** DB 数据对象 */
    private final P6eJurisdictionGroupDb p6eJurisdictionGroupDb;

    /** 注入数据操作对象 */
    private final P6eJurisdictionGroupMapper
            p6eJurisdictionGroupMapper = P6eSpringUtil.getBean(P6eJurisdictionGroupMapper.class);

    public P6eJurisdictionGroupEntity(Integer id) {
        this.p6eJurisdictionGroupDb =
                p6eJurisdictionGroupMapper.selectOneData(new P6eJurisdictionGroupDb().setId(id));
        if (this.p6eJurisdictionGroupDb == null) {
            throw new RuntimeException();
        }
    }

    public P6eJurisdictionGroupEntity(P6eJurisdictionGroupDb p6eJurisdictionGroupDb) {
        this.p6eJurisdictionGroupDb = p6eJurisdictionGroupDb;
    }

    public P6eJurisdictionGroupDb get() {
        return p6eJurisdictionGroupDb;
    }

    public Long count() {
        return p6eJurisdictionGroupMapper.count(p6eJurisdictionGroupDb);
    }

    public List<P6eJurisdictionGroupDb> select() {
        return p6eJurisdictionGroupMapper.select(p6eJurisdictionGroupDb);
    }

    public P6eJurisdictionGroupDb create() {
        if (p6eJurisdictionGroupMapper.create(p6eJurisdictionGroupDb) > 0) {
            return new P6eJurisdictionGroupEntity(p6eJurisdictionGroupDb.getId()).get();
        }
        return null;
    }

    public P6eJurisdictionGroupDb update() {
        if (p6eJurisdictionGroupMapper.update(p6eJurisdictionGroupDb) > 0) {
            return new P6eJurisdictionGroupEntity(p6eJurisdictionGroupDb.getId()).get();
        }
        return null;
    }

    public P6eJurisdictionGroupDb delete() {
        final P6eJurisdictionGroupDb result = p6eJurisdictionGroupMapper.selectOneData(p6eJurisdictionGroupDb);
        if (result != null && p6eJurisdictionGroupMapper.delete(p6eJurisdictionGroupDb) > 0) {
            return result;
        }
        return null;
    }
}

















