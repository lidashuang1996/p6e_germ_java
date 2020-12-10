package com.p6e.germ.oauth2.context.controller;

import com.p6e.germ.oauth2.application.P6eApplication;
import com.p6e.germ.oauth2.context.support.P6eBaseController;
import com.p6e.germ.oauth2.context.support.model.*;
import com.p6e.germ.oauth2.infrastructure.utils.P6eCopyUtil;
import com.p6e.germ.oauth2.infrastructure.utils.P6eJsonUtil;
import com.p6e.germ.oauth2.model.P6eModel;
import com.p6e.germ.oauth2.model.dto.*;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登陆服务的接口
 * @author lidashuang
 * @version 1.0
 */
@RestController
public class P6eLoginController extends P6eBaseController {

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

    @GetMapping("/login/code/generate")
    public P6eModel generateCode(P6eCodeLoginParam param) {
        final P6eGenerateCodeLoginDto p6eGenerateCodeLoginDto = P6eApplication.login.generateCode(param.getMark());
        if (p6eGenerateCodeLoginDto.getError() == null) {
            return P6eModel.build().setData(P6eCopyUtil.run(p6eGenerateCodeLoginDto, P6eCodeLoginResult.class));
        } else {
            return P6eModel.build(p6eGenerateCodeLoginDto.getError());
        }
    }

    @GetMapping("/login/code/get")
    public P6eModel getCode(P6eCodeLoginParam param) {
        final P6eLoginDto p6eLoginDto = P6eApplication.login.getCodeLogin(param.getCode());
        if (p6eLoginDto.getError() == null) {
            if (p6eLoginDto.getAccessToken() == null) {
                return P6eModel.build();
            } else {
                return P6eModel.build().setData(P6eCopyUtil.run(p6eLoginDto, P6eDefaultLoginResult.class));
            }
        } else {
            return P6eModel.build(p6eLoginDto.getError());
        }
    }

    @RequestMapping("/voucher")
    public P6eModel voucher() {
        final P6eVoucherDto p6eVoucherDto = P6eApplication.login.defaultVoucher();
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

    @RequestMapping("/login/qq")
    public void qqLogin(HttpServletResponse response, P6eQqLoginParam param) throws Exception {
        final P6eUrlLoginDto p6eUrlLoginDto = P6eApplication.login.qqInfo(param.getMark(), param.getDisplay());
        if (p6eUrlLoginDto.getError() == null) {
            // 重定向 URL
            response.sendRedirect(p6eUrlLoginDto.getUrl());
        } else {
            // 写入返回的数据
            response.getWriter().write(P6eJsonUtil.toJson(P6eModel.build(p6eUrlLoginDto.getError())));
            response.getWriter().flush();
            response.getWriter().close();
        }
    }

    @RequestMapping("/login/qq/callback")
    public void qqLoginCallback(P6eQqCallbackLoginParam param,
                                    HttpServletRequest request, HttpServletResponse response) throws Exception {
        final String dataType = "data";
        if (dataType.equals(param.getType())) {
            // 通过首页发送请求，然后跳转
            final P6eModel p6eModel;
            final P6eLoginDto p6eLoginDto = P6eApplication.login.qqLogin(P6eCopyUtil.run(param, P6eQqLoginDto.class));
            if (p6eLoginDto.getError() == null) {
                p6eModel = P6eModel.build().setData(P6eCopyUtil.run(p6eLoginDto, P6eVerificationLoginResult.class));
            } else {
                p6eModel = P6eModel.build(p6eLoginDto.getError());
            }
            // 写入返回的数据
            response.getWriter().write(P6eJsonUtil.toJson(p6eModel));
            response.getWriter().flush();
            response.getWriter().close();
        } else {
            // 回到首页
            request.getRequestDispatcher("/index.html?code="
                    + param.getCode() + "&state=" + param.getState()).forward(request, response);
        }
    }

}
