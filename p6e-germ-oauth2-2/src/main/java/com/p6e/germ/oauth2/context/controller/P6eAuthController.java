package com.p6e.germ.oauth2.context.controller;

import com.p6e.germ.oauth2.application.P6eApplication;
import com.p6e.germ.oauth2.context.controller.support.P6eBaseController;
import com.p6e.germ.oauth2.context.controller.support.model.P6eAuthModelParam;
import com.p6e.germ.oauth2.context.controller.support.model.P6eAuthModelResult;
import com.p6e.germ.oauth2.context.controller.support.model.P6eDefaultLoginResult;
import com.p6e.germ.oauth2.infrastructure.utils.P6eCopyUtil;
import com.p6e.germ.oauth2.model.P6eModel;
import com.p6e.germ.oauth2.model.dto.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Oauth2 接口
 * @author lidashuang
 * @version 1.0
 */
@Controller
@RequestMapping("/auth")
public class P6eAuthController extends P6eBaseController {

    private static final String CODE_TYPE = "CODE";
    private static final String TOKEN_TYPE = "TOKEN";

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

    /**
     * 1. 客户端模式
     * 2. 授权码模式
     * 3. 密码模式
     * 4. 简化模式
     *
     * http://127.0.0.1:9900/auth?client_id=1234567890&response_type=code&redirect_uri=http://127.0.0.1:10000&scope=123&state=11111
     *
     *
     * // 简化模式
     * // // http://localhost:8888/oauth/authorize?client_id=cms&redirect_uri=http://127.0.0.1:8084/cms/login&response_type=token&scope=all
     *
     *
     * // 密码模式支持，但是只能查看你自己数据，也就是查询自己的 clien 信息
     * // http://localhost:8888/oauth/token?client_id=cms&client_secret=secret&grant_type=client_credentials&scope=all
     * @param param
     * @return
     */
    @RequestMapping
    public P6eModel def(HttpServletRequest request, P6eAuthModelParam param) {
        if (param == null
                || param.getScope() == null
                || param.getClientId() == null
                || param.getRedirectUri() == null
                || param.getResponseType() == null) {
            // 参数异常
            return P6eModel.build(P6eModel.Error.PARAMETER_EXCEPTION);
        } else {
            String token = request.getParameter(AUTH_PARAM_NAME);
            if (token == null) {
                final String content = request.getHeader(AUTH_HEADER_NAME);
                if (content != null && content.startsWith(AUTH_HEADER_BEARER)) {
                    token = content.substring(7);
                }
            }
            if (token != null) {
                // 读取浏览器数据，验证当前用户是否登录
                final P6eLoginDto p6eLoginDto =
                        P6eApplication.login.verification(P6eCopyUtil.run(param, P6eVerificationLoginDto.class));
                if (p6eLoginDto.getError() == null) {
                    return P6eModel.build().setData(P6eCopyUtil.run(p6eLoginDto, P6eDefaultLoginResult.class));
                } else {
                    return P6eModel.build(p6eLoginDto.getError());
                }
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
    }


}
