package com.p6e.germ.oauth2.service.impl;

import com.p6e.germ.oauth2.mapper.P6eOauth2AdminUserMapper;
import com.p6e.germ.oauth2.model.db.P6eOauth2AdminUserDb;
import com.p6e.germ.oauth2.model.dto.P6eAdminSignParamDto;
import com.p6e.germ.oauth2.model.dto.P6eAdminSignResultDto;
import com.p6e.germ.oauth2.service.P6eAdminSignService;
import com.p6e.germ.oauth2.utils.CopyUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 管理员账号 登录、注册、退出登录、刷新 的实现
 * @author lidashuang
 * @version 1.0
 */
@Service
public class P6eAdminSignServiceImpl implements P6eAdminSignService {

    @Resource
    private P6eOauth2AdminUserMapper adminUserMapper;

    @Override
    public P6eAdminSignResultDto in(P6eAdminSignParamDto param) {
        P6eOauth2AdminUserDb db = adminUserMapper.select(CopyUtil.run(param, P6eOauth2AdminUserDb.class));

        return null;
    }

    @Override
    public P6eAdminSignResultDto up(P6eAdminSignParamDto param) {
        return null;
    }

    @Override
    public P6eAdminSignResultDto out(P6eAdminSignParamDto param) {
        return null;
    }

    @Override
    public P6eAdminSignResultDto refresh(P6eAdminSignParamDto param) {
        return null;
    }

}
