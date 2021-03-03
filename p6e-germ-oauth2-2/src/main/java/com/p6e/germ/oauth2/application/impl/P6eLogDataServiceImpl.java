package com.p6e.germ.oauth2.application.impl;

import com.p6e.germ.oauth2.application.P6eLogDataService;
import com.p6e.germ.oauth2.application.P6eLoginService;
import com.p6e.germ.oauth2.domain.entity.P6eLogEntity;
import com.p6e.germ.oauth2.model.db.P6eOauth2LogDb;
import com.p6e.germ.common.utils.P6eCopyUtil;
import com.p6e.germ.oauth2.model.P6eModel;
import com.p6e.germ.oauth2.model.dto.P6eLogDataDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author lidashuang
 * @version 1.0
 */
public class P6eLogDataServiceImpl implements P6eLogDataService {

    /** 注入日志系统 */
    private final static Logger LOGGER = LoggerFactory.getLogger(P6eLoginService.class);

    @Override
    public P6eLogDataDto get(P6eLogDataDto param) {
        final P6eLogDataDto p6eLogDataDto = new P6eLogDataDto();
        try {
            // 查询一条数据
            P6eCopyUtil.run(new P6eLogEntity(param.getId()).get(), p6eLogDataDto);
        } catch (RuntimeException e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            p6eLogDataDto.setError(P6eModel.Error.PARAMETER_EXCEPTION);
        } catch (Exception ee) {
            LOGGER.error(ee.getMessage());
            p6eLogDataDto.setError(P6eModel.Error.SERVICE_EXCEPTION);
        }
        return p6eLogDataDto;
    }

    @Override
    public P6eLogDataDto select(P6eLogDataDto param) {
        final P6eLogDataDto p6eLogDataDto = new P6eLogDataDto();
        try {
            // 查询多条数据
            final P6eLogEntity p6eLogEntity = new P6eLogEntity(P6eCopyUtil.run(param, P6eOauth2LogDb.class));
            // 写入总长度数据
            p6eLogDataDto.setTotal(p6eLogEntity.count());
            // 初始化分页数据
            p6eLogDataDto.initPaging(p6eLogEntity.get());
            // 写入查询的内容数据
            p6eLogDataDto.setList(P6eCopyUtil.runList(p6eLogEntity.select(), P6eLogDataDto.class));
        } catch (RuntimeException e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            p6eLogDataDto.setError(P6eModel.Error.PARAMETER_EXCEPTION);
        } catch (Exception ee) {
            LOGGER.error(ee.getMessage());
            p6eLogDataDto.setError(P6eModel.Error.SERVICE_EXCEPTION);
        }
        return p6eLogDataDto;
    }
}
