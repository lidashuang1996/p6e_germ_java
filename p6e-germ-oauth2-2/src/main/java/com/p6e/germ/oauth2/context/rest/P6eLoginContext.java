package com.p6e.germ.oauth2.context.rest;

import com.p6e.germ.oauth2.application.P6eApplication;
import com.p6e.germ.oauth2.context.support.model.P6eAuthResult;
import com.p6e.germ.oauth2.context.support.model.P6eCodeLoginParam;
import com.p6e.germ.oauth2.context.support.model.P6eDefaultLoginResult;
import com.p6e.germ.oauth2.infrastructure.utils.P6eCopyUtil;
import com.p6e.germ.oauth2.model.P6eModel;
import com.p6e.germ.oauth2.model.dto.P6eAuthDto;
import com.p6e.germ.oauth2.model.dto.P6eCodeLoginDto;
import com.p6e.germ.oauth2.model.dto.P6eLoginDto;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author lidashuang
 * @version 1.0
 */
@RestController
@RequestMapping("/context/login")
public class P6eLoginContext {

    /**
     * 请求的携带认证信息的参数
     */
    private static final String AUTH_PARAM_NAME = "access_token";

    @RequestMapping("/mark/{id}")
    public P6eModel mark(@PathVariable("id") String mark) {
        final P6eAuthDto p6eAuthDto = P6eApplication.auth.mark(mark);
        if (p6eAuthDto.getError() == null) {
            return P6eModel.build().setData(P6eCopyUtil.run(p6eAuthDto, P6eAuthResult.class));
        } else {
            return P6eModel.build(p6eAuthDto.getError());
        }
    }

    @RequestMapping("/code")
    public P6eModel code(HttpServletRequest request, P6eCodeLoginParam param) {
        final String accessToken = request.getParameter(AUTH_PARAM_NAME);
        if (param == null) {
            param = new P6eCodeLoginParam();
        }
        if (param.getAccessToken() == null) {
            param.setAccessToken(accessToken);
        }
        final P6eLoginDto p6eLoginDto = P6eApplication.login.codeLogin(P6eCopyUtil.run(param, P6eCodeLoginDto.class));
        if (p6eLoginDto.getError() == null) {
            return P6eModel.build().setData(P6eCopyUtil.run(p6eLoginDto, P6eDefaultLoginResult.class));
        } else {
            return P6eModel.build(p6eLoginDto.getError());
        }
    }


}
