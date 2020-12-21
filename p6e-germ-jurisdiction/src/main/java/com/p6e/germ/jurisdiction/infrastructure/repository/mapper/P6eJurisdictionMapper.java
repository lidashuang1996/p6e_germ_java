package com.p6e.germ.jurisdiction.infrastructure.repository.mapper;

import com.p6e.germ.jurisdiction.model.db.P6eJurisdictionDb;

import java.util.List;

/**
 * @author lidashuang
 * @version 1.0
 */
public interface P6eJurisdictionMapper {

    /**
     * 创建一个权限
     * @param param 权限参数对象
     * @return 权限结果对象
     */
    public Integer create(P6eJurisdictionDb param);

    /**
     * 更新一个权限
     * @param param 权限参数对象
     * @return 权限结果对象
     */
    public Integer update(P6eJurisdictionDb param);

    /**
     * 删除一个权限
     * @param param 权限参数对象
     * @return 权限结果对象
     */
    public Integer delete(P6eJurisdictionDb param);

    public void clean();

    /**
     * 查询权限数据
     * @param param 权限参数对象
     * @return 权限结果对象
     */
    public List<P6eJurisdictionDb> select(P6eJurisdictionDb param);

    /**
     * 查询权限数据
     * @param param 权限参数对象
     * @return 权限结果对象
     */
    public P6eJurisdictionDb selectOneData(P6eJurisdictionDb param);

    /**
     * 查询数据的条数
     * @param paramDb 权限参数对象
     * @return 权限结果对象
     */
    public Long count(P6eJurisdictionDb paramDb);
}
