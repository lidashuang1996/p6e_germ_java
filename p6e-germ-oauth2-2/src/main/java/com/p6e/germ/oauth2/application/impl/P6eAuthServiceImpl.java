package com.p6e.germ.oauth2.application.impl;

import com.p6e.germ.common.utils.P6eCopyUtil;
import com.p6e.germ.oauth2.application.P6eAuthService;
import com.p6e.germ.oauth2.domain.entity.*;
import com.p6e.germ.oauth2.domain.keyvalue.*;
import com.p6e.germ.oauth2.model.*;
import com.p6e.germ.oauth2.model.P6eInfoAuthModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 认证服务
 * @author lidashuang
 * @version 1.0
 */
public class P6eAuthServiceImpl implements P6eAuthService {

    /** 注入日志系统 */
    private final static Logger LOGGER = LoggerFactory.getLogger(P6eAuthService.class);

    @Override
    public com.p6e.germ.oauth2.model.P6eAuthModel.DtoResult verification(com.p6e.germ.oauth2.model.P6eAuthModel.DtoParam param) {
        final com.p6e.germ.oauth2.model.P6eAuthModel.DtoResult result = new com.p6e.germ.oauth2.model.P6eAuthModel.DtoResult();
        try {
            // 读取客户端信息
            final P6eClientEntity client;
            try {
                client = P6eClientEntity.get(new P6eClientKeyValue.Key(param.getClientId()));
            } catch (Exception e) {
                result.setError(P6eResultModel.Error.PARAMETER_EXCEPTION);
                return result;
            }
            // 验证客户端信息
            if (client.verificationScope(param.getScope())
                    && client.verificationRedirectUri(param.getRedirectUri())) {
                // 记号参数
                final P6eMarkKeyValue.Content content = new P6eMarkKeyValue.Content();
                P6eCopyUtil.run(client.getData(), content);
                P6eCopyUtil.run(param, content);
                // 生成记号并写入数据且返回
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
    public P6eCodeAuthModel.DtoResult code(P6eCodeAuthModel.DtoParam param) {
        final P6eCodeAuthModel.DtoResult result = new P6eCodeAuthModel.DtoResult();
        try {
            // 读取客户端信息
            final P6eClientEntity client;
            try {
                client = P6eClientEntity.get(new P6eClientKeyValue.Key(param.getClientId()));
            } catch (Exception e) {
                result.setError(P6eResultModel.Error.CLIENT_ID_EXCEPTION);
                return result;
            }
            // 验证客户端的信息是否匹配
            if (client.verificationSecret(param.getClientSecret())
                    && client.verificationRedirectUri(param.getRedirectUri())) {
                if (client.isEnable()) {
                    try {
                        // 读取缓存数据并返回
                        P6eCopyUtil.run(P6eTokenEntity.get(param.getCode()).getContent(), result);
                    } catch (Exception e) {
                        result.setError(P6eResultModel.Error.EXPIRATION_EXCEPTION);
                        return result;
                    }
                } else {
                    result.setError(P6eResultModel.Error.CLIENT_NO_ENABLE_EXCEPTION);
                }
            } else {
                result.setError(P6eResultModel.Error.CLIENT_PARAM_EXCEPTION);
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            result.setError(P6eResultModel.Error.SERVICE_EXCEPTION);
        }
        return result;
    }

    @Override
    public P6eClientAuthModel.DtoResult client(P6eClientAuthModel.DtoParam param) {
        final P6eClientAuthModel.DtoResult result = new P6eClientAuthModel.DtoResult();
        try {
            // 读取客户端信息
            final P6eClientEntity client;
            try {
                client = P6eClientEntity.get(new P6eClientKeyValue.Key(param.getClientId()));
            } catch (Exception e) {
                result.setError(P6eResultModel.Error.CLIENT_ID_EXCEPTION);
                return result;
            }
            // 验证客户端的信息是否匹配
            if (client.verificationSecret(param.getClientSecret())
                    && client.verificationRedirectUri(param.getRedirectUri())) {
                if (client.isEnable()) {
                     final P6eTokenEntity token = P6eTokenEntity.create(null, new P6eTokenKeyValue.DataParam(
                             "CLIENT_" + client.getData().getId(), client.getData().toMap()));
                     P6eCopyUtil.run(token.getContent(), result);
                } else {
                    result.setError(P6eResultModel.Error.CLIENT_NO_ENABLE_EXCEPTION);
                }
            } else {
                result.setError(P6eResultModel.Error.CLIENT_PARAM_EXCEPTION);
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            result.setError(P6eResultModel.Error.SERVICE_EXCEPTION);
        }
        return result;
    }

    @Override
    public P6ePasswordAuthModel.DtoResult password(P6ePasswordAuthModel.DtoParam param) {
        final P6ePasswordAuthModel.DtoResult result = new P6ePasswordAuthModel.DtoResult();
        try {
            final String cid = param.getClientId();
            final String username = param.getUsername();
            final String password = param.getPassword();
            // 读取客户端信息
            final P6eClientEntity client;
            try {
                client = P6eClientEntity.get(new P6eClientKeyValue.Key(cid));
            } catch (Exception e) {
                result.setError(P6eResultModel.Error.CLIENT_ID_EXCEPTION);
                return result;
            }
            // 验证客户端的信息是否匹配
            if (client.verificationSecret(param.getClientSecret())
                    && client.verificationRedirectUri(param.getRedirectUri())) {
                if (client.isEnable()) {
                    final P6eUserEntity user;
                    try {
                        user = P6eUserEntity.get(new P6eUserKeyValue.Account(username));
                    } catch (Exception e) {
                        result.setError(P6eResultModel.Error.ACCOUNT_OR_PASSWORD);
                        return result;
                    }
                    if (user.defaultVerification(password)) {
                        final P6eUserKeyValue.Content uContent = user.getData();
                        final P6eTokenEntity token = P6eTokenEntity.create(null,
                                new P6eTokenKeyValue.DataParam(String.valueOf(uContent.getId()), uContent.toMap()));
                        P6eCopyUtil.run(token.getContent(), result);
                    } else {
                        result.setError(P6eResultModel.Error.ACCOUNT_OR_PASSWORD);
                    }
                } else {
                    result.setError(P6eResultModel.Error.CLIENT_NO_ENABLE_EXCEPTION);
                }
            } else {
                result.setError(P6eResultModel.Error.CLIENT_PARAM_EXCEPTION);
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            result.setError(P6eResultModel.Error.SERVICE_EXCEPTION);
        }
        return result;
    }

    @Override
    public P6eRefreshAuthModel.DtoResult refresh(P6eRefreshAuthModel.DtoParam param) {
        final P6eRefreshAuthModel.DtoResult result = new P6eRefreshAuthModel.DtoResult();
        try {
            final String accessToken = param.getAccessToken();
            final String refreshToken = param.getRefreshToken();
            final P6eTokenEntity token = P6eTokenEntity.get(new P6eTokenKeyValue.AccessToken(accessToken));
            if (token.verificationRefreshToken(refreshToken)) {
                P6eCopyUtil.run(token.refresh().getData(), result);
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            result.setError(P6eResultModel.Error.EXPIRATION_EXCEPTION);
        }
        return result;
    }


    @Override
    public P6eInfoAuthModel.DtoResult info(P6eInfoAuthModel.DtoParam param) {
        final P6eInfoAuthModel.DtoResult result = new P6eInfoAuthModel.DtoResult();
        try {
            final String accessToken = param.getAccessToken();
            final P6eTokenEntity token = P6eTokenEntity.get(new P6eTokenKeyValue.AccessToken(accessToken));
            result.setData(token.getData());
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            result.setError(P6eResultModel.Error.EXPIRATION_EXCEPTION);
        }
        return result;
    }

    @Override
    public P6eCacheAuthModel.DtoResult setCache(P6eCacheAuthModel.DtoParam param) {
        final P6eCacheAuthModel.DtoResult result = new P6eCacheAuthModel.DtoResult();
        try {
            final P6eAuthEntity auth =
                    P6eAuthEntity.create(new P6eAuthKeyValue.Content(param.getContent()));
            System.out.println(auth.getKey() + "  " + param.getContent());
            result.setContent(auth.getKey());
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            result.setError(P6eResultModel.Error.RESOURCES_NO_EXIST);
        }
        return result;
    }

    @Override
    public P6eCacheAuthModel.DtoResult getCache(P6eCacheAuthModel.DtoParam param) {
        final P6eCacheAuthModel.DtoResult result = new P6eCacheAuthModel.DtoResult();
        try {
            final P6eAuthEntity auth =
                    P6eAuthEntity.get(new P6eAuthKeyValue.Key(param.getKey()));
            result.setContent(auth.getValue());
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            result.setError(P6eResultModel.Error.RESOURCES_NO_EXIST);
        }
        return result;
    }

    @Override
    public P6eCacheAuthModel.DtoResult cleanCache(P6eCacheAuthModel.DtoParam param) {
        final P6eCacheAuthModel.DtoResult result = new P6eCacheAuthModel.DtoResult();
        try {
            final P6eAuthEntity auth =
                    P6eAuthEntity.get(new P6eAuthKeyValue.Key(param.getKey())).clean();
            result.setContent(auth.getValue());
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            result.setError(P6eResultModel.Error.RESOURCES_NO_EXIST);
        }
        return result;
    }


}
