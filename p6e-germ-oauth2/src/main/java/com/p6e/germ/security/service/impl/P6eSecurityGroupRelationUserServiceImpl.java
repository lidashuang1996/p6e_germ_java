package com.p6e.germ.security.service.impl;

import com.p6e.germ.oauth2.utils.CopyUtil;
import com.p6e.germ.security.mapper.P6eSecurityGroupRelationUserMapper;
import com.p6e.germ.security.model.db.P6eSecurityGroupRelationUserDb;
import com.p6e.germ.security.model.dto.P6eListResultDto;
import com.p6e.germ.security.model.dto.P6eSecurityGroupRelationUserParamDto;
import com.p6e.germ.security.model.dto.P6eSecurityGroupRelationUserResultDto;
import com.p6e.germ.security.service.P6eSecurityGroupRelationUserService;
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
public class P6eSecurityGroupRelationUserServiceImpl implements P6eSecurityGroupRelationUserService {

    private final static Logger LOGGER = LoggerFactory.getLogger(P6eSecurityGroupRelationUserService.class);

    @Resource
    private P6eSecurityGroupRelationUserMapper securityGroupRelationUserMapper;

    @Override
    public P6eListResultDto<P6eSecurityGroupRelationUserResultDto> select(P6eSecurityGroupRelationUserParamDto param) {
        final P6eSecurityGroupRelationUserDb paramDb = CopyUtil.run(param, P6eSecurityGroupRelationUserDb.class);
        final P6eListResultDto<P6eSecurityGroupRelationUserResultDto> p6eListResultDto = new P6eListResultDto<>();
        final List<P6eSecurityGroupRelationUserDb> p6eSecurityGroupRelationUserDbList =
                securityGroupRelationUserMapper.select(paramDb);
        p6eListResultDto.setPage((paramDb.getPage() / paramDb.getSize()) + 1);
        p6eListResultDto.setSize(paramDb.getSize());
        p6eListResultDto.setTotal(securityGroupRelationUserMapper.count(paramDb));
        p6eListResultDto.setList(
                CopyUtil.run(p6eSecurityGroupRelationUserDbList, P6eSecurityGroupRelationUserResultDto.class));
        return p6eListResultDto;
    }

    @Override
    public List<P6eSecurityGroupRelationUserResultDto> delete(P6eSecurityGroupRelationUserParamDto param) {
        if (param.getUid() == null) {
            // 只能通过 uid 删除数据，不能通过 gid 删除数据
            return null;
        } else {
            // 只能通过 uid 删除数据，不能通过 gid 删除数据
            int page = 1;
            final List<P6eSecurityGroupRelationUserResultDto> resultDtoList = new ArrayList<>();
            final P6eSecurityGroupRelationUserDb paramDb = CopyUtil.run(param, P6eSecurityGroupRelationUserDb.class);
            paramDb.setSize(200);
            paramDb.setPage(page);
            List<P6eSecurityGroupRelationUserDb> p6eSecurityGroupRelationUserDbList =
                    securityGroupRelationUserMapper.select(paramDb);
            while (p6eSecurityGroupRelationUserDbList != null && p6eSecurityGroupRelationUserDbList.size() > 0) {
                resultDtoList.addAll(
                        CopyUtil.run(p6eSecurityGroupRelationUserDbList, P6eSecurityGroupRelationUserResultDto.class));
                paramDb.setSize(200);
                paramDb.setPage(++page);
                p6eSecurityGroupRelationUserDbList = securityGroupRelationUserMapper.select(paramDb);
            }
            LOGGER.debug("delete ==> 1: " + securityGroupRelationUserMapper.delete(paramDb) + ", 2: " + resultDtoList.size());
            return resultDtoList;
        }
    }

}
