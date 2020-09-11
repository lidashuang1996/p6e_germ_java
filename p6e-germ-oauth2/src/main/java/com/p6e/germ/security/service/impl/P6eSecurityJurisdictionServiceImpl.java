package com.p6e.germ.security.service.impl;

import com.p6e.germ.oauth2.utils.CopyUtil;
import com.p6e.germ.security.mapper.P6eSecurityJurisdictionMapper;
import com.p6e.germ.security.model.db.P6eSecurityJurisdictionDb;
import com.p6e.germ.security.model.dto.P6eListResultDto;
import com.p6e.germ.security.model.dto.P6eSecurityJurisdictionParamDto;
import com.p6e.germ.security.model.dto.P6eSecurityJurisdictionResultDto;
import com.p6e.germ.security.service.P6eSecurityJurisdictionService;
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
public class P6eSecurityJurisdictionServiceImpl implements P6eSecurityJurisdictionService {

    private final static Logger LOGGER = LoggerFactory.getLogger(P6eSecurityJurisdictionService.class);

    @Resource
    private P6eSecurityJurisdictionMapper securityJurisdictionMapper;

    @Override
    public P6eSecurityJurisdictionResultDto create(P6eSecurityJurisdictionParamDto param) {
        final P6eSecurityJurisdictionDb paramDb = CopyUtil.run(param, P6eSecurityJurisdictionDb.class);
        if (securityJurisdictionMapper.create(paramDb) > 0) {
            return CopyUtil.run(securityJurisdictionMapper.selectOneData(paramDb), P6eSecurityJurisdictionResultDto.class);
        } else {
            return null;
        }
    }

    @Override
    public P6eSecurityJurisdictionResultDto update(P6eSecurityJurisdictionParamDto param) {
        final P6eSecurityJurisdictionDb paramDb = CopyUtil.run(param, P6eSecurityJurisdictionDb.class);
        if (securityJurisdictionMapper.update(paramDb) > 0) {
            return CopyUtil.run(securityJurisdictionMapper.selectOneData(paramDb), P6eSecurityJurisdictionResultDto.class);
        } else {
            return null;
        }
    }

    @Override
    public P6eSecurityJurisdictionResultDto delete(P6eSecurityJurisdictionParamDto param) {
        final P6eSecurityJurisdictionDb paramDb = CopyUtil.run(param, P6eSecurityJurisdictionDb.class);
        final P6eSecurityJurisdictionDb resultDb = securityJurisdictionMapper.selectOneData(paramDb);
        if (resultDb != null && securityJurisdictionMapper.delete(paramDb) > 0) {
            return CopyUtil.run(resultDb, P6eSecurityJurisdictionResultDto.class);
        } else {
            return null;
        }
    }

    @Override
    public List<P6eSecurityJurisdictionResultDto> clean() {
        int page = 1;
        final List<P6eSecurityJurisdictionResultDto> resultDtoList = new ArrayList<>();
        final P6eSecurityJurisdictionDb p6eSecurityJurisdictionDb = new P6eSecurityJurisdictionDb();
        p6eSecurityJurisdictionDb.setSize(200);
        p6eSecurityJurisdictionDb.setPage(page);
        List<P6eSecurityJurisdictionDb> p6eSecurityJurisdictionDbList = securityJurisdictionMapper.select(p6eSecurityJurisdictionDb);
        while (p6eSecurityJurisdictionDbList != null && p6eSecurityJurisdictionDbList.size() > 0) {
            resultDtoList.addAll(CopyUtil.run(p6eSecurityJurisdictionDbList, P6eSecurityJurisdictionResultDto.class));
            p6eSecurityJurisdictionDb.setSize(200);
            p6eSecurityJurisdictionDb.setPage(++page);
            p6eSecurityJurisdictionDbList = securityJurisdictionMapper.select(p6eSecurityJurisdictionDb);
        }
        LOGGER.debug("clean ==> 1: " + securityJurisdictionMapper.clean() + ", 2: " + resultDtoList.size());
        return resultDtoList;
    }

    @Override
    public P6eListResultDto<P6eSecurityJurisdictionResultDto> select(P6eSecurityJurisdictionParamDto param) {
        final P6eListResultDto<P6eSecurityJurisdictionResultDto> p6eListResultDto = new P6eListResultDto<>();
        final P6eSecurityJurisdictionDb paramDb = CopyUtil.run(param, P6eSecurityJurisdictionDb.class);
        final List<P6eSecurityJurisdictionDb> p6eSecurityJurisdictionDbList = securityJurisdictionMapper.select(paramDb);
        p6eListResultDto.setSize(paramDb.getSize());
        p6eListResultDto.setPage((paramDb.getPage() / paramDb.getSize()) + 1);
        p6eListResultDto.setTotal(securityJurisdictionMapper.count(paramDb));
        p6eListResultDto.setList(CopyUtil.run(p6eSecurityJurisdictionDbList, P6eSecurityJurisdictionResultDto.class));
        return p6eListResultDto;
    }

    @Override
    public P6eSecurityJurisdictionResultDto selectOneData(P6eSecurityJurisdictionParamDto param) {
        return CopyUtil.run(securityJurisdictionMapper.selectOneData(
                CopyUtil.run(param, P6eSecurityJurisdictionDb.class)), P6eSecurityJurisdictionResultDto.class);
    }
}
