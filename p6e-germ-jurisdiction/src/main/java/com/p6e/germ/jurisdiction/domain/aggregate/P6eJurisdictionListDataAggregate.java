package com.p6e.germ.jurisdiction.domain.aggregate;

import com.p6e.germ.common.utils.P6eSpringUtil;
import com.p6e.germ.jurisdiction.domain.entity.P6eJurisdictionEntity;
import com.p6e.germ.jurisdiction.infrastructure.error.P6eDataNullRuntimeException;
import com.p6e.germ.jurisdiction.infrastructure.mapper.P6eJurisdictionMapper;
import com.p6e.germ.jurisdiction.model.db.P6eJurisdictionDb;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lidashuang
 * @version 1.0
 */
public class P6eJurisdictionListDataAggregate {

    /** 注入数据操作对象 */
    private static final P6eJurisdictionMapper JURISDICTION_MAPPER = P6eSpringUtil.getBean(P6eJurisdictionMapper.class);


    private final long count;
    private final List<P6eJurisdictionDb> list;

    /**
     * 查询的方法创建
     * @param param DB 对象
     * @return 实体对象
     */
    public static P6eJurisdictionListDataAggregate select(P6eJurisdictionDb param) {
        return new P6eJurisdictionListDataAggregate(param);
    }

    public P6eJurisdictionListDataAggregate(P6eJurisdictionDb param) {
        this.count = JURISDICTION_MAPPER.count(param);
        List<P6eJurisdictionDb> list = JURISDICTION_MAPPER.select(param);
        if (list == null) {
            list = new ArrayList<>();
        }
        this.list = list;
    }

    public long getCount() {
        return count;
    }

    public List<P6eJurisdictionDb> getList() {
        return list;
    }
}
