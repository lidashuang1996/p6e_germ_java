package com.p6e.germ.oauth2.infrastructure.repository.mapper;

import com.p6e.germ.oauth2.infrastructure.repository.db.P6eClientDb;
import com.p6e.germ.oauth2.infrastructure.repository.db.P6eUserDb;

import java.util.List;

/**
 * @author lidashuang
 * @version 1.0
 */
public interface P6eUserMapper {

    public int count(P6eUserDb db);

    public P6eUserDb queryById(Integer id);
    public P6eUserDb queryByAccount(String account);

    public List<P6eUserDb> queryAll(P6eUserDb db);

    public int create(P6eUserDb db);

    public int update(P6eUserDb db);

    public int delete(P6eUserDb db);

}
