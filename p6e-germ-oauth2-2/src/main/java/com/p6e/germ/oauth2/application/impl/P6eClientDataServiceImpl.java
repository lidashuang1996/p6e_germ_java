package com.p6e.germ.oauth2.application.impl;

import com.p6e.germ.oauth2.application.P6eAuthService;
import com.p6e.germ.oauth2.application.P6eClientDataService;
import com.p6e.germ.oauth2.domain.entity.P6eClientEntity;
import com.p6e.germ.oauth2.model.db.P6eOauth2ClientDb;
import com.p6e.germ.common.utils.P6eCopyUtil;
import com.p6e.germ.oauth2.model.P6eResultModel;
import com.p6e.germ.oauth2.model.dto.P6eClientDataDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author lidashuang
 * @version 1.0
 */
public class P6eClientDataServiceImpl implements P6eClientDataService {

    /** 注入日志系统 */
    private final static Logger LOGGER = LoggerFactory.getLogger(P6eAuthService.class);

    @Override
    public P6eClientDataDto create(P6eClientDataDto param) {
        return null;
    }

    @Override
    public P6eClientDataDto delete(P6eClientDataDto param) {
        return null;
    }

    @Override
    public P6eClientDataDto update(P6eClientDataDto param) {
        return null;
    }

    @Override
    public P6eClientDataDto get(P6eClientDataDto param) {
        return null;
    }

    @Override
    public P6eClientDataDto select(P6eClientDataDto param) {
        return null;
    }
}
