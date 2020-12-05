package com.p6e.germ.oauth2.context.controller;

import com.p6e.germ.oauth2.application.P6eApplication;
import com.p6e.germ.oauth2.context.support.P6eBaseController;
import com.p6e.germ.oauth2.context.support.model.P6eAuthModelParam;
import com.p6e.germ.oauth2.context.support.model.P6eAuthModelResult;
import com.p6e.germ.oauth2.infrastructure.utils.P6eCopyUtil;
import com.p6e.germ.oauth2.model.P6eModel;
import com.p6e.germ.oauth2.model.dto.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Oauth2 接口
 * @author lidashuang
 * @version 1.0
 */
@RestController
@RequestMapping("/auth")
public class P6eAuthController extends P6eBaseController {

    /**
     * CODE 认证类型
     */
    private static final String CODE_TYPE = "CODE";

    /**
     * 简化认证类型
     */
    private static final String TOKEN_TYPE = "TOKEN";

    /**
     * 客户端参数名称
     */
    private static final String CLIENT_ID_PARAM = "client_id";

    /**
     * 重定向 URL 参数名称
     */
    private static final String REDIRECT_URI_PARAM = "redirect_uri";

    /**
     * 资源类型参数名称
     */
    private static final String RESPONSE_TYPE_PARAM = "response_type";

    /**
     * 请求的携带认证信息的参数
     */
    private static final String AUTH_PARAM_NAME = "access_token";

    /**
     * 请求头内容的前缀
     */
    private static final String AUTH_HEADER_BEARER = "Bearer ";

    /**
     * 请求头名称
     */
    private static final String AUTH_HEADER_NAME = "authentication";


    @RequestMapping
    public P6eModel def(HttpServletRequest request, P6eAuthModelParam param) {
        // 写入数据
        param.setClientId(request.getParameter(CLIENT_ID_PARAM));
        param.setRedirectUri(request.getParameter(REDIRECT_URI_PARAM));
        param.setResponseType(request.getParameter(RESPONSE_TYPE_PARAM));
        if (param.getScope() == null
                || param.getClientId() == null
                || param.getRedirectUri() == null
                || param.getResponseType() == null) {
            // 参数异常
            return P6eModel.build(P6eModel.Error.PARAMETER_EXCEPTION);
        } else {
            final P6eAuthDto p6eAuthDto;
            final String responseType = param.getResponseType();
            switch (responseType.toUpperCase()) {
                case CODE_TYPE:
                    // CODE 的认证模式
                    p6eAuthDto = P6eApplication.auth.code(P6eCopyUtil.run(param, P6eCodeAuthDto.class));
                    break;
                case TOKEN_TYPE:
                    // 简化模式
                    p6eAuthDto = P6eApplication.auth.simple(P6eCopyUtil.run(param, P6eSimpleAuthDto.class));
                    break;
                default:
                    return P6eModel.build(P6eModel.Error.PARAMETER_EXCEPTION);
            }
            if (p6eAuthDto.getError() == null) {
                return P6eModel.build().setData(P6eCopyUtil.run(p6eAuthDto, P6eAuthModelResult.class));
            } else {
                return P6eModel.build(p6eAuthDto.getError());
            }
        }
    }

    @RequestMapping("/verification")
    public P6eModel verification(HttpServletRequest request, P6eAuthModelParam param) {
        // 写入数据
        param.setClientId(request.getParameter(CLIENT_ID_PARAM));
        param.setRedirectUri(request.getParameter(REDIRECT_URI_PARAM));
        param.setResponseType(request.getParameter(RESPONSE_TYPE_PARAM));
        if (param.getScope() == null
                || param.getClientId() == null
                || param.getRedirectUri() == null
                || param.getResponseType() == null) {
            // 参数异常
            return P6eModel.build(P6eModel.Error.PARAMETER_EXCEPTION);
        } else {
            // 验证的参数的数据并返回的验证后的数据信息
            final P6eAuthDto p6eAuthDto =
                    P6eApplication.auth.verification(P6eCopyUtil.run(param, P6eVerificationAuthDto.class));
            if (p6eAuthDto.getError() == null) {
                return P6eModel.build().setData(P6eCopyUtil.run(p6eAuthDto, P6eAuthModelResult.class));
            } else {
                return P6eModel.build(p6eAuthDto.getError());
            }
        }
    }
}
