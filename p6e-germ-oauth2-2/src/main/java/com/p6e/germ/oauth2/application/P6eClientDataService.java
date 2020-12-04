package com.p6e.germ.oauth2.application;

import com.p6e.germ.oauth2.model.dto.P6eClientDataDto;

/**
 * 客户端数据服务
 * @author lidashuang
 * @version 1.0
 */
public interface P6eClientDataService {

    /**
     * 创建一个客户端
     * @param param 创建参数
     * @return 创建结果
     */
    public P6eClientDataDto create(P6eClientDataDto param);

    /**
     * 删除一个客户端
     * @param param 删除参数
     * @return 删除结果
     */
    public P6eClientDataDto delete(P6eClientDataDto param);

    /**
     * 修改一个客户端
     * @param param 修改参数
     * @return 修改结果
     */
    public P6eClientDataDto update(P6eClientDataDto param);

    /**
     * 查询一个客户端
     * @param param 查询参数
     * @return 查询结果
     */
    public P6eClientDataDto get(P6eClientDataDto param);

    /**
     * 查询客户端数据
     * @param param 查询参数
     * @return 查询结果
     */
    public P6eClientDataDto select(P6eClientDataDto param);

}
