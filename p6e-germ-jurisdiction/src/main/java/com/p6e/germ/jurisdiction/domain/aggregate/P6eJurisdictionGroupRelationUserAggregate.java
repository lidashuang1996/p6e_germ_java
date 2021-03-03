package com.p6e.germ.jurisdiction.domain.aggregate;

import com.p6e.germ.jurisdiction.domain.entity.P6eJurisdictionGroupEntity;
import com.p6e.germ.jurisdiction.domain.entity.P6eUserEntity;
import com.p6e.germ.jurisdiction.infrastructure.mapper.P6eJurisdictionGroupRelationUserMapper;
import com.p6e.germ.jurisdiction.infrastructure.mapper.P6eJurisdictionWholeDataMapper;
import com.p6e.germ.jurisdiction.model.db.P6eJurisdictionGroupRelationUserDb;
import com.p6e.germ.jurisdiction.model.db.P6eWholeJurisdictionUserDb;
import com.p6e.germ.jurisdiction.utils.P6eSpringUtil;

import java.util.List;

/**
 * @author lidashuang
 * @version 1.0
 */
public class P6eJurisdictionGroupRelationUserAggregate {

    private final P6eUserEntity p6eUserEntity;
    private final P6eJurisdictionGroupEntity p6eJurisdictionGroupEntity;
    private final P6eJurisdictionGroupRelationUserDb p6eJurisdictionGroupRelationUserDb;

    /** 注入数据操作对象 */
    private final P6eJurisdictionWholeDataMapper
            p6eJurisdictionWholeDataMapper = P6eSpringUtil.getBean(P6eJurisdictionWholeDataMapper.class);

    /** 注入数据操作对象 */
    private final P6eJurisdictionGroupRelationUserMapper p6eJurisdictionGroupRelationUserMapper =
            P6eSpringUtil.getBean(P6eJurisdictionGroupRelationUserMapper.class);

    public P6eJurisdictionGroupRelationUserAggregate(Integer uid, Integer gid) {
        this.p6eUserEntity = new P6eUserEntity(uid);
        this.p6eJurisdictionGroupEntity = new P6eJurisdictionGroupEntity(gid);
        this.p6eJurisdictionGroupRelationUserDb = p6eJurisdictionGroupRelationUserMapper.selectOneData(
                new P6eJurisdictionGroupRelationUserDb().setGid(gid).setUid(uid));
        if (this.p6eJurisdictionGroupRelationUserDb == null) {
            throw new NullPointerException();
        }
    }

    public P6eJurisdictionGroupRelationUserAggregate(P6eJurisdictionGroupRelationUserDb p6eJurisdictionGroupRelationUserDb) {
        if (p6eJurisdictionGroupRelationUserDb == null
                || p6eJurisdictionGroupRelationUserDb.getGid() ==  null
                || p6eJurisdictionGroupRelationUserDb.getUid() == null) {
            throw new NullPointerException();
        }
        this.p6eUserEntity = new P6eUserEntity(p6eJurisdictionGroupRelationUserDb.getUid());
        this.p6eJurisdictionGroupEntity = new P6eJurisdictionGroupEntity(p6eJurisdictionGroupRelationUserDb.getGid());
        this.p6eJurisdictionGroupRelationUserDb = p6eJurisdictionGroupRelationUserDb;
    }

    public P6eJurisdictionGroupRelationUserDb get() {
        return p6eJurisdictionGroupRelationUserDb;
    }

    public Long count() {
        return p6eJurisdictionGroupRelationUserMapper.count(p6eJurisdictionGroupRelationUserDb);
    }

    public List<P6eJurisdictionGroupRelationUserDb> select() {
        return p6eJurisdictionGroupRelationUserMapper.select(p6eJurisdictionGroupRelationUserDb);
    }

    public P6eJurisdictionGroupRelationUserDb create() {
        if (p6eUserEntity != null && p6eJurisdictionGroupEntity != null
                && p6eJurisdictionGroupRelationUserMapper.create(p6eJurisdictionGroupRelationUserDb) > 0) {
            return new P6eJurisdictionGroupRelationUserAggregate(p6eJurisdictionGroupRelationUserDb).get();
        }
        return null;
    }

    public P6eJurisdictionGroupRelationUserDb delete() {
        if (p6eUserEntity != null && p6eJurisdictionGroupEntity != null) {
            final P6eJurisdictionGroupRelationUserDb result =
                    p6eJurisdictionGroupRelationUserMapper.selectOneData(p6eJurisdictionGroupRelationUserDb);
            if (p6eJurisdictionGroupRelationUserMapper.delete(p6eJurisdictionGroupRelationUserDb) > 0) {
                return result;
            }
        }
        return null;
    }

    public List<P6eWholeJurisdictionUserDb> getWholeUser() {
        return p6eJurisdictionWholeDataMapper.user(
                new P6eWholeJurisdictionUserDb().setUserId(p6eJurisdictionGroupRelationUserDb.getUid()));
    }
}
