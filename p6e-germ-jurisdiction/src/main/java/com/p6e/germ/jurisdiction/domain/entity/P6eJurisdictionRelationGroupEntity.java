package com.p6e.germ.jurisdiction.domain.entity;

import com.p6e.germ.common.utils.P6eSpringUtil;
import com.p6e.germ.jurisdiction.infrastructure.mapper.P6eJurisdictionRelationGroupMapper;
import com.p6e.germ.jurisdiction.model.db.P6eJurisdictionRelationGroupDb;

/**
 * @author lidashuang
 * @version 1.0
 */
public class P6eJurisdictionRelationGroupEntity {

    /** 注入数据操作对象 */
    private static final P6eJurisdictionRelationGroupMapper
            JURISDICTION_RELATION_GROUP_MAPPER = P6eSpringUtil.getBean(P6eJurisdictionRelationGroupMapper.class);

    /** DB 数据对象 */
    private final P6eJurisdictionRelationGroupDb data;

    /**
     * 创建的方法创建
     * @param param DB 对象
     * @return 实体对象
     */
    public static P6eJurisdictionRelationGroupEntity create(P6eJurisdictionRelationGroupDb param) {
        if (JURISDICTION_RELATION_GROUP_MAPPER.createOneData(param) > 1) {
            return select(param);
        } else {
            throw new RuntimeException(P6eJurisdictionRelationGroupEntity.class + " create fail.");
        }
    }

    /**
     * 查询的方法创建
     * @param param DB 对象
     * @return 实体对象
     */
    public static P6eJurisdictionRelationGroupEntity select(P6eJurisdictionRelationGroupDb param) {
        final P6eJurisdictionRelationGroupDb result = JURISDICTION_RELATION_GROUP_MAPPER.selectOneData(param);
        if (result == null) {
            throw new RuntimeException(P6eJurisdictionRelationGroupEntity.class + " select fail.");
        } else {
            return new P6eJurisdictionRelationGroupEntity(result);
        }
    }

    /**
     * 私有的构造方法
     */
    private P6eJurisdictionRelationGroupEntity(P6eJurisdictionRelationGroupDb data) {
        this.data = data;
    }

    /**
     * 删除数据
     * @return 实体对象
     */
    public P6eJurisdictionRelationGroupEntity delete() {
        if (JURISDICTION_RELATION_GROUP_MAPPER.deleteOneData(data) > 0) {
            return this;
        } else {
            throw new RuntimeException(P6eJurisdictionRelationGroupEntity.class + " delete fail.");
        }
    }
}
