package com.p6e.germ.oauth2.application.impl;

import com.p6e.germ.oauth2.application.P6eAuthService;
import com.p6e.germ.oauth2.domain.entity.*;
import com.p6e.germ.oauth2.domain.keyvalue.P6eAuthKeyValue;
import com.p6e.germ.oauth2.infrastructure.utils.P6eCopyUtil;
import com.p6e.germ.oauth2.model.P6eModel;
import com.p6e.germ.oauth2.model.dto.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * oauth2.0 4 种授权
 * @author lidashuang
 * @version 1.0
 */
public class P6eAuthServiceImpl implements P6eAuthService {

    /** 注入日志系统 */
    private final static Logger LOGGER = LoggerFactory.getLogger(P6eAuthService.class);

    @Override
    public P6eAuthDto code(P6eCodeAuthDto param) {
        final P6eAuthDto p6eAuthDto = new P6eAuthDto();
        try {
            final P6eClientEntity p6eClientEntity = new P6eClientEntity(param.getClientId());
            if (p6eClientEntity.verificationScope(param.getScope())
                    && p6eClientEntity.verificationRedirectUri(param.getRedirectUri())) {
                // 生产凭证
                final P6eVoucherEntity p6eVoucherEntity = new P6eVoucherEntity().create();
                // 生成记号
                final P6eMarkEntity p6eMarkEntity = new P6eMarkEntity(P6eCopyUtil.run(param, P6eAuthKeyValue.class));
                // 写入信息
                p6eAuthDto.setMark(p6eMarkEntity.getMark());
                p6eAuthDto.setVoucher(p6eVoucherEntity.getVoucher());
                p6eAuthDto.setPublicKey(p6eVoucherEntity.getPublicSecretKey());
                P6eCopyUtil.run(p6eClientEntity.get(), p6eAuthDto);
            } else {
                p6eAuthDto.setError(P6eModel.Error.PARAMETER_EXCEPTION);
            }
        } catch (RuntimeException e) {
            LOGGER.error(e.getMessage());
            p6eAuthDto.setError(P6eModel.Error.PARAMETER_EXCEPTION);
        } catch (Exception ee) {
            LOGGER.error(ee.getMessage());
            p6eAuthDto.setError(P6eModel.Error.SERVICE_EXCEPTION);
        }
        return p6eAuthDto;
    }

    @Override
    public P6eAuthDto simple(P6eSimpleAuthDto param) {
        return code(P6eCopyUtil.run(param, P6eCodeAuthDto.class));
    }

    @Override
    public P6eAuthTokenDto codeCallback(P6eCodeCallbackAuthDto param) {
        final P6eAuthTokenDto p6eAuthTokenDto = new P6eAuthTokenDto();
        try {
            final P6eClientEntity p6eClientEntity = new P6eClientEntity(param.getClientId());
            if (p6eClientEntity.verificationScope(param.getScope())
                    && p6eClientEntity.verificationRedirectUri(param.getRedirectUri())
                    && p6eClientEntity.verificationSecret(param.getClientSecret())) {
                final P6eTokenEntity p6eTokenEntity = new P6eTokenEntity(param.getCode()).delModel();
                P6eCopyUtil.run(p6eTokenEntity.getModel(), p6eAuthTokenDto);
            } else {
                p6eAuthTokenDto.setError(P6eModel.Error.PARAMETER_EXCEPTION);
            }
        } catch (RuntimeException e) {
            LOGGER.error(e.getMessage());
            p6eAuthTokenDto.setError(P6eModel.Error.PARAMETER_EXCEPTION);
        } catch (Exception ee) {
            LOGGER.error(ee.getMessage());
            p6eAuthTokenDto.setError(P6eModel.Error.SERVICE_EXCEPTION);
        }
        return p6eAuthTokenDto;
    }

    @Override
    public P6eAuthTokenDto client(P6eClientAuthDto param) {
        final P6eAuthTokenDto p6eAuthTokenDto = new P6eAuthTokenDto();
        try {
            final P6eClientEntity p6eClientEntity = new P6eClientEntity(param.getClientId());
            if (p6eClientEntity.verificationScope(param.getScope())
                    && p6eClientEntity.verificationRedirectUri(param.getRedirectUri())
                    && p6eClientEntity.verificationSecret(param.getClientSecret())) {
                // 生成缓存对象写入缓存，并返回数据
                final P6eTokenEntity p6eTokenEntity = p6eClientEntity.createTokenCache();
                p6eTokenEntity.cache();
                P6eCopyUtil.run(p6eTokenEntity.getModel(), p6eAuthTokenDto);
            } else {
                p6eAuthTokenDto.setError(P6eModel.Error.PARAMETER_EXCEPTION);
            }
        } catch (RuntimeException e) {
            LOGGER.error(e.getMessage());
            p6eAuthTokenDto.setError(P6eModel.Error.PARAMETER_EXCEPTION);
        } catch (Exception ee) {
            LOGGER.error(ee.getMessage());
            p6eAuthTokenDto.setError(P6eModel.Error.SERVICE_EXCEPTION);
        }
        return p6eAuthTokenDto;
    }

    @Override
    public P6eAuthTokenDto password(P6ePasswordAuthDto param) {
        final P6eAuthTokenDto p6eAuthTokenDto = new P6eAuthTokenDto();
        try {
            final P6eClientEntity p6eClientEntity = new P6eClientEntity(param.getClientId());
            if (p6eClientEntity.verificationScope(param.getScope())
                    && p6eClientEntity.verificationRedirectUri(param.getRedirectUri())
                    && p6eClientEntity.verificationSecret(param.getClientSecret())) {
                final P6eUserEntity p6eUserEntity = new P6eUserEntity(param.getAccount());
                if (p6eUserEntity.defaultVerification(param.getPassword())) {
                    // 生成缓存对象写入缓存，并返回数据
                    final P6eTokenEntity p6eTokenEntity = p6eUserEntity.createTokenCache();
                    p6eTokenEntity.cache();
                    P6eCopyUtil.run(p6eTokenEntity.getModel(), p6eAuthTokenDto);
                } else {
                    p6eAuthTokenDto.setError(P6eModel.Error.ACCOUNT_OR_PASSWORD);
                }
            } else {
                p6eAuthTokenDto.setError(P6eModel.Error.PARAMETER_EXCEPTION);
            }
        } catch (RuntimeException e) {
            LOGGER.error(e.getMessage());
            p6eAuthTokenDto.setError(P6eModel.Error.PARAMETER_EXCEPTION);
        } catch (Exception ee) {
            LOGGER.error(ee.getMessage());
            p6eAuthTokenDto.setError(P6eModel.Error.SERVICE_EXCEPTION);
        }
        return p6eAuthTokenDto;
    }

    @Override
    public P6eAuthTokenDto refresh(String token, String refreshToken) {
        final P6eAuthTokenDto p6eAuthTokenDto = new P6eAuthTokenDto();
        try {
            final P6eTokenEntity p6eTokenEntity = new P6eTokenEntity(token, P6eTokenEntity.ACCESS_TOKEN);
            if (p6eTokenEntity.verificationRefreshToken(refreshToken)) {
                P6eCopyUtil.run(p6eTokenEntity.refresh().getModel(), p6eAuthTokenDto);
            } else {
                p6eAuthTokenDto.setError(P6eModel.Error.EXPIRATION_EXCEPTION);
            }
        } catch (RuntimeException e) {
            LOGGER.error(e.getMessage());
            p6eAuthTokenDto.setError(P6eModel.Error.EXPIRATION_EXCEPTION);
        } catch (Exception ee) {
            LOGGER.error(ee.getMessage());
            p6eAuthTokenDto.setError(P6eModel.Error.SERVICE_EXCEPTION);
        }
        return p6eAuthTokenDto;
    }

    @Override
    public P6eInfoDto info(String token) {
        final P6eInfoDto p6eInfoDto = new P6eInfoDto();
        try {
            final P6eTokenEntity p6eTokenEntity = new P6eTokenEntity(token, P6eTokenEntity.ACCESS_TOKEN);
            p6eInfoDto.setData(p6eTokenEntity.getValue());
        } catch (RuntimeException e) {
            LOGGER.error(e.getMessage());
            p6eInfoDto.setError(P6eModel.Error.EXPIRATION_EXCEPTION);
        } catch (Exception ee) {
            LOGGER.error(ee.getMessage());
            p6eInfoDto.setError(P6eModel.Error.SERVICE_EXCEPTION);
        }
        return p6eInfoDto;
    }
}
