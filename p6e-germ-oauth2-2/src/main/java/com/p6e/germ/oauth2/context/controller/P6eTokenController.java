package com.p6e.germ.oauth2.context.controller;

import com.p6e.germ.oauth2.application.P6eApplication;
import com.p6e.germ.oauth2.context.support.P6eBaseController;
import com.p6e.germ.oauth2.context.support.model.P6eTokenParam;
import com.p6e.germ.oauth2.context.support.model.P6eTokenResult;
import com.p6e.germ.oauth2.infrastructure.utils.P6eCopyUtil;
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

    /** 认证的类型 - 密码 */
    private static final String PASSWORD_TYPE = "PASSWORD";
    /** 认证的类型 - CODE */
    private static final String AUTH_CODE_TYPE = "AUTHORIZATION_CODE";
    /** 认证的类型 - 客户端 */
    private static final String CLIENT_TYPE = "CLIENT_CREDENTIALS";

    private static final String CLIENT_ID_PARAM = "client_id";
    private static final String CLIENT_SECRET_PARAM = "client_secret";
    private static final String GRANT_TYPE_PARAM = "grant_type";
    private static final String REDIRECT_URI_PARAM = "redirect_uri";



    /**
     * 请求的携带认证信息的参数
     */
    private static final String AUTH_PARAM_NAME = "access_token";
    private static final String REFRESH_TOKEN_NAME = "refresh_token";

    /**
     * 请求头内容的前缀
     */
    private static final String AUTH_HEADER_BEARER = "Bearer ";

    /**
     * 请求头名称
     */
    private static final String AUTH_HEADER_NAME = "authentication";

    @RequestMapping
    public P6eModel def(HttpServletRequest request, P6eTokenParam param) {
        if (param == null) {
            param = new P6eTokenParam();
        }
        param.setClientId(request.getParameter(CLIENT_ID_PARAM));
        param.setClientSecret(request.getParameter(CLIENT_SECRET_PARAM));
        param.setRedirectUri(request.getParameter(REDIRECT_URI_PARAM));
        param.setGrantType(request.getParameter(GRANT_TYPE_PARAM));
        if (param.getClientId() == null
                || param.getClientSecret() == null
                || param.getRedirectUri() == null
                || param.getGrantType() == null) {
            return P6eModel.build(P6eModel.Error.PARAMETER_EXCEPTION);
        } else {
            P6eAuthTokenDto p6eAuthTokenDto;
            switch (param.getGrantType().toUpperCase()) {
                case AUTH_CODE_TYPE:
                    // code 执行模式
                    p6eAuthTokenDto = P6eApplication.auth.codeCallback(P6eCopyUtil.run(param, P6eCodeCallbackAuthDto.class));
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
                    p6eAuthTokenDto = P6eApplication.auth.client(P6eCopyUtil.run(param, P6eClientAuthDto.class));
                    break;
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

    @RequestMapping("refresh")
    public P6eModel refresh(HttpServletRequest request) {
        String token = request.getParameter(AUTH_PARAM_NAME);
        String refreshToken = request.getParameter(REFRESH_TOKEN_NAME);
        if (token == null) {
            final String content = request.getHeader(AUTH_HEADER_NAME);
            if (content != null && content.startsWith(AUTH_HEADER_BEARER)) {
                token = content.substring(7);
            }
        }
        // 更具 token 获取用户数据
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
