package com.p6e.germ.oauth2.domain.entity;

import com.p6e.germ.oauth2.model.db.P6eOauth2LogDb;
import com.p6e.germ.oauth2.infrastructure.repository.mapper.P6eOauth2LogMapper;
import com.p6e.germ.oauth2.infrastructure.utils.P6eSpringUtil;

import java.util.List;

/**
 * @author lidashuang
 * @version 1.0
 */
public class P6eLogEntity {

    private P6eOauth2LogDb p6eOauth2LogDb;
    private final P6eOauth2LogMapper p6eOauth2LogMapper = P6eSpringUtil.getBean(P6eOauth2LogMapper.class);

    public P6eLogEntity(Integer id) {
        this.p6eOauth2LogDb = p6eOauth2LogMapper.queryById(id);
        if (this.p6eOauth2LogDb == null) {
            throw new RuntimeException();
        }
    }

    public P6eLogEntity(P6eOauth2LogDb p6eOauth2LogDb) {
        this.p6eOauth2LogDb = p6eOauth2LogDb;
    }

    public P6eOauth2LogDb create() {
        if (p6eOauth2LogMapper.create(p6eOauth2LogDb) > 0) {
            p6eOauth2LogDb = p6eOauth2LogMapper.queryById(p6eOauth2LogDb.getId());
            return p6eOauth2LogDb;
        } else {
            return null;
        }
    }

    public List<P6eOauth2LogDb> select() {
        return p6eOauth2LogMapper.queryAll(p6eOauth2LogDb);
    }

    public Long count() {
        return p6eOauth2LogMapper.count(p6eOauth2LogDb);
    }

    public P6eOauth2LogDb get() {
        return p6eOauth2LogDb;
    }
}
