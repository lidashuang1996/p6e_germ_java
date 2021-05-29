package com.p6e.germ.oauth2.application.impl;

import com.p6e.germ.common.utils.P6eCopyUtil;
import com.p6e.germ.oauth2.application.P6eAuthService;
import com.p6e.germ.oauth2.domain.entity.*;
import com.p6e.germ.oauth2.domain.keyvalue.P6eMarkKeyValue;
import com.p6e.germ.oauth2.model.P6eAuthModel;
import com.p6e.germ.oauth2.model.P6eAuthTokenModel;
import com.p6e.germ.oauth2.model.P6eResultModel;
import com.p6e.germ.oauth2.model.dto.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

/**
 * 认证服务
 * @author lidashuang
 * @version 1.0
 */
public class P6eAuthServiceImpl implements P6eAuthService {

    /** 注入日志系统 */
    private final static Logger LOGGER = LoggerFactory.getLogger(P6eAuthService.class);

    @Override
    public P6eAuthModel.DtoResult verification(P6eAuthModel.DtoParam param) {
        final P6eAuthModel.DtoResult result = new P6eAuthModel.DtoResult();
        try {
            // 读取客户端信息
            final P6eClientEntity client;
            try {
                client = P6eClientEntity.get(param.getClientId());
            } catch (Exception e) {
                result.setError(P6eResultModel.Error.PARAMETER_EXCEPTION);
                return result;
            }
            // 验证客户端信息
            if (client.verificationScope(param.getScope())
                    && client.verificationRedirectUri(param.getRedirectUri())) {
                // 记号参数
                final P6eMarkKeyValue.Content content = new P6eMarkKeyValue.Content();
                P6eCopyUtil.run(param, content);
                P6eCopyUtil.run(client.getData(), content);
                // 生成记号
                result.setMark(P6eMarkEntity.create(content).cache().getKey());
            } else {
                result.setError(P6eResultModel.Error.PARAMETER_EXCEPTION);
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            result.setError(P6eResultModel.Error.SERVICE_EXCEPTION);
        }
        return result;
    }

    @Override
    public P6eAuthTokenModel.DtoResult code(P6eAuthTokenModel.DtoParam param) {
        final P6eAuthTokenModel.DtoResult result = new P6eAuthTokenModel.DtoResult();
        try {
            // 读取客户端信息
            final P6eClientEntity client;
            try {
                client = P6eClientEntity.get(param.getClientId());
            } catch (Exception e) {
                result.setError(P6eResultModel.Error.PARAMETER_EXCEPTION);
                return result;
            }
            if (client.verificationSecret(param.getClientSecret())
                    && client.verificationRedirectUri(param.getRedirectUri())) {
                try {
                    P6eCopyUtil.run(P6eTokenEntity.get(param.getCode()).getModel(), result);
                } catch (Exception e) {
                    result.setError(P6eResultModel.Error.EXPIRATION_EXCEPTION);
                }
            } else {
                result.setError(P6eResultModel.Error.PARAMETER_EXCEPTION);
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            result.setError(P6eResultModel.Error.SERVICE_EXCEPTION);
        }
        return result;
    }

    @Override
    public P6eAuthTokenModel.DtoResult client(P6eAuthTokenModel.DtoParam param) {
        final P6eAuthTokenModel.DtoResult result = new P6eAuthTokenModel.DtoResult();
        try {
            // 读取客户端信息
            final P6eClientEntity client;
            try {
                client = P6eClientEntity.get(param.getClientId());
            } catch (Exception e) {
                result.setError(P6eResultModel.Error.PARAMETER_EXCEPTION);
                return result;
            }
            if (client.verificationSecret(param.getClientSecret())) {
                final P6eTokenEntity token = new P6eTokenEntity(String.valueOf(client.getData().getId()), new HashMap<>());
                P6eCopyUtil.run(token.getModel(), result);
            } else {
                result.setError(P6eResultModel.Error.PARAMETER_EXCEPTION);
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            result.setError(P6eResultModel.Error.SERVICE_EXCEPTION);
        }
        return result;
    }

    @Override
    public P6eAuthTokenModel.DtoResult password(P6eAuthTokenModel.DtoParam param) {
        final P6eAuthTokenModel.DtoResult result = new P6eAuthTokenModel.DtoResult();
        try {
            final String cid = param.getClientId();
            final String username = param.getUsername();
            final String password = param.getPassword();
            try {
                P6eClientEntity.get(cid);
            } catch (Exception e) {
                result.setError(P6eResultModel.Error.PARAMETER_EXCEPTION);
                return result;
            }
            final P6eUserEntity user;
            try {
                user = new P6eUserEntity(new P6eUserEntity.Account(username));
            } catch (Exception e) {
                result.setError(P6eResultModel.Error.ACCOUNT_OR_PASSWORD);
                return result;
            }
            if (user.defaultVerification(password)) {
                P6eCopyUtil.run(user.createTokenCache().getModel(), result);
            } else {
                result.setError(P6eResultModel.Error.ACCOUNT_OR_PASSWORD);
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            result.setError(P6eResultModel.Error.SERVICE_EXCEPTION);
        }
        return result;
    }

    @Override
    public P6eAuthTokenModel.DtoResult refresh(P6eAuthTokenModel.DtoParam param) {
        final P6eAuthTokenModel.DtoResult result = new P6eAuthTokenModel.DtoResult();
        try {
            final String accessToken = param.getAccessToken();
            final String refreshToken = param.getRefreshToken();
            P6eTokenEntity userToken = new P6eTokenEntity(accessToken);
            if (userToken.verificationRefreshToken(refreshToken)) {
                P6eCopyUtil.run(result,  userToken.refresh().getModel());
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            result.setError(P6eResultModel.Error.EXPIRATION_EXCEPTION);
        }
        return result;
    }


    @Override
    public P6eInfoDto info(String accessToken) {
        return null;
    }


}
