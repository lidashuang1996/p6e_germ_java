package com.p6e.germ.oauth2.service.impl;

import com.p6e.germ.oauth2.mapper.P6eLogMapper;
import com.p6e.germ.oauth2.model.db.P6eLogDb;
import com.p6e.germ.oauth2.model.dto.P6eLogParamDto;
import com.p6e.germ.oauth2.model.dto.P6eLogResultDto;
import com.p6e.germ.oauth2.service.P6eLogService;
import com.p6e.germ.oauth2.utils.CopyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 日志服务的实现
 * @author lidashuang
 * @version 1.0
 */
@Service
public class P6eLogServiceImpl implements P6eLogService {

    /**
     * 注入日志对象
     */
    private static final Logger logger = LoggerFactory.getLogger(P6eLogService.class);

    public static final String UID_TO_CID = "UID_TO_CID";
    public static final String CID_TO_UID = "CID_TO_UID";

    /**
     * 注入日志 DB 的服务
     */
    @Resource
    private P6eLogMapper p6eLogMapper;

    @Override
    public P6eLogResultDto create(final P6eLogParamDto param) {
        final P6eLogDb p6eLogDb = CopyUtil.run(param, P6eLogDb.class);
        P6eLogResultDto p6eLogResultDto = new P6eLogResultDto();
        if (p6eLogMapper.create(p6eLogDb) > 0) {
            p6eLogResultDto = CopyUtil.run(p6eLogDb, P6eLogResultDto.class);
        } else {
            logger.error("[ P6E LOG SERVICE ] CREATE ==> ERROR.");
        }
        return p6eLogResultDto;
    }
}
