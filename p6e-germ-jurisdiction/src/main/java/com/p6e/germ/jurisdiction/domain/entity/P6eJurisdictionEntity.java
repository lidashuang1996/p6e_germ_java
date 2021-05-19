package com.p6e.germ.jurisdiction.domain.entity;

import com.p6e.germ.common.utils.P6eSpringUtil;
import com.p6e.germ.jurisdiction.infrastructure.error.P6eDataNoNullRuntimeException;
import com.p6e.germ.jurisdiction.infrastructure.error.P6eDataNullRuntimeException;
import com.p6e.germ.jurisdiction.infrastructure.error.P6eRelationDataException;
import com.p6e.germ.jurisdiction.infrastructure.error.P6eSqlRuntimeException;
import com.p6e.germ.jurisdiction.infrastructure.mapper.P6eJurisdictionMapper;
import com.p6e.germ.jurisdiction.infrastructure.mapper.P6eJurisdictionRelationGroupMapper;
import com.p6e.germ.jurisdiction.model.db.P6eJurisdictionDb;
import com.p6e.germ.jurisdiction.model.db.P6eJurisdictionRelationGroupDb;

import java.util.List;

/**
 * 权限操作的实体对象
 * @author lidashuang
 * @version 1.0
 */
public class P6eJurisdictionEntity {

    /** 注入数据操作对象 */
    private static final P6eJurisdictionMapper JURISDICTION_MAPPER = P6eSpringUtil.getBean(P6eJurisdictionMapper.class);
    private static final P6eJurisdictionRelationGroupMapper JURISDICTION_RELATION_GROUP_MAPPER = P6eSpringUtil.getBean(P6eJurisdictionRelationGroupMapper.class);

    /** DB 数据对象 */
    private final P6eJurisdictionDb data;

    /**
     * 创建的方法创建
     * @param param DB 对象
     * @return 实体对象
     */
    public static P6eJurisdictionEntity create(P6eJurisdictionDb param) {
        // 查询是否存在相同的 CODE 标记
        final P6eJurisdictionDb db =
                JURISDICTION_MAPPER.selectOneData(new P6eJurisdictionDb().setCode(param.getCode()));
        // 不存在就创建
        if (db == null) {
            if (JURISDICTION_MAPPER.createOneData(param) > 1) {
                return select(param);
            } else {
                throw new P6eSqlRuntimeException(P6eJurisdictionEntity.class + " create");
            }
        } else {
            // 存在抛出异常
            throw new P6eDataNoNullRuntimeException(P6eJurisdictionEntity.class + " create");
        }
    }

    /**
     * 查询的方法创建
     * @param param DB 对象
     * @return 实体对象
     */
    public static P6eJurisdictionEntity select(P6eJurisdictionDb param) {
        final P6eJurisdictionDb result = JURISDICTION_MAPPER.selectOneData(param);
        if (result == null) {
            throw new P6eDataNullRuntimeException(P6eJurisdictionEntity.class + " select");
        } else {
            return new P6eJurisdictionEntity(result);
        }
    }

    /**
     * 私有的构造方法
     */
    private P6eJurisdictionEntity(P6eJurisdictionDb data) {
        this.data = data;
    }

    /**
     * 修改数据对象
     * @param data DB 对象
     * @return 实体对象
     */
    public P6eJurisdictionEntity update(P6eJurisdictionDb data) {
        // 判断数据是否合法
        final P6eJurisdictionDb db =
                JURISDICTION_MAPPER.selectOneData(new P6eJurisdictionDb().setCode(data.getCode()));
        if (db == null || this.data.getId().equals(db.getId())) {
            if (JURISDICTION_MAPPER.updateOneData(data.setId(data.getId())) > 0) {
                return select(JURISDICTION_MAPPER.selectOneData(data));
            } else {
                throw new P6eSqlRuntimeException(this.getClass() + " update");
            }
        } else {
            throw new P6eDataNoNullRuntimeException(this.getClass() + " update");
        }
    }

    /**
     * 删除数据对象
     * @return 实体对象
     */
    public P6eJurisdictionEntity delete() {
        // 权限组没有引用就可以删除
        final List<P6eJurisdictionRelationGroupDb> list = JURISDICTION_RELATION_GROUP_MAPPER.select(
                new P6eJurisdictionRelationGroupDb().setJurisdictionId(this.data.getId()));
        if (list == null || list.size() == 0) {
            if (JURISDICTION_MAPPER.deleteOneData(data) > 0) {
                return this;
            } else {
                throw new P6eSqlRuntimeException(this.getClass() + " delete");
            }
        } else {
            throw new P6eRelationDataException(this.getClass() + " delete");
        }
    }

    /**
     * 获取数据对象
     * @return 数据对象
     */
    public P6eJurisdictionDb getData() {
        return data;
    }
}
