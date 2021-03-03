package com.p6e.germ.oauth2.application.impl;

import com.p6e.germ.common.utils.P6eCopyUtil;
import com.p6e.germ.oauth2.application.P6eAuthService;
import com.p6e.germ.oauth2.domain.entity.*;
import com.p6e.germ.oauth2.domain.keyvalue.P6eMarkKeyValue;
import com.p6e.germ.oauth2.model.P6eModel;
import com.p6e.germ.oauth2.model.db.P6eOauth2ClientDb;
import com.p6e.germ.oauth2.model.db.P6eOauth2LogDb;
import com.p6e.germ.oauth2.model.dto.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * oauth2 4 种授权
 * @author lidashuang
 * @version 1.0
 */
public class P6eAuthServiceImpl implements P6eAuthService {

    /** 注入日志系统 */
    private final static Logger LOGGER = LoggerFactory.getLogger(P6eAuthService.class);

    @Override
    public P6eAuthDto mark(String mark) {
        final P6eAuthDto p6eAuthDto = new P6eAuthDto();
        try {
            if (mark == null) {
                p6eAuthDto.setError(P6eModel.Error.PARAMETER_EXCEPTION);
            } else {
                // 读取 mark 的数据
                final P6eMarkKeyValue p6eMarkKeyValue = new P6eMarkEntity(mark).getP6eMarkKeyValue();
                final P6eOauth2ClientDb p6eOauth2ClientDb = new P6eClientEntity(p6eMarkKeyValue.getId()).get();
                // 写入数据
                p6eAuthDto.setMark(mark);
                P6eCopyUtil.run(p6eOauth2ClientDb, p6eAuthDto);
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
    public P6eAuthDto verification(P6eVerificationAuthDto param) {
        final P6eAuthDto p6eAuthDto = new P6eAuthDto();
        try {
            // 读取客户端信息
            final P6eClientEntity p6eClientEntity = new P6eClientEntity(param.getClientId());
            // 验证客户端信息
            if (p6eClientEntity.verificationScope(param.getScope())
                    && p6eClientEntity.verificationRedirectUri(param.getRedirectUri())) {
                // 生成记号
                final P6eMarkEntity p6eMarkEntity = new P6eMarkEntity(
                        P6eCopyUtil.run(param, P6eMarkKeyValue.class).setId(p6eClientEntity.get().getId())).cache();
                // 写入信息
                p6eAuthDto.setMark(p6eMarkEntity.getMark());
                // 复制信息并写入
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
    public P6eAuthTokenDto code(P6eCodeAuthDto param) {
        final P6eAuthTokenDto p6eAuthTokenDto = new P6eAuthTokenDto();
        try {
            // 读取客户端信息
            final P6eClientEntity p6eClientEntity = new P6eClientEntity(param.getClientId());
            // 验证客户端信息
            if (p6eClientEntity.verificationScope(param.getScope())
                    && p6eClientEntity.verificationRedirectUri(param.getRedirectUri())
                    && p6eClientEntity.verificationSecret(param.getClientSecret())) {
                // 获取对象并删除 CODE 缓存数据
                final P6eTokenEntity p6eTokenEntity = new P6eTokenEntity(param.getCode()).delModel();
                P6eCopyUtil.run(p6eTokenEntity.getModel(), p6eAuthTokenDto);
                // CID / UID
                int cid = p6eClientEntity.get().getId();
                int uid = Integer.parseInt(p6eTokenEntity.getValue().get("id"));
                // 写入日志数据
                new P6eLogEntity(new P6eOauth2LogDb()
                        .setCid(cid)
                        .setUid(uid)
                        .setType("CID_TO_UID_CODE_TYPE")
                ).create();
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
            // 读取客户端信息
            final P6eClientEntity p6eClientEntity = new P6eClientEntity(param.getClientId());
            // 验证客户端信息
            if (p6eClientEntity.verificationScope(param.getScope())
                    && p6eClientEntity.verificationRedirectUri(param.getRedirectUri())
                    && p6eClientEntity.verificationSecret(param.getClientSecret())) {
                // 生成缓存对象写入缓存，并返回数据
                final P6eTokenEntity p6eTokenEntity = p6eClientEntity.createTokenCache().cache();
                // 写入数据
                P6eCopyUtil.run(p6eTokenEntity.getModel(), p6eAuthTokenDto);
                // CID / UID
                int cid = p6eClientEntity.get().getId();
                int uid = Integer.parseInt(p6eTokenEntity.getValue().get("id"));
                // 写入日志数据
                new P6eLogEntity(new P6eOauth2LogDb()
                        .setCid(cid)
                        .setUid(uid)
                        .setType("UID_TO_CID_CLIENT_TYPE")
                ).create();
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
            // 读取客户端信息
            final P6eClientEntity p6eClientEntity = new P6eClientEntity(param.getClientId());
            // 验证客户端信息
            if (p6eClientEntity.verificationScope(param.getScope())
                    && p6eClientEntity.verificationRedirectUri(param.getRedirectUri())
                    && p6eClientEntity.verificationSecret(param.getClientSecret())) {
                final P6eUserEntity p6eUserEntity = new P6eUserEntity(new P6eUserEntity.Account(param.getAccount()));
                if (p6eUserEntity.defaultVerification(param.getPassword())) {
                    // 生成缓存对象写入缓存，并返回数据
                    final P6eTokenEntity p6eTokenEntity = p6eUserEntity.createTokenCache().cache();
                    // 写入数据
                    P6eCopyUtil.run(p6eTokenEntity.getModel(), p6eAuthTokenDto);
                    // CID / UID
                    int cid = p6eClientEntity.get().getId();
                    int uid = Integer.parseInt(p6eTokenEntity.getValue().get("id"));
                    // 写入日志数据
                    new P6eLogEntity(new P6eOauth2LogDb()
                            .setCid(cid)
                            .setUid(uid)
                            .setType("UID_TO_CID_PASSWORD_TYPE")
                    ).create();
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
            // 查询用户信息
            final P6eTokenEntity p6eTokenEntity = new P6eTokenEntity(token, P6eTokenEntity.ACCESS_TOKEN);
            // 验证 refresh token 的内容
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
            // 查询用户信息
            final P6eTokenEntity p6eTokenEntity = new P6eTokenEntity(token, P6eTokenEntity.ACCESS_TOKEN);
            // 查询信息写入返回对象
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
