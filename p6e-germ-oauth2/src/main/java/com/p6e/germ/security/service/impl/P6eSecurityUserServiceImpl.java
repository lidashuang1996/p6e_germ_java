package com.p6e.germ.security.service.impl;

import com.p6e.germ.oauth2.utils.CopyUtil;
import com.p6e.germ.security.mapper.P6eOauth2UserMapper2;
import com.p6e.germ.security.model.db.P6eOauth2UserDb;
import com.p6e.germ.security.model.dto.P6eListResultDto;
import com.p6e.germ.security.model.dto.P6eSecurityGroupRelationUserParamDto;
import com.p6e.germ.security.model.dto.P6eSecurityUserParamDto;
import com.p6e.germ.security.model.dto.P6eSecurityUserResultDto;
import com.p6e.germ.security.service.P6eSecurityGroupRelationUserService;
import com.p6e.germ.security.service.P6eSecurityUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author lidashuang
 * @version 1.0
 */
@Service
public class P6eSecurityUserServiceImpl implements P6eSecurityUserService {

    @Resource
    private P6eOauth2UserMapper2 oauth2UserMapper;

    @Resource
    private P6eSecurityGroupRelationUserService securityGroupRelationUserService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public P6eSecurityUserResultDto create(P6eSecurityUserParamDto param) {
        final P6eOauth2UserDb paramDb = CopyUtil.run(param, P6eOauth2UserDb.class);
        final P6eOauth2UserDb resultDb =
                oauth2UserMapper.selectOneData(new P6eOauth2UserDb().setAccount(paramDb.getAccount()));
        if (resultDb == null
                && oauth2UserMapper.createUserInfo(paramDb) > 0
                && oauth2UserMapper.createUserAuth(paramDb) > 0){
            return CopyUtil.run(oauth2UserMapper.selectOneData(paramDb), P6eSecurityUserResultDto.class);
        }
        return null;
    }

    @Override
    public P6eSecurityUserResultDto update(P6eSecurityUserParamDto param) {
        final P6eOauth2UserDb paramDb = CopyUtil.run(param, P6eOauth2UserDb.class);
        final P6eOauth2UserDb resultDb =
                oauth2UserMapper.selectOneData(new P6eOauth2UserDb().setAccount(paramDb.getAccount()));
        if (resultDb != null && oauth2UserMapper.updateUserInfo(paramDb) > 0) {
            return CopyUtil.run(oauth2UserMapper.selectOneData(paramDb), P6eSecurityUserResultDto.class);
        }
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public P6eSecurityUserResultDto delete(P6eSecurityUserParamDto param) {
        final P6eOauth2UserDb paramDb = CopyUtil.run(param, P6eOauth2UserDb.class);
        final P6eOauth2UserDb resultDb = oauth2UserMapper.selectOneData(paramDb);
        if (resultDb != null
                && oauth2UserMapper.deleteUserInfo(paramDb) > 0
                && oauth2UserMapper.deleteUserAuth(paramDb) > 0) {
            securityGroupRelationUserService.delete(new P6eSecurityGroupRelationUserParamDto().setUid(paramDb.getId()));
            return CopyUtil.run(resultDb, P6eSecurityUserResultDto.class);
        }
        return null;
    }

    @Override
    public P6eListResultDto<P6eSecurityUserResultDto> select(P6eSecurityUserParamDto param) {
        final P6eListResultDto<P6eSecurityUserResultDto> p6eListResultDto = new P6eListResultDto<>();
        final P6eOauth2UserDb paramDb = CopyUtil.run(param, P6eOauth2UserDb.class);
        final List<P6eOauth2UserDb> p6eOauth2UserDbList = oauth2UserMapper.select(paramDb);
        p6eListResultDto.setSize(paramDb.getSize());
        p6eListResultDto.setPage((paramDb.getPage() / paramDb.getSize()) + 1);
        p6eListResultDto.setTotal(oauth2UserMapper.count(paramDb));
        p6eListResultDto.setList(CopyUtil.run(p6eOauth2UserDbList, P6eSecurityUserResultDto.class));
        return p6eListResultDto;
    }

    @Override
    public P6eSecurityUserResultDto selectOneData(P6eSecurityUserParamDto param) {
        final P6eOauth2UserDb result =
                oauth2UserMapper.selectOneData(CopyUtil.run(param, P6eOauth2UserDb.class));
        return CopyUtil.run(result, P6eSecurityUserResultDto.class);
    }
}
