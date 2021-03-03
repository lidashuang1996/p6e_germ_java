package com.p6e.germ.oauth2.context.controller;

import com.p6e.germ.common.utils.P6eCopyUtil;
import com.p6e.germ.oauth2.application.P6eApplication;
import com.p6e.germ.oauth2.context.support.P6eBaseController;
import com.p6e.germ.oauth2.context.support.model.P6eTokenParam;
import com.p6e.germ.oauth2.context.support.model.P6eTokenResult;
import com.p6e.germ.oauth2.model.P6eModel;
import com.p6e.germ.oauth2.model.dto.*;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 令牌认证接口
 * @author lidashuang
 * @version 1.0
 */
@RestController
@RequestMapping("/token")
public class P6eTokenController extends P6eBaseController {

    @RequestMapping
    public P6eModel def(HttpServletRequest request, P6eTokenParam param) {
        param.setClientId(request.getParameter(CLIENT_ID_PARAM));
        param.setGrantType(request.getParameter(GRANT_TYPE_PARAM));
        param.setRedirectUri(request.getParameter(REDIRECT_URI_PARAM));
        param.setClientSecret(request.getParameter(CLIENT_SECRET_PARAM));
        if (param.getClientId() == null
                || param.getClientSecret() == null
                || param.getRedirectUri() == null
                || param.getGrantType() == null) {
            return P6eModel.build(P6eModel.Error.PARAMETER_EXCEPTION);
        } else {
            final P6eAuthTokenDto p6eAuthTokenDto;
            switch (param.getGrantType().toUpperCase()) {
                case AUTH_CODE_TYPE:
                    // code 执行模式
                    p6eAuthTokenDto = P6eApplication.auth.code(P6eCopyUtil.run(param, P6eCodeAuthDto.class));
                    break;
                case PASSWORD_TYPE:
                    // password 执行模式
                    if (HttpMethod.POST.name().equals(request.getMethod().toUpperCase())) {
                        p6eAuthTokenDto = P6eApplication.auth.password(P6eCopyUtil.run(param, P6ePasswordAuthDto.class));
                        break;
                    } else {
                        return P6eModel.build(P6eModel.Error.HTTP_METHOD_EXCEPTION);
                    }
                case CLIENT_TYPE:
                    // 客户端执行模式
                    if (HttpMethod.POST.name().equals(request.getMethod().toUpperCase())) {
                        p6eAuthTokenDto = P6eApplication.auth.client(P6eCopyUtil.run(param, P6eClientAuthDto.class));
                        break;
                    } else {
                        return P6eModel.build(P6eModel.Error.HTTP_METHOD_EXCEPTION);
                    }
                default:
                    return P6eModel.build(P6eModel.Error.PARAMETER_EXCEPTION);
            }
            if (p6eAuthTokenDto.getError() == null) {
                return P6eModel.build().setData(P6eCopyUtil.run(p6eAuthTokenDto, P6eTokenResult.class));
            } else {
                return P6eModel.build(p6eAuthTokenDto.getError());
            }
        }
    }

    @RequestMapping("/refresh")
    public P6eModel refresh(HttpServletRequest request) {
        String token = request.getParameter(AUTH_PARAM_NAME);
        String refreshToken = request.getParameter(REFRESH_TOKEN_NAME);
        if (token == null) {
            final String content = request.getHeader(AUTH_HEADER_NAME);
            if (content != null && content.startsWith(AUTH_HEADER_BEARER)) {
                token = content.substring(7);
            }
        }
        // 通过 token 获取用户数据
        if (token != null && refreshToken != null) {
            final P6eAuthTokenDto p6eAuthTokenDto = P6eApplication.auth.refresh(token, refreshToken);
            if (p6eAuthTokenDto.getError() == null) {
                return P6eModel.build().setData(P6eCopyUtil.run(p6eAuthTokenDto, P6eTokenResult.class));
            } else {
                return P6eModel.build(p6eAuthTokenDto.getError());
            }
        }
        return P6eModel.build(P6eModel.Error.PARAMETER_EXCEPTION);
    }

}
