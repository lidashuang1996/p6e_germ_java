package com.p6e.germ.oauth2.context.controller;

import com.p6e.germ.oauth2.application.P6eApplication;
import com.p6e.germ.oauth2.context.controller.support.P6eBaseController;
import com.p6e.germ.oauth2.model.P6eModel;
import com.p6e.germ.oauth2.model.dto.P6eInfoDto;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 获取信息的接口
 * @author lidashuang
 * @version 1.0
 */
@RestController
@RequestMapping("/info")
public class P6eInfoController extends P6eBaseController {

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
    public P6eModel def(final HttpServletRequest request) {
        String token = request.getParameter(AUTH_PARAM_NAME);
        if (token == null) {
            final String content = request.getHeader(AUTH_HEADER_NAME);
            if (content != null && content.startsWith(AUTH_HEADER_BEARER)) {
                token = content.substring(7);
            }
        }
        // 更具 token 获取用户数据
        if (token != null) {
            final P6eInfoDto p6eInfoDto = P6eApplication.auth.info(token);
            if (p6eInfoDto.getError() == null) {
                return P6eModel.build().setData(p6eInfoDto.getData());
            } else {
                return P6eModel.build(p6eInfoDto.getError());
            }
        }
        return P6eModel.build(P6eModel.Error.PARAMETER_EXCEPTION);
    }

}

