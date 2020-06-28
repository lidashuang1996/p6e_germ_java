package com.p6e.germ.oauth2.service.impl;

import com.p6e.germ.oauth2.cache.IP6eCacheToken;
import com.p6e.germ.oauth2.model.dto.P6eTokenParamDto;
import com.p6e.germ.oauth2.model.dto.P6eTokenResultDto;
import com.p6e.germ.oauth2.service.P6eTokenService;

import javax.annotation.Resource;

/**
 * @author lidashuang
 * @version 1.0
 */
public class P6eTokenServiceImpl implements P6eTokenService {

    @Resource
    private IP6eCacheToken p6eCacheToken;

    @Override
    public P6eTokenResultDto set(P6eTokenParamDto param) {
        return null;
    }

    @Override
    public P6eTokenResultDto get(P6eTokenParamDto param) {
        return null;
    }

    @Override
    public P6eTokenResultDto refresh(P6eTokenParamDto param) {
        return null;
    }
}
