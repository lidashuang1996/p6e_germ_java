package com.p6e.germ.jurisdiction.domain.entity;

import com.p6e.germ.jurisdiction.infrastructure.mapper.P6eJurisdictionMapper;
import com.p6e.germ.jurisdiction.model.db.P6eJurisdictionDb;
import com.p6e.germ.jurisdiction.utils.P6eSpringUtil;

import java.util.List;

/**
 * @author lidashuang
 * @version 1.0
 */
public class P6eJurisdictionEntity {

    /** DB 数据对象 */
    private final P6eJurisdictionDb p6eJurisdictionDb;

    /** 注入数据操作对象 */
    private final P6eJurisdictionMapper p6eJurisdictionMapper = P6eSpringUtil.getBean(P6eJurisdictionMapper.class);

    public P6eJurisdictionEntity(Integer id) {
        this.p6eJurisdictionDb = p6eJurisdictionMapper.selectOneData(new P6eJurisdictionDb().setId(id));
    }

    public P6eJurisdictionEntity(String code) {
        this.p6eJurisdictionDb = p6eJurisdictionMapper.selectOneData(new P6eJurisdictionDb().setCode(code));
    }

    public P6eJurisdictionEntity(P6eJurisdictionDb p6eJurisdictionDb) {
        this.p6eJurisdictionDb = p6eJurisdictionDb;
    }

    public P6eJurisdictionDb get() {
        return p6eJurisdictionDb;
    }

    public Long count() {
        return p6eJurisdictionMapper.count(p6eJurisdictionDb);
    }

    public List<P6eJurisdictionDb> select() {
        return p6eJurisdictionMapper.select(p6eJurisdictionDb);
    }

    public P6eJurisdictionDb create() {
        if (p6eJurisdictionMapper.create(p6eJurisdictionDb) > 0) {
            return new P6eJurisdictionEntity(p6eJurisdictionDb.getId()).get();
        }
        return null;
    }

    public P6eJurisdictionDb update() {
        if (p6eJurisdictionMapper.update(p6eJurisdictionDb) > 0) {
            return new P6eJurisdictionEntity(p6eJurisdictionDb.getId()).get();
        }
        return null;
    }

    public P6eJurisdictionDb delete() {
        final P6eJurisdictionDb result = p6eJurisdictionMapper.selectOneData(p6eJurisdictionDb);
        if (result != null && p6eJurisdictionMapper.delete(p6eJurisdictionDb) > 0) {
            return result;
        }
        return null;
    }

    public void clean() {
        p6eJurisdictionMapper.clean();
    }

}
