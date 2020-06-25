package com.p6e.germ.oauth2.service.impl;

import com.p6e.germ.oauth2.cache.IP6eCacheSign;
import com.p6e.germ.oauth2.cache.P6eCacheRedisSignModel;
import com.p6e.germ.oauth2.mapper.P6eOauth2UserMapper;
import com.p6e.germ.oauth2.model.db.P6eOauth2UserDb;
import com.p6e.germ.oauth2.model.dto.P6eSignParamDto;
import com.p6e.germ.oauth2.model.dto.P6eSignResultDto;
import com.p6e.germ.oauth2.service.P6eSignService;
import com.p6e.germ.oauth2.utils.CopyUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 正常登录服务的实现
 * @author lidashuang
 * @version 1.0
 */
@Service
public class P6eSignServiceImpl implements P6eSignService {

    /**
     * 注入认证登录的缓存
     */
//    @Resource
    private IP6eCacheSign cacheSign;

    /**
     * 注入认证登录的数据库查询对象
     */
    @Resource
    private P6eOauth2UserMapper oauth2UserMapper;

    @Override
    public P6eSignResultDto in(final P6eSignParamDto param) {
        P6eSignResultDto p6eSignResultDto = new P6eSignResultDto();
        P6eOauth2UserDb p6eOauth2UserDb = oauth2UserMapper.select(CopyUtil.run(param, P6eOauth2UserDb.class));
        if (p6eOauth2UserDb == null) {
            p6eSignResultDto.setError("ERROR_ACCOUNT_OR_PASSWORD");
        } else {
            P6eCacheRedisSignModel.Core core = cacheSign.set(String.valueOf(p6eOauth2UserDb.getId()), p6eOauth2UserDb);
            p6eSignResultDto = CopyUtil.run(p6eOauth2UserDb, P6eSignResultDto.class);
            p6eSignResultDto.setToken(core.getToken());
            p6eSignResultDto.setRefreshToken(core.getRefreshToken());
            p6eSignResultDto.setExpiration(core.getExpiration());
        }
        return p6eSignResultDto;
    }

    @Override
    public P6eSignResultDto get(P6eSignParamDto param) {
        P6eSignResultDto p6eSignResultDto = new P6eSignResultDto();
        P6eCacheRedisSignModel.Core core = cacheSign.get(param.getToken());
        p6eSignResultDto.setToken(core.getToken());
        p6eSignResultDto.setRefreshToken(core.getRefreshToken());
        p6eSignResultDto.setExpiration(core.getExpiration());
        return p6eSignResultDto;
    }

    @Override
    public P6eSignResultDto refresh(final P6eSignParamDto param) {
        P6eSignResultDto p6eSignResultDto = new P6eSignResultDto();
        P6eCacheRedisSignModel.Core delCore = cacheSign.del(param.getToken());
        P6eCacheRedisSignModel.Core setCore = cacheSign.set("delCore.getId()", delCore.getData());
        p6eSignResultDto.setToken(setCore.getToken());
        p6eSignResultDto.setRefreshToken(setCore.getRefreshToken());
        p6eSignResultDto.setExpiration(setCore.getExpiration());
        return p6eSignResultDto;
    }

    @Override
    public P6eSignResultDto out(final P6eSignParamDto param) {
        P6eSignResultDto p6eSignResultDto = new P6eSignResultDto();
        P6eCacheRedisSignModel.Core core = cacheSign.del(param.getToken());
        p6eSignResultDto.setToken(core.getToken());
        p6eSignResultDto.setRefreshToken(core.getRefreshToken());
        p6eSignResultDto.setExpiration(core.getExpiration());
        return p6eSignResultDto;
    }
}
