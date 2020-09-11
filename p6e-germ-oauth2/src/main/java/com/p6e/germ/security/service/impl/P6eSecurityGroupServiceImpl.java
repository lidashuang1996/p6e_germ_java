package com.p6e.germ.security.service.impl;

import com.p6e.germ.oauth2.utils.CopyUtil;
import com.p6e.germ.security.mapper.P6eSecurityGroupMapper;
import com.p6e.germ.security.model.db.P6eSecurityGroupDb;
import com.p6e.germ.security.model.dto.*;
import com.p6e.germ.security.service.P6eSecurityGroupRelationUserService;
import com.p6e.germ.security.service.P6eSecurityGroupService;
import com.p6e.germ.security.service.P6eSecurityJurisdictionRelationGroupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
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

    @Resource
    private P6eSecurityGroupRelationUserService securityGroupRelationUserService;

    @Resource
    private P6eSecurityJurisdictionRelationGroupService securityJurisdictionRelationGroupService;

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
    @Transactional(rollbackFor = Exception.class)
    public P6eSecurityGroupResultDto delete(P6eSecurityGroupParamDto param) {
        final P6eSecurityGroupResultDto p6eSecurityGroupResultDto = new P6eSecurityGroupResultDto();
        final P6eSecurityGroupDb paramDb = CopyUtil.run(param, P6eSecurityGroupDb.class);
        final P6eSecurityGroupDb resultDb = securityGroupMapper.selectOneData(paramDb);
        // 判断是否存在删除的数据资源
        if (resultDb != null) {
            final P6eListResultDto<P6eSecurityGroupRelationUserResultDto>
                    p6eListResultDto = securityGroupRelationUserService.select(
                            new P6eSecurityGroupRelationUserParamDto().setGid(resultDb.getId()));
            // 判断是否存在关联的数据
            if (p6eListResultDto.getTotal() == 0) {
                // 判断是否删除成功
                final List<P6eSecurityJurisdictionRelationGroupResultDto> p6eSecurityJurisdictionRelationGroupResultDtoList =
                        securityJurisdictionRelationGroupService.delete(
                                new P6eSecurityJurisdictionRelationGroupParamDto().setGid(resultDb.getId()));
                if (p6eSecurityJurisdictionRelationGroupResultDtoList != null
                        && securityGroupMapper.delete(paramDb) > 0) {
                    CopyUtil.run(resultDb, p6eSecurityGroupResultDto);
                } else {
                    p6eSecurityGroupResultDto.setError("ERROR_SERVICE_INSIDE");
                }
            } else {
                p6eSecurityGroupResultDto.setError("ERROR_RESOURCES_EXISTENCE_RELATION_DATA");
            }
        } else {
            p6eSecurityGroupResultDto.setError("ERROR_RESOURCES_NO_EXIST");
        }
        return p6eSecurityGroupResultDto;
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
