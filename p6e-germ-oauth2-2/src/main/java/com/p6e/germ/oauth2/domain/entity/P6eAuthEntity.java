package com.p6e.germ.oauth2.domain.entity;

import com.p6e.germ.oauth2.infrastructure.utils.GeneratorUtil;

/**
 * @author lidashuang
 * @version 1.0
 */
public class P6eAuthEntity {

    /** 唯一标记 */
    private final String uniqueId;

    public static P6eAuthEntity create() {
        return new P6eAuthEntity();
    }

    private P6eAuthEntity() {
        this.uniqueId = GeneratorUtil.uuid();
    }
}
