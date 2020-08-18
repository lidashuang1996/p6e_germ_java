package com.p6e.germ.oauth2.service.impl;

import com.p6e.germ.oauth2.mapper.P6eAdminClientMapper;
import com.p6e.germ.oauth2.model.db.P6eAdminUserDb;
import com.p6e.germ.oauth2.model.dto.P6eAdminUserClientParamDto;
import com.p6e.germ.oauth2.model.dto.P6eAdminUserClientResultDto;
import com.p6e.germ.oauth2.model.dto.P6eListResultDto;
import com.p6e.germ.oauth2.service.P6eAdminUserClientService;
import com.p6e.germ.oauth2.utils.CopyUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 用户客户端服务
 * @author lidashuang
 * @version 1.0
 */
@Service
public class P6eAdminUserClientServiceImpl implements P6eAdminUserClientService {

    @Resource
    private P6eAdminClientMapper p6eAdminClientMapper;

    @Override
    public P6eAdminUserClientResultDto get(final P6eAdminUserClientParamDto param) {
        final P6eAdminUserDb p6eAdminUserDb
                = p6eAdminClientMapper.selectById(CopyUtil.run(param, P6eAdminUserDb.class));
        return CopyUtil.run(p6eAdminUserDb, P6eAdminUserClientResultDto.class);
    }

    @Override
    public P6eListResultDto<P6eAdminUserClientResultDto> list(P6eAdminUserClientParamDto param) {
        return null;
    }

    @Override
    public P6eAdminUserClientResultDto create(P6eAdminUserClientParamDto param) {
        return null;
    }

    @Override
    public P6eAdminUserClientResultDto delete(P6eAdminUserClientParamDto param) {
        return null;
    }

    @Override
    public P6eAdminUserClientResultDto update(P6eAdminUserClientParamDto param) {
        return null;
    }
}
