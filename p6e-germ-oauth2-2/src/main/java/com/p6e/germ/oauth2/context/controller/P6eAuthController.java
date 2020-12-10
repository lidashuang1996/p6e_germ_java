package com.p6e.germ.oauth2.context.controller;

import com.p6e.germ.oauth2.application.P6eApplication;
import com.p6e.germ.oauth2.context.support.P6eBaseController;
import com.p6e.germ.oauth2.context.support.model.P6eAuthParam;
import com.p6e.germ.oauth2.context.support.model.P6eAuthResult;
import com.p6e.germ.oauth2.infrastructure.utils.P6eCopyUtil;
import com.p6e.germ.oauth2.model.P6eModel;
import com.p6e.germ.oauth2.model.dto.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Oauth2 接口
 * @author lidashuang
 * @version 1.0
 */
@RestController
@RequestMapping("/auth")
public class P6eAuthController extends P6eBaseController {

    /**
     * 请求转发到首页
     */
    @RequestMapping
    public void def(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.getRequestDispatcher("/index.html").forward(request, response);
    }

    /**
     * 验证的参数数据，返回客户端信息
     */
    @RequestMapping("/verification")
    public P6eModel verification(HttpServletRequest request, P6eAuthParam param) {
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
                case TOKEN_TYPE:
                    // CODE 模式
                    // 简化模式
                    p6eAuthDto = P6eApplication.auth.verification(P6eCopyUtil.run(param, P6eVerificationAuthDto.class));
                    break;
                default:
                    return P6eModel.build(P6eModel.Error.PARAMETER_EXCEPTION);
            }
            if (p6eAuthDto.getError() == null) {
                return P6eModel.build().setData(P6eCopyUtil.run(p6eAuthDto, P6eAuthResult.class));
            } else {
                return P6eModel.build(p6eAuthDto.getError());
            }
        }
    }
}
