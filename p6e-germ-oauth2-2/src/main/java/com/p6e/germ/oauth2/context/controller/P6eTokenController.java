package com.p6e.germ.oauth2.context.controller;

import com.p6e.germ.common.utils.P6eCopyUtil;
import com.p6e.germ.oauth2.application.P6eApplication;
import com.p6e.germ.oauth2.context.controller.support.P6eBaseController;
import com.p6e.germ.oauth2.model.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 令牌认证接口
 * @author lidashuang
 * @version 1.0
 */
@Api("Oauth2认证入口")
@RestController
@RequestMapping("/token")
public class P6eTokenController extends P6eBaseController {

    @ApiOperation(
            value = "验证传入的数据是否为客户端数据，正确则返回登录的页面的，否则错误提示"
    )
    @RequestMapping
    public P6eResultModel def(HttpServletRequest request, P6eAuthTokenModel.VoParam param) {
        try {
            if (param.getClientId() == null) {
                param.setClientId(request.getParameter(CLIENT_ID_PARAM));
            }
            if (param.getGrantType() == null) {
                param.setGrantType(request.getParameter(GRANT_TYPE_PARAM));
            }
            if (param.getRedirectUri() == null) {
                param.setRedirectUri(request.getParameter(REDIRECT_URI_PARAM));
            }
            if (param.getClientSecret() == null) {
                param.setClientSecret(request.getParameter(CLIENT_SECRET_PARAM));
            }
            if (param.getClientId() == null || param.getGrantType() == null) {
                return P6eResultModel.build(P6eResultModel.Error.PARAMETER_EXCEPTION);
            } else {
                final P6eAuthTokenModel.DtoResult p6eAuthTokenDto;
                switch (param.getGrantType().toUpperCase()) {
                    case AUTH_CODE_TYPE:
                        // CODE 执行模式
                        if (HttpMethod.GET.name().equals(request.getMethod().toUpperCase())
                                || HttpMethod.POST.name().equals(request.getMethod().toUpperCase())) {
                            if (param.getCode() == null
                                    || param.getRedirectUri() == null
                                    || param.getClientSecret() == null) {
                                return P6eResultModel.build(P6eResultModel.Error.PARAMETER_EXCEPTION);
                            } else {
                                p6eAuthTokenDto = P6eApplication.auth.code(
                                        P6eCopyUtil.run(param, P6eAuthTokenModel.DtoParam.class));
                                break;
                            }
                        } else {
                            return P6eResultModel.build(P6eResultModel.Error.HTTP_METHOD_EXCEPTION);
                        }
                    case PASSWORD_TYPE:
                        // PASSWORD 执行模式
                        if (HttpMethod.POST.name().equals(request.getMethod().toUpperCase())) {
                            if (param.getUsername() == null || param.getPassword() == null) {
                                return P6eResultModel.build(P6eResultModel.Error.PARAMETER_EXCEPTION);
                            } else {
                                p6eAuthTokenDto = P6eApplication.auth.password(
                                        P6eCopyUtil.run(param, P6eAuthTokenModel.DtoParam.class));
                            }
                            break;
                        } else {
                            return P6eResultModel.build(P6eResultModel.Error.HTTP_METHOD_EXCEPTION);
                        }
                    case CLIENT_TYPE:
                        // 客户端执行模式
                        if (HttpMethod.POST.name().equals(request.getMethod().toUpperCase())) {
                            if (param.getClientSecret() == null) {
                                return P6eResultModel.build(P6eResultModel.Error.PARAMETER_EXCEPTION);
                            } else {
                                p6eAuthTokenDto = P6eApplication.auth.client(
                                        P6eCopyUtil.run(param, P6eAuthTokenModel.DtoParam.class));
                            }
                            break;
                        } else {
                            return P6eResultModel.build(P6eResultModel.Error.HTTP_METHOD_EXCEPTION);
                        }
                    default:
                        return P6eResultModel.build(P6eResultModel.Error.PARAMETER_EXCEPTION);
                }
                if (p6eAuthTokenDto == null) {
                    return P6eResultModel.build(P6eResultModel.Error.SERVICE_EXCEPTION);
                } else if (p6eAuthTokenDto.getError() != null) {
                    return P6eResultModel.build(p6eAuthTokenDto.getError());
                } else {
                    return P6eResultModel.build().setData(P6eCopyUtil.run(p6eAuthTokenDto, P6eTokenResult.class));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            return P6eResultModel.build(P6eResultModel.Error.SERVICE_EXCEPTION);
        }
    }

    @GetMapping("/refresh")
    public P6eResultModel refresh(HttpServletRequest request, P6eAuthTokenModel.VoParam param) {
        try {
            String accessToken, refreshToken;
            if (param.getAccessToken() == null) {
                accessToken = request.getParameter(AUTH_PARAM_NAME);
            } else {
                accessToken = param.getAccessToken();
            }
            if (param.getRefreshToken() == null) {
                refreshToken = request.getParameter(REFRESH_TOKEN_NAME);
            } else {
                refreshToken = param.getRefreshToken();
            }
            if (accessToken == null) {
                final String content = request.getHeader(AUTH_HEADER_NAME);
                if (content != null
                        && content.startsWith(AUTH_HEADER_BEARER)
                        && content.length() > AUTH_HEADER_BEARER_LENGTH) {
                    accessToken = content.substring(AUTH_HEADER_BEARER_LENGTH);
                }
            }
            // 通过 token 获取用户数据
            if (accessToken != null && refreshToken != null) {
                final P6eAuthTokenModel.DtoResult p6eAuthTokenDto =
                        P6eApplication.auth.refresh(P6eCopyUtil.run(param, P6eAuthTokenModel.DtoParam.class));
                if (p6eAuthTokenDto == null) {
                    return P6eResultModel.build(P6eResultModel.Error.SERVICE_EXCEPTION);
                } else if (p6eAuthTokenDto.getError() != null) {
                    return P6eResultModel.build(p6eAuthTokenDto.getError());
                } else {
                    return P6eResultModel.build(P6eCopyUtil.run(p6eAuthTokenDto, P6eTokenResult.class));
                }
            } else {
                return P6eResultModel.build(P6eResultModel.Error.PARAMETER_EXCEPTION);
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            return P6eResultModel.build(P6eResultModel.Error.SERVICE_EXCEPTION);
        }
    }

}
