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
            final P6eClientEntity client = P6eClientEntity.get(param.getClientId());
            // 验证客户端信息
            if (client.verificationScope(param.getScope())
                    && client.verificationRedirectUri(param.getRedirectUri())) {
                // 记号参数
                final P6eMarkKeyValue.Content content = P6eCopyUtil.run(param, P6eMarkKeyValue.Content.class);
                P6eCopyUtil.run(client.getData(), content);
                // 生成记号
                final P6eMarkEntity mark = P6eMarkEntity.create(content).cache();
                // 写入信息
                result.setMark(mark.getKey());
            } else {
                result.setError(P6eResultModel.Error.PARAMETER_EXCEPTION);
            }
        } catch (RuntimeException e) {
            LOGGER.error(e.getMessage());
            result.setError(P6eResultModel.Error.RESOURCES_NO_EXIST);
        } catch (Exception ee) {
            LOGGER.error(ee.getMessage());
            result.setError(P6eResultModel.Error.SERVICE_EXCEPTION);
        }
        return result;
    }

    @Override
    public P6eAuthTokenModel.DtoResult code(P6eAuthTokenModel.DtoParam param) {
        return null;
    }

    @Override
    public P6eAuthTokenModel.DtoResult client(P6eAuthTokenModel.DtoParam param) {
        return null;
    }

    @Override
    public P6eAuthTokenModel.DtoResult password(P6eAuthTokenModel.DtoParam param) {
        return null;
    }

    @Override
    public P6eAuthTokenModel.DtoResult refresh(P6eAuthTokenModel.DtoParam param) {
        return null;
    }


    @Override
    public P6eInfoDto info(String accessToken) {
        return null;
    }


}
