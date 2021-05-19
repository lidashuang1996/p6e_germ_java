package com.p6e.germ.jurisdiction.domain.aggregate;

import com.p6e.germ.jurisdiction.domain.keyvalue.P6eJurisdictionWholeDataKeyValue;

/**
 * 权限管理的聚合根
 * @author lidashuang
 * @version 1.0
 */
public class P6eJurisdictionWholeDataAggregate {

    private final P6eJurisdictionWholeDataKeyValue data;

    public static P6eJurisdictionWholeDataAggregate select(P6eJurisdictionWholeDataKeyValue data) {
        return new P6eJurisdictionWholeDataAggregate(data);
    }

    public P6eJurisdictionWholeDataAggregate(P6eJurisdictionWholeDataKeyValue data) {
        this.data = data;
    }
}
