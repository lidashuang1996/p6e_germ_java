package com.p6e.germ.oauth2.infrastructure.repository.mapper;

import com.p6e.germ.oauth2.infrastructure.repository.db.P6eClientDb;

import java.util.List;

/**
 * @author lidashuang
 * @version 1.0
 */
public interface P6eClientMapper {

    public int count(P6eClientDb db);

    public P6eClientDb queryById(Integer id);
    public P6eClientDb queryByKey(String key);

    public List<P6eClientDb> queryAll(P6eClientDb db);

    public int create(P6eClientDb db);

    public int update(P6eClientDb db);

    public int delete(Integer id);

}
