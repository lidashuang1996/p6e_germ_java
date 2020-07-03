package com.p6e.germ.oauth2.service.impl;

import com.p6e.germ.oauth2.mapper.P6eAdminUserMapper;
import com.p6e.germ.oauth2.model.db.P6eAdminUserDb;
import com.p6e.germ.oauth2.model.dto.P6eAdminUserManageParamDto;
import com.p6e.germ.oauth2.model.dto.P6eAdminUserManageResultDto;
import com.p6e.germ.oauth2.model.dto.P6eListResultDto;
import com.p6e.germ.oauth2.mybatis.MyBatisTool;
import com.p6e.germ.oauth2.service.P6eAdminUserManageService;
import com.p6e.germ.oauth2.utils.CopyUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author lidashuang
 * @version 1.0
 */
@Service
public class P6eAdminUserManageServiceImpl implements P6eAdminUserManageService {

    /**
     * 注入 DB 操作对象
     */
    @Resource
    private P6eAdminUserMapper p6eAdminUserMapper;

    @Override
    public P6eAdminUserManageResultDto get(final P6eAdminUserManageParamDto param) {
        return CopyUtil.run(p6eAdminUserMapper.selectById(CopyUtil.run(
                param, P6eAdminUserDb.class)), P6eAdminUserManageResultDto.class);
    }

    @Override
    public P6eListResultDto<P6eAdminUserManageResultDto> list(final P6eAdminUserManageParamDto param) {
        final P6eAdminUserDb p6eAdminUserDb = CopyUtil.run(param, P6eAdminUserDb.class);
        final Long total = p6eAdminUserMapper.count(p6eAdminUserDb);
        final List<P6eAdminUserDb> p6eAdminUserDbList = p6eAdminUserMapper.select(p6eAdminUserDb);
        final P6eListResultDto<P6eAdminUserManageResultDto> p6eListResultDto = new P6eListResultDto<>();
        final int[] pagingData = MyBatisTool.obtainPagingData(p6eAdminUserDb);
        p6eListResultDto.setTotal(total);
        p6eListResultDto.setPage(pagingData[0]);
        p6eListResultDto.setSize(pagingData[1]);
        p6eListResultDto.setList(CopyUtil.run(p6eAdminUserDbList, P6eAdminUserManageResultDto.class));
        return p6eListResultDto;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public P6eAdminUserManageResultDto create(final P6eAdminUserManageParamDto param) {
        final P6eAdminUserDb p6eAdminUserDb = CopyUtil.run(param, P6eAdminUserDb.class);
        // 判断账号是邮箱还是手机号码
        if (MyBatisTool.isEmail(p6eAdminUserDb.getAccount())) {
            p6eAdminUserDb.setEmail(p6eAdminUserDb.getAccount());
        }
        if (MyBatisTool.isPhone(p6eAdminUserDb.getAccount())) {
            p6eAdminUserDb.setPhone(p6eAdminUserDb.getAccount());
        }
        final P6eAdminUserDb p6eAdminUserDbResult = p6eAdminUserMapper.selectByAccount(p6eAdminUserDb);
        if (p6eAdminUserDbResult == null
                && p6eAdminUserMapper.createInfo(p6eAdminUserDb) > 0
                && p6eAdminUserMapper.createAuth(p6eAdminUserDb) > 0) {
            return CopyUtil.run(p6eAdminUserMapper.selectById(p6eAdminUserDb), P6eAdminUserManageResultDto.class);
        } else {
            return null;
        }
    }

    @Override
    public P6eAdminUserManageResultDto delete(final P6eAdminUserManageParamDto param) {
        final P6eAdminUserDb p6eAdminUserDb = CopyUtil.run(param, P6eAdminUserDb.class);
        final P6eAdminUserManageResultDto p6eAdminUserManageResultDto =
                CopyUtil.run(p6eAdminUserMapper.selectById(p6eAdminUserDb), P6eAdminUserManageResultDto.class);
        if (p6eAdminUserManageResultDto != null && p6eAdminUserMapper.delete(p6eAdminUserDb) > 0) {
            return p6eAdminUserManageResultDto;
        } else {
            return null;
        }
    }

    @Override
    public P6eAdminUserManageResultDto update(final P6eAdminUserManageParamDto param) {
        final P6eAdminUserDb p6eAdminUserDb = CopyUtil.run(param, P6eAdminUserDb.class);
        if (p6eAdminUserMapper.update(p6eAdminUserDb) > 0) {
            return CopyUtil.run(p6eAdminUserMapper.selectById(p6eAdminUserDb), P6eAdminUserManageResultDto.class);
        } else {
            return null;
        }
    }

}
