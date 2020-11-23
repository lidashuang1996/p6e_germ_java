package com.p6e.germ.oauth2.application;

import com.p6e.germ.oauth2.context.controller.support.model.P6eAuthModelParam;
import com.p6e.germ.oauth2.context.controller.support.model.P6eAuthModelResult;
import com.p6e.germ.oauth2.context.controller.support.model.P6eModelConfig;
import com.p6e.germ.oauth2.context.controller.support.model.P6eTokenModelParam;
import com.p6e.germ.oauth2.domain.aggregate.P6eClientModeAggregate;
import com.p6e.germ.oauth2.domain.aggregate.P6eCodeModeAggregate;
import com.p6e.germ.oauth2.domain.aggregate.P6ePasswordModeAggregate;
import com.p6e.germ.oauth2.domain.entity.P6eClientEntity;
import com.p6e.germ.oauth2.domain.keyvalue.*;
import com.p6e.germ.oauth2.infrastructure.exception.ParamException;
import com.p6e.germ.oauth2.infrastructure.utils.CopyUtil;
import com.p6e.germ.oauth2.infrastructure.utils.JsonUtil;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 认证的服务
 * @author lidashuang
 * @version 1.0
 */
@Service
public class P6eAuthService {

    /** 认证的缓存标记 */
    private static final String AUTH_COOKIE = "P6E_OAUTH2_AUTH_TOKEN";

    /**
     * 执行 code 认证模式
     * @param param 请求参数
     * @return 处理的结果
     */
    public P6eAuthModelResult codeMode(P6eAuthModelParam param) {
        final P6eAuthModelResult p6eAuthModelResult = new P6eAuthModelResult();
        try {
            final P6eCodeAuthKeyValue p6eCodeModeKeyValue = P6eCodeModeAggregate.create().generate(
                    new P6eAuthKeyValue(
                            param.getClient_id(),
                            param.getScope(),
                            param.getState(),
                            param.getResponse_type(),
                            param.getRedirect_uri()
                    ));
            CopyUtil.run(p6eCodeModeKeyValue, p6eAuthModelResult);
        } catch (ParamException | NullPointerException e) {
            e.printStackTrace();
            p6eAuthModelResult.setError(P6eModelConfig.ERROR_OAUTH2_PARAM_EXCEPTION);
        }
        return p6eAuthModelResult;
    }

    public Map<String, Object> codeModeExecute(P6eTokenModelParam param) {
        try {
            return CopyUtil.toMap(P6eCodeModeAggregate.create().execute(
                    param.getCode(),
                    param.getRedirect_uri(),
                    param.getClient_id(),
                    param.getClient_secret()
            ));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public P6eTokenKeyValue passwordModeExecute(P6ePasswordModeKeyValue param) {
        try {
            return P6ePasswordModeAggregate.create().execute(param);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public P6eTokenKeyValue clientModeExecute(P6eClientModeKeyValue param) {
        try {
            return P6eClientModeAggregate.create().execute(param);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取客户端的信息
     * @param request
     * @param param
     * @return
     */
    public P6eAuthModelResult client(final HttpServletRequest request, final P6eAuthModelParam param) {
        final Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            for (final Cookie cookie : cookies) {
                if (AUTH_COOKIE.equals(cookie.getName().toUpperCase())) {
                    final P6eClientEntity p6eClientEntity = P6eClientEntity.fetch(param.getClient_id());
                    if (p6eClientEntity.verificationScope(param.getScope())
                            && p6eClientEntity.verificationRedirectUri(param.getRedirect_uri())) {
                        final P6eAuthModelResult p6eAuthModelResult = new P6eAuthModelResult();
                        p6eAuthModelResult.setIcon(p6eClientEntity.getIcon());
                        p6eAuthModelResult.setName(p6eClientEntity.getName());
                        p6eAuthModelResult.setDescribe(p6eClientEntity.getDescribe());
                        return p6eAuthModelResult;
                    }
                }
            }
        }
        return null;
    }

    /**
     * 执行简化认证模式
     * @param param
     * @return
     */
    public P6eAuthModelResult tokenMode(P6eAuthModelParam param) {
        return codeMode(param);
    }
}
