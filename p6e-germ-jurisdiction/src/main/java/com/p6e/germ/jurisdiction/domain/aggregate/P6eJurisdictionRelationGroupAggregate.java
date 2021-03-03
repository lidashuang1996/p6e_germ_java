package com.p6e.germ.jurisdiction.domain.aggregate;

import com.p6e.germ.jurisdiction.domain.entity.P6eJurisdictionEntity;
import com.p6e.germ.jurisdiction.domain.entity.P6eJurisdictionGroupEntity;
import com.p6e.germ.jurisdiction.infrastructure.mapper.P6eJurisdictionRelationGroupMapper;
import com.p6e.germ.jurisdiction.infrastructure.mapper.P6eJurisdictionWholeDataMapper;
import com.p6e.germ.jurisdiction.model.db.P6eJurisdictionRelationGroupDb;
import com.p6e.germ.jurisdiction.model.db.P6eWholeJurisdictionGroupDb;
import com.p6e.germ.jurisdiction.utils.P6eSpringUtil;

import java.util.List;

/**
 * @author lidashuang
 * @version 1.0
 */
public class P6eJurisdictionRelationGroupAggregate {

    private final P6eJurisdictionEntity p6eJurisdictionEntity;
    private final P6eJurisdictionGroupEntity p6eJurisdictionGroupEntity;
    private final P6eJurisdictionRelationGroupDb p6eJurisdictionRelationGroupDb;

    /** 注入数据操作对象 */
    private final P6eJurisdictionWholeDataMapper
            p6eJurisdictionWholeDataMapper = P6eSpringUtil.getBean(P6eJurisdictionWholeDataMapper.class);

    /** 注入数据操作对象 */
    private final P6eJurisdictionRelationGroupMapper
            p6eJurisdictionRelationGroupMapper = P6eSpringUtil.getBean(P6eJurisdictionRelationGroupMapper.class);

    public P6eJurisdictionRelationGroupAggregate(Integer jurisdictionId, Integer jurisdictionGroupId) {
        this.p6eJurisdictionEntity = new P6eJurisdictionEntity(jurisdictionId);
        this.p6eJurisdictionGroupEntity = new P6eJurisdictionGroupEntity(jurisdictionGroupId);
        this.p6eJurisdictionRelationGroupDb = p6eJurisdictionRelationGroupMapper.selectOneData(
                new P6eJurisdictionRelationGroupDb().setGid(jurisdictionGroupId).setJurisdictionId(jurisdictionId));
        if (this.p6eJurisdictionRelationGroupDb == null) {
            throw new NullPointerException();
        }
    }

    public P6eJurisdictionRelationGroupAggregate(P6eJurisdictionRelationGroupDb p6eJurisdictionRelationGroupDb) {
        if (p6eJurisdictionRelationGroupDb == null
                || p6eJurisdictionRelationGroupDb.getGid() ==  null
                || p6eJurisdictionRelationGroupDb.getJurisdictionId() == null) {
            throw new NullPointerException();
        }
        this.p6eJurisdictionEntity = new P6eJurisdictionEntity(p6eJurisdictionRelationGroupDb.getJurisdictionId());
        this.p6eJurisdictionGroupEntity = new P6eJurisdictionGroupEntity(p6eJurisdictionRelationGroupDb.getGid());
        this.p6eJurisdictionRelationGroupDb = p6eJurisdictionRelationGroupDb;
    }

    public P6eJurisdictionRelationGroupDb get() {
        return p6eJurisdictionRelationGroupDb;
    }

    public Long count() {
        return p6eJurisdictionRelationGroupMapper.count(p6eJurisdictionRelationGroupDb);
    }

    public List<P6eJurisdictionRelationGroupDb> select() {
        return p6eJurisdictionRelationGroupMapper.select(p6eJurisdictionRelationGroupDb);
    }

    public P6eJurisdictionRelationGroupDb create() {
        if (p6eJurisdictionEntity != null && p6eJurisdictionGroupEntity != null
                && p6eJurisdictionRelationGroupMapper.create(p6eJurisdictionRelationGroupDb) > 0) {
            return new P6eJurisdictionRelationGroupAggregate(p6eJurisdictionRelationGroupDb).get();
        }
        return null;
    }

    public P6eJurisdictionRelationGroupDb delete() {
        if (p6eJurisdictionEntity != null && p6eJurisdictionGroupEntity != null) {
            final P6eJurisdictionRelationGroupDb result =
                    p6eJurisdictionRelationGroupMapper.selectOneData(p6eJurisdictionRelationGroupDb);
            if (p6eJurisdictionRelationGroupMapper.delete(p6eJurisdictionRelationGroupDb) > 0) {
                return result;
            }
        }
        return null;
    }

    public List<P6eWholeJurisdictionGroupDb> getWholeGroup() {
        return p6eJurisdictionWholeDataMapper.group(
                new P6eWholeJurisdictionGroupDb().setGroupId(p6eJurisdictionRelationGroupDb.getGid()));
    }
}
