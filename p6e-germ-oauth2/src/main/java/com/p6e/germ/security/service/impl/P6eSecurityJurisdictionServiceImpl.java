package com.p6e.germ.security.service.impl;

import com.p6e.germ.oauth2.utils.CopyUtil;
import com.p6e.germ.security.mapper.P6eSecurityJurisdictionMapper;
import com.p6e.germ.security.model.db.P6eSecurityJurisdictionDb;
import com.p6e.germ.security.model.dto.*;
import com.p6e.germ.security.service.P6eSecurityJurisdictionRelationGroupService;
import com.p6e.germ.security.service.P6eSecurityJurisdictionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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

    @Resource
    private P6eSecurityJurisdictionRelationGroupService securityJurisdictionRelationGroupService;

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
        final P6eSecurityJurisdictionResultDto p6eSecurityJurisdictionResultDto = new P6eSecurityJurisdictionResultDto();
        final P6eSecurityJurisdictionDb paramDb = CopyUtil.run(param, P6eSecurityJurisdictionDb.class);
        final P6eSecurityJurisdictionDb resultDb = securityJurisdictionMapper.selectOneData(paramDb);
        // 判断是否存在删除的数据资源
        if (resultDb != null) {
            final P6eListResultDto<P6eSecurityJurisdictionRelationGroupResultDto>
                    p6eListResultDto = securityJurisdictionRelationGroupService.select(
                            new P6eSecurityJurisdictionRelationGroupParamDto().setJurisdictionId(resultDb.getId()));
            // 判断是否存在关联的数据
            if (p6eListResultDto.getTotal() == 0) {
                // 判断是否删除成功
                if (securityJurisdictionMapper.delete(paramDb) > 0) {
                    CopyUtil.run(resultDb, p6eSecurityJurisdictionResultDto);
                } else {
                    p6eSecurityJurisdictionResultDto.setError("ERROR_SERVICE_INSIDE");
                }
            } else {
                p6eSecurityJurisdictionResultDto.setError("ERROR_RESOURCES_EXISTENCE_RELATION_DATA");
            }
        } else {
            p6eSecurityJurisdictionResultDto.setError("ERROR_RESOURCES_NO_EXIST");
        }
        return p6eSecurityJurisdictionResultDto;
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
