package com.p6e.germ.oauth2.service.impl;

import com.p6e.germ.oauth2.mapper.P6eOauth2UserMapper;
import com.p6e.germ.oauth2.model.db.P6eOauth2UserDb;
import com.p6e.germ.oauth2.model.dto.P6eUserParamDto;
import com.p6e.germ.oauth2.model.dto.P6eUserResultDto;
import com.p6e.germ.oauth2.service.P6eUserService;
import com.p6e.germ.oauth2.utils.CopyUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 查询服务的实现
 * @author lidashuang
 * @version 1.0
 */
@Service
public class P6eUserServiceImpl implements P6eUserService {

    /**
     * 注入 DB 对象
     */
    @Resource
    private P6eOauth2UserMapper p6eUserMapper;

    @Override
    public P6eUserResultDto select(P6eUserParamDto param) {
        final P6eUserResultDto p6eUserResultDto = new P6eUserResultDto();
        final P6eOauth2UserDb p6eOauth2UserDb = p6eUserMapper.selectById(CopyUtil.run(param, P6eOauth2UserDb.class));
        if (p6eOauth2UserDb == null) {
            // 查询的用户信息不存在
            p6eUserResultDto.setError("ERROR_USER_ID_NOT_EXISTENCE");
        } else {
            CopyUtil.run(p6eOauth2UserDb, p6eUserResultDto);
        }
        return p6eUserResultDto;
    }

}
