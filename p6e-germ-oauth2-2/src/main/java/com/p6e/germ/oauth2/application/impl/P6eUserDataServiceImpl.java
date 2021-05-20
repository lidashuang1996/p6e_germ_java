package com.p6e.germ.oauth2.application.impl;

import com.p6e.germ.common.utils.P6eCopyUtil;
import com.p6e.germ.oauth2.application.P6eUserDataService;
import com.p6e.germ.oauth2.domain.entity.P6eUserEntity;
import com.p6e.germ.oauth2.model.P6eResultModel;
import com.p6e.germ.oauth2.model.db.P6eOauth2UserDb;
import com.p6e.germ.oauth2.model.dto.P6eUserDataDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author lidashuang
 * @version 1.0
 */
public class P6eUserDataServiceImpl implements P6eUserDataService {

    /** 注入日志系统 */
    private final static Logger LOGGER = LoggerFactory.getLogger(P6eUserDataService.class);

    @Override
    public P6eUserDataDto get(P6eUserDataDto param) {
        final P6eUserDataDto p6eUserDataDto = new P6eUserDataDto();
        try {
            // 查询一条数据
            P6eCopyUtil.run(new P6eUserEntity(param.getId()).get(), p6eUserDataDto);
        } catch (RuntimeException e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            p6eUserDataDto.setError(P6eResultModel.Error.PARAMETER_EXCEPTION);
        } catch (Exception ee) {
            LOGGER.error(ee.getMessage());
            p6eUserDataDto.setError(P6eResultModel.Error.SERVICE_EXCEPTION);
        }
        return p6eUserDataDto;
    }

    @Override
    public P6eUserDataDto update(P6eUserDataDto param) {
        final P6eUserDataDto p6eUserDataDto = new P6eUserDataDto();
        try {
            // 查询一条数据
            P6eCopyUtil.run(new P6eUserEntity(P6eCopyUtil.run(param, P6eOauth2UserDb.class)).update(), p6eUserDataDto);
        } catch (RuntimeException e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            p6eUserDataDto.setError(P6eResultModel.Error.PARAMETER_EXCEPTION);
        } catch (Exception ee) {
            LOGGER.error(ee.getMessage());
            p6eUserDataDto.setError(P6eResultModel.Error.SERVICE_EXCEPTION);
        }
        return p6eUserDataDto;
    }

    @Override
    public P6eUserDataDto delete(P6eUserDataDto param) {
        final P6eUserDataDto p6eUserDataDto = new P6eUserDataDto();
        try {
            // 删除一条数据
            P6eCopyUtil.run(new P6eUserEntity(param.getId()).delete(), p6eUserDataDto);
        } catch (RuntimeException e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            p6eUserDataDto.setError(P6eResultModel.Error.PARAMETER_EXCEPTION);
        } catch (Exception ee) {
            LOGGER.error(ee.getMessage());
            p6eUserDataDto.setError(P6eResultModel.Error.SERVICE_EXCEPTION);
        }
        return p6eUserDataDto;
    }

    @Override
    public P6eUserDataDto select(P6eUserDataDto param) {
        final P6eUserDataDto p6eUserDataDto = new P6eUserDataDto();
        try {
            final P6eUserEntity p6eUserEntity = new P6eUserEntity(P6eCopyUtil.run(param, P6eOauth2UserDb.class));
            p6eUserDataDto.setTotal(p6eUserEntity.count());
            p6eUserDataDto.initPaging(p6eUserEntity.get());
            p6eUserDataDto.setList(P6eCopyUtil.runList(p6eUserEntity.select(), P6eUserDataDto.class));
        } catch (RuntimeException e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            p6eUserDataDto.setError(P6eResultModel.Error.PARAMETER_EXCEPTION);
        } catch (Exception ee) {
            LOGGER.error(ee.getMessage());
            p6eUserDataDto.setError(P6eResultModel.Error.SERVICE_EXCEPTION);
        }
        return p6eUserDataDto;
    }
}
