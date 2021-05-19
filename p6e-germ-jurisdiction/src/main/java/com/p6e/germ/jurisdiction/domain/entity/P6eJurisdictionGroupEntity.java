package com.p6e.germ.jurisdiction.domain.entity;

import com.p6e.germ.common.utils.P6eSpringUtil;
import com.p6e.germ.jurisdiction.infrastructure.mapper.P6eJurisdictionGroupMapper;
import com.p6e.germ.jurisdiction.model.db.P6eJurisdictionGroupDb;

/**
 * 权限组实体操作的对象
 * @author lidashuang
 * @version 1.0
 */
public class P6eJurisdictionGroupEntity {

    /** 注入数据操作对象 */
    private static final P6eJurisdictionGroupMapper
            JURISDICTION_GROUP_MAPPER = P6eSpringUtil.getBean(P6eJurisdictionGroupMapper.class);

    /** DB 数据对象 */
    private final P6eJurisdictionGroupDb data;

    /**
     * 创建的方法创建
     * @param param DB 对象
     * @return 实体对象
     */
    public static P6eJurisdictionGroupEntity create(P6eJurisdictionGroupDb param) {
        if (JURISDICTION_GROUP_MAPPER.createOneData(param) > 1) {
            return select(param);
        } else {
            throw new RuntimeException(P6eJurisdictionGroupEntity.class + " create fail.");
        }
    }

    /**
     * 查询的方法创建
     * @param param DB 对象
     * @return 实体对象
     */
    public static P6eJurisdictionGroupEntity select(P6eJurisdictionGroupDb param) {
        final P6eJurisdictionGroupDb result = JURISDICTION_GROUP_MAPPER.selectOneData(param);
        if (result == null) {
            throw new RuntimeException(P6eJurisdictionGroupEntity.class + " select fail.");
        } else {
            return new P6eJurisdictionGroupEntity(result);
        }
    }

    /**
     * 私有的构造方法
     */
    private P6eJurisdictionGroupEntity(P6eJurisdictionGroupDb data) {
        this.data = data;
    }

    /**
     * 更新数据
     * @param data DB 对象
     * @return 实体对象
     */
    public P6eJurisdictionGroupEntity update(P6eJurisdictionGroupDb data) {
        if (JURISDICTION_GROUP_MAPPER.updateOneData(data.setId(data.getId())) > 0) {
            return select(JURISDICTION_GROUP_MAPPER.selectOneData(data));
        } else {
            throw new RuntimeException(this.getClass() + " update fail.");
        }
    }

    /**
     * 删除数据
     * @return 实体对象
     */
    public P6eJurisdictionGroupEntity delete() {
        if (JURISDICTION_GROUP_MAPPER.deleteOneData(data) > 0) {
            return this;
        } else {
            throw new RuntimeException(this.getClass() + " delete fail.");
        }
    }

    public P6eJurisdictionGroupDb getData() {
        return data;
    }
}

















