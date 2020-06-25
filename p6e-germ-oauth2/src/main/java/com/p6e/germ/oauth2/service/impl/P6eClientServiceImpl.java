package com.p6e.germ.oauth2.service.impl;

import com.p6e.germ.oauth2.cache.IP6eCacheClient;
import com.p6e.germ.oauth2.mapper.P6eClientMapper;
import com.p6e.germ.oauth2.model.db.P6eClientDb;
import com.p6e.germ.oauth2.model.dto.P6eClientParamDto;
import com.p6e.germ.oauth2.model.dto.P6eClientResultDto;
import com.p6e.germ.oauth2.service.P6eClientService;
import com.p6e.germ.oauth2.utils.CopyUtil;
import com.p6e.germ.oauth2.utils.GsonUtil;

import javax.annotation.Resource;

/**
 * @author lidashuang
 * @version 1.0
 */
public class P6eClientServiceImpl implements P6eClientService {

    /**
     * 注入 DB 的服务
     */
    @Resource
    private P6eClientMapper p6eClientMapper;

    /**
     * 注入缓存的服务
     */
    @Resource
    private IP6eCacheClient p6eCacheClient;

    @Override
    public P6eClientResultDto selectOne(final P6eClientParamDto param) {
        final String clientId = param.getClientId();
        final String clientJson = p6eCacheClient.get(clientId);
        if (clientJson == null) {
            P6eClientDb p6eClientDb = p6eClientMapper.selectOne(CopyUtil.run(param, P6eClientDb.class));
            if (p6eClientDb == null) {
                // 请求量很大可以考虑采用布隆过滤器
                return null;
            } else {
                // 添加到缓存中
                p6eCacheClient.set(clientId, GsonUtil.toJson(p6eClientDb));
                // 返回查询出来的数据
                return CopyUtil.run(p6eClientDb, P6eClientResultDto.class);
            }
        } else {
            return GsonUtil.fromJson(clientJson, P6eClientResultDto.class);
        }
    }
}
