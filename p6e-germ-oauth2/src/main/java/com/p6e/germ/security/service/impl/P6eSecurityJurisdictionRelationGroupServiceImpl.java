package com.p6e.germ.security.service.impl;

import com.p6e.germ.oauth2.utils.CopyUtil;
import com.p6e.germ.security.mapper.P6eSecurityJurisdictionRelationGroupMapper;
import com.p6e.germ.security.model.db.P6eSecurityJurisdictionRelationGroupDb;
import com.p6e.germ.security.model.dto.P6eListResultDto;
import com.p6e.germ.security.model.dto.P6eSecurityJurisdictionRelationGroupParamDto;
import com.p6e.germ.security.model.dto.P6eSecurityJurisdictionRelationGroupResultDto;
import com.p6e.germ.security.service.P6eSecurityGroupRelationUserService;
import com.p6e.germ.security.service.P6eSecurityJurisdictionRelationGroupService;
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
public class P6eSecurityJurisdictionRelationGroupServiceImpl implements P6eSecurityJurisdictionRelationGroupService {
    private final static Logger LOGGER = LoggerFactory.getLogger(P6eSecurityGroupRelationUserService.class);

    @Resource
    private P6eSecurityJurisdictionRelationGroupMapper securityJurisdictionRelationGroupMapper;

    @Override
    public P6eListResultDto<P6eSecurityJurisdictionRelationGroupResultDto>
                            select(P6eSecurityJurisdictionRelationGroupParamDto param) {
        final P6eSecurityJurisdictionRelationGroupDb paramDb =
                CopyUtil.run(param, P6eSecurityJurisdictionRelationGroupDb.class);
        final P6eListResultDto<P6eSecurityJurisdictionRelationGroupResultDto> p6eListResultDto = new P6eListResultDto<>();
        final List<P6eSecurityJurisdictionRelationGroupDb> p6eSecurityJurisdictionRelationGroupDbList =
                securityJurisdictionRelationGroupMapper.select(paramDb);
        p6eListResultDto.setPage((paramDb.getPage() / paramDb.getSize()) + 1);
        p6eListResultDto.setSize(paramDb.getSize());
        p6eListResultDto.setTotal(securityJurisdictionRelationGroupMapper.count(paramDb));
        p6eListResultDto.setList(CopyUtil.run(p6eSecurityJurisdictionRelationGroupDbList,
                P6eSecurityJurisdictionRelationGroupResultDto.class));
        return p6eListResultDto;
    }

    @Override
    public List<P6eSecurityJurisdictionRelationGroupResultDto>
                            delete(P6eSecurityJurisdictionRelationGroupParamDto param) {
        int page = 1;
        final List<P6eSecurityJurisdictionRelationGroupResultDto> resultDtoList = new ArrayList<>();
        final P6eSecurityJurisdictionRelationGroupDb paramDb =
                CopyUtil.run(param, P6eSecurityJurisdictionRelationGroupDb.class);
        paramDb.setSize(200);
        paramDb.setPage(page);
        List<P6eSecurityJurisdictionRelationGroupDb> p6eSecurityJurisdictionRelationGroupDbList =
                securityJurisdictionRelationGroupMapper.select(paramDb);
        while (p6eSecurityJurisdictionRelationGroupDbList != null
                && p6eSecurityJurisdictionRelationGroupDbList.size() > 0) {
            resultDtoList.addAll(CopyUtil.run(
                    p6eSecurityJurisdictionRelationGroupDbList, P6eSecurityJurisdictionRelationGroupResultDto.class));
            paramDb.setSize(200);
            paramDb.setPage(++page);
            p6eSecurityJurisdictionRelationGroupDbList = securityJurisdictionRelationGroupMapper.select(paramDb);
        }
        if (paramDb.getGid() != null) {
            LOGGER.debug("delete ==> 1: " + securityJurisdictionRelationGroupMapper.delete(paramDb)
                    + ", 2: " + resultDtoList.size());
        }
        return resultDtoList;
    }
}
