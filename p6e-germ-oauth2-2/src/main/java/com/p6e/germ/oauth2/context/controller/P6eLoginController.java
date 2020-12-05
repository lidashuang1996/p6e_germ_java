package com.p6e.germ.oauth2.context.controller;

import com.p6e.germ.oauth2.application.P6eApplication;
import com.p6e.germ.oauth2.context.controller.support.P6eBaseController;
import com.p6e.germ.oauth2.context.controller.support.model.*;
import com.p6e.germ.oauth2.infrastructure.utils.P6eCopyUtil;
import com.p6e.germ.oauth2.model.P6eModel;
import com.p6e.germ.oauth2.model.dto.P6eDefaultLoginDto;
import com.p6e.germ.oauth2.model.dto.P6eLoginDto;
import com.p6e.germ.oauth2.model.dto.P6eVerificationLoginDto;
import com.p6e.germ.oauth2.model.dto.P6eVoucherDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 登陆服务的接口
 * @author lidashuang
 * @version 1.0
 */
@RestController
public class P6eLoginController extends P6eBaseController {

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

    @PostMapping("/login/")
    public P6eModel def(@RequestBody P6eDefaultLoginParam param) {
        final P6eLoginDto p6eLoginDto =
                P6eApplication.login.defaultLogin(P6eCopyUtil.run(param, P6eDefaultLoginDto.class));
        if (p6eLoginDto.getError() == null) {
            return P6eModel.build().setData(P6eCopyUtil.run(p6eLoginDto, P6eDefaultLoginResult.class));
        } else {
            return P6eModel.build(p6eLoginDto.getError());
        }
    }

    @RequestMapping("/voucher")
    public P6eModel voucher() {
        final P6eVoucherDto p6eVoucherDto = P6eApplication.login.defVoucher();
        if (p6eVoucherDto.getError() == null) {
            return P6eModel.build().setData(P6eCopyUtil.run(p6eVoucherDto, P6eVoucherResult.class));
        } else {
            return P6eModel.build(p6eVoucherDto.getError());
        }
    }

    @RequestMapping("/login/verification")
    public P6eModel verification(HttpServletRequest request, P6eVerificationLoginParam param) {
        // 读取 ACCESS TOKEN 数据
        String token = request.getParameter(AUTH_PARAM_NAME);
        if (token == null) {
            final String content = request.getHeader(AUTH_HEADER_NAME);
            if (content != null && content.startsWith(AUTH_HEADER_BEARER)) {
                token = content.substring(7);
            }
        }
        // 执行登录验证服务
        final P6eLoginDto p6eLoginDto = P6eApplication.login.verification(
                P6eCopyUtil.run(param, P6eVerificationLoginDto.class).setAccessToken(token));
        if (p6eLoginDto.getError() == null) {
            return P6eModel.build().setData(P6eCopyUtil.run(p6eLoginDto, P6eVerificationLoginResult.class));
        } else {
            return P6eModel.build(p6eLoginDto.getError());
        }
    }
}
