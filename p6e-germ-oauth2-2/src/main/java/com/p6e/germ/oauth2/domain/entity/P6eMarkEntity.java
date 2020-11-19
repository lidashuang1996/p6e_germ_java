package com.p6e.germ.oauth2.domain.entity;

import com.p6e.germ.oauth2.infrastructure.utils.GeneratorUtil;

/**
 * @author lidashuang
 * @version 1.0
 */
public class P6eMarkEntity {

    private final String mark;

    public P6eMarkEntity() {
        this.mark = GeneratorUtil.uuid();
    }

    public P6eMarkEntity(String mark) {
        this.mark = mark;
    }

    public void get() {
    }
}
