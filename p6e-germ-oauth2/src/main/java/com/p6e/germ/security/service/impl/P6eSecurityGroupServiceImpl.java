package com.p6e.germ.security.service.impl;

import com.p6e.germ.oauth2.utils.CopyUtil;
import com.p6e.germ.security.mapper.P6eSecurityGroupMapper;
import com.p6e.germ.security.model.db.P6eSecurityGroupDb;
import com.p6e.germ.security.model.dto.P6eListResultDto;
import com.p6e.germ.security.model.dto.P6eSecurityGroupParamDto;
import com.p6e.germ.security.model.dto.P6eSecurityGroupResultDto;
import com.p6e.germ.security.service.P6eSecurityGroupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lidashuang
 * @version 1.0
 */
@Service
public class P6eSecurityGroupServiceImpl implements P6eSecurityGroupService {

    private static final Logger LOGGER = LoggerFactory.getLogger(P6eSecurityGroupService.class);

    @Resource
    private P6eSecurityGroupMapper securityGroupMapper;

    @Override
    public P6eSecurityGroupResultDto create(P6eSecurityGroupParamDto param) {
        final P6eSecurityGroupDb paramDb = CopyUtil.run(param, P6eSecurityGroupDb.class);
        if (securityGroupMapper.create(paramDb) > 0) {
            return CopyUtil.run(securityGroupMapper.selectOneData(paramDb), P6eSecurityGroupResultDto.class);
        } else {
            return null;
        }
    }

    @Override
    public P6eSecurityGroupResultDto update(P6eSecurityGroupParamDto param) {
        final P6eSecurityGroupDb paramDb = CopyUtil.run(param, P6eSecurityGroupDb.class);
        if (securityGroupMapper.update(paramDb) > 0) {
            return CopyUtil.run(securityGroupMapper.selectOneData(paramDb), P6eSecurityGroupResultDto.class);
        } else {
            return null;
        }
    }

    @Override
    public P6eSecurityGroupResultDto delete(P6eSecurityGroupParamDto param) {
        final P6eSecurityGroupDb paramDb = CopyUtil.run(param, P6eSecurityGroupDb.class);
        final P6eSecurityGroupDb resultDb = securityGroupMapper.selectOneData(paramDb);
        if (resultDb != null && securityGroupMapper.delete(paramDb) > 0) {
            return CopyUtil.run(resultDb, P6eSecurityGroupResultDto.class);
        } else {
            return null;
        }
    }

    @Override
    public List<P6eSecurityGroupResultDto> clean() {
        int page = 1;
        final List<P6eSecurityGroupResultDto> resultDtoList = new ArrayList<>();
        final P6eSecurityGroupDb p6eSecurityGroupDb = new P6eSecurityGroupDb();
        p6eSecurityGroupDb.setSize(200);
        p6eSecurityGroupDb.setPage(page);
        List<P6eSecurityGroupDb> p6eSecurityGroupDbList = securityGroupMapper.select(p6eSecurityGroupDb);
        while (p6eSecurityGroupDbList != null && p6eSecurityGroupDbList.size() > 0) {
            resultDtoList.addAll(CopyUtil.run(p6eSecurityGroupDbList, P6eSecurityGroupResultDto.class));
            p6eSecurityGroupDb.setSize(200);
            p6eSecurityGroupDb.setPage(++page);
            p6eSecurityGroupDbList = securityGroupMapper.select(p6eSecurityGroupDb);
        }
        LOGGER.debug("clean ==> 1: " + securityGroupMapper.clean() + ", 2: " + resultDtoList.size());
        return resultDtoList;
    }

    @Override
    public P6eListResultDto<P6eSecurityGroupResultDto> select(P6eSecurityGroupParamDto param) {
        final P6eListResultDto<P6eSecurityGroupResultDto> p6eListResultDto = new P6eListResultDto<>();
        final P6eSecurityGroupDb paramDb = CopyUtil.run(param, P6eSecurityGroupDb.class);
        final List<P6eSecurityGroupDb> p6eSecurityGroupDbList = securityGroupMapper.select(paramDb);
        p6eListResultDto.setSize(paramDb.getSize());
        p6eListResultDto.setPage((paramDb.getPage() / paramDb.getSize()) + 1);
        p6eListResultDto.setTotal(securityGroupMapper.count(paramDb));
        p6eListResultDto.setList(CopyUtil.run(p6eSecurityGroupDbList, P6eSecurityGroupResultDto.class));
        return p6eListResultDto;
    }

    @Override
    public P6eSecurityGroupResultDto selectOneData(P6eSecurityGroupParamDto param) {
        return CopyUtil.run(securityGroupMapper.selectOneData(
                CopyUtil.run(param, P6eSecurityGroupDb.class)), P6eSecurityGroupResultDto.class);
    }
}
