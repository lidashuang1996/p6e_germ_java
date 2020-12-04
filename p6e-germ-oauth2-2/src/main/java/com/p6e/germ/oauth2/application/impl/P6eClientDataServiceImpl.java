package com.p6e.germ.oauth2.application.impl;

import com.p6e.germ.oauth2.application.P6eAuthService;
import com.p6e.germ.oauth2.application.P6eClientDataService;
import com.p6e.germ.oauth2.domain.entity.P6eClientEntity;
import com.p6e.germ.oauth2.model.db.P6eOauth2ClientDb;
import com.p6e.germ.oauth2.infrastructure.utils.P6eCopyUtil;
import com.p6e.germ.oauth2.model.P6eModel;
import com.p6e.germ.oauth2.model.dto.P6eClientDataDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author lidashuang
 * @version 1.0
 */
public class P6eClientDataServiceImpl implements P6eClientDataService {

    /** 注入日志系统 */
    private final static Logger LOGGER = LoggerFactory.getLogger(P6eAuthService.class);

    @Override
    public P6eClientDataDto create(P6eClientDataDto param) {
        final P6eClientDataDto p6eClientDataDto = new P6eClientDataDto();
        try {
            if (param == null
                    || param.getStatus() == null
                    || param.getIcon() == null
                    || param.getName() == null
                    || param.getScope() == null
                    || param.getRedirectUri() == null
                    || param.getLimitingRule() == null) {
                p6eClientDataDto.setError(P6eModel.Error.PARAMETER_EXCEPTION);
            } else {
                param.setId(null);
                final P6eOauth2ClientDb p6eOauth2ClientDb =
                        new P6eClientEntity(P6eCopyUtil.run(param, P6eOauth2ClientDb.class)).create();
                if (p6eOauth2ClientDb == null) {
                    p6eClientDataDto.setError(P6eModel.Error.DATABASE_EXCEPTION);
                } else {
                    P6eCopyUtil.run(p6eOauth2ClientDb, p6eClientDataDto);
                }
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            p6eClientDataDto.setError(P6eModel.Error.PARAMETER_EXCEPTION);
        } catch (Exception ee) {
            LOGGER.error(ee.getMessage());
            p6eClientDataDto.setError(P6eModel.Error.SERVICE_EXCEPTION);
        }
        return p6eClientDataDto;
    }

    @Override
    public P6eClientDataDto delete(P6eClientDataDto param) {
        final P6eClientDataDto p6eClientDataDto = new P6eClientDataDto();
        try {
            if (param == null || param.getId() == null) {
                p6eClientDataDto.setError(P6eModel.Error.PARAMETER_EXCEPTION);
            } else {
                final P6eOauth2ClientDb p6eOauth2ClientDb = new P6eClientEntity(param.getId()).delete();
                if (p6eOauth2ClientDb == null) {
                    p6eClientDataDto.setError(P6eModel.Error.DATABASE_EXCEPTION);
                } else {
                    P6eCopyUtil.run(p6eOauth2ClientDb, p6eClientDataDto);
                }
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            p6eClientDataDto.setError(P6eModel.Error.PARAMETER_EXCEPTION);
        } catch (Exception ee) {
            LOGGER.error(ee.getMessage());
            p6eClientDataDto.setError(P6eModel.Error.SERVICE_EXCEPTION);
        }
        return p6eClientDataDto;
    }

    @Override
    public P6eClientDataDto update(P6eClientDataDto param) {
        final P6eClientDataDto p6eClientDataDto = new P6eClientDataDto();
        try {
            if (param == null || param.getId() == null) {
                p6eClientDataDto.setError(P6eModel.Error.PARAMETER_EXCEPTION);
            } else {
                final P6eOauth2ClientDb p6eOauth2ClientDb =
                        new P6eClientEntity(P6eCopyUtil.run(param, P6eOauth2ClientDb.class)).update();
                if (p6eOauth2ClientDb == null) {
                    p6eClientDataDto.setError(P6eModel.Error.DATABASE_EXCEPTION);
                } else {
                    P6eCopyUtil.run(p6eOauth2ClientDb, p6eClientDataDto);
                }
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            p6eClientDataDto.setError(P6eModel.Error.PARAMETER_EXCEPTION);
        } catch (Exception ee) {
            LOGGER.error(ee.getMessage());
            p6eClientDataDto.setError(P6eModel.Error.SERVICE_EXCEPTION);
        }
        return p6eClientDataDto;
    }

    @Override
    public P6eClientDataDto get(P6eClientDataDto param) {
        final P6eClientDataDto p6eClientDataDto = new P6eClientDataDto();
        try {
            if (param == null) {
                p6eClientDataDto.setError(P6eModel.Error.PARAMETER_EXCEPTION);
            } else {
                final P6eOauth2ClientDb p6eOauth2ClientDb;
                if (param.getId() != null) {
                    p6eOauth2ClientDb = new P6eClientEntity(param.getId()).get();
                    P6eCopyUtil.run(p6eOauth2ClientDb, p6eClientDataDto);
                } else if (param.getKey() != null) {
                    p6eOauth2ClientDb = new P6eClientEntity(param.getKey()).get();
                    P6eCopyUtil.run(p6eOauth2ClientDb, p6eClientDataDto);
                } else {
                    p6eClientDataDto.setError(P6eModel.Error.PARAMETER_EXCEPTION);
                }
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            p6eClientDataDto.setError(P6eModel.Error.PARAMETER_EXCEPTION);
        } catch (Exception ee) {
            LOGGER.error(ee.getMessage());
            p6eClientDataDto.setError(P6eModel.Error.SERVICE_EXCEPTION);
        }
        return p6eClientDataDto;
    }

    @Override
    public P6eClientDataDto select(P6eClientDataDto param) {
        final P6eClientDataDto p6eClientDataDto = new P6eClientDataDto();
        try {
            final P6eClientEntity p6eClientEntity = new P6eClientEntity(P6eCopyUtil.run(param, P6eOauth2ClientDb.class));
            p6eClientDataDto.setTotal(p6eClientEntity.count());
            p6eClientDataDto.setList(P6eCopyUtil.run(p6eClientEntity.select(), P6eClientDataDto.class));
        } catch (RuntimeException e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            p6eClientDataDto.setError(P6eModel.Error.PARAMETER_EXCEPTION);
        } catch (Exception ee) {
            LOGGER.error(ee.getMessage());
            p6eClientDataDto.setError(P6eModel.Error.SERVICE_EXCEPTION);
        }
        return p6eClientDataDto;
    }
}
