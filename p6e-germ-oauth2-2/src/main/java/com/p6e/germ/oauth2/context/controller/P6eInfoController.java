package com.p6e.germ.oauth2.context.controller;

import com.p6e.germ.oauth2.application.P6eApplication;
import com.p6e.germ.oauth2.context.controller.support.P6eBaseController;
import com.p6e.germ.oauth2.model.P6eResultModel;
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

    @RequestMapping
    public P6eResultModel def(HttpServletRequest request) {
        // 请求参数中读取 token 参数
        String token = request.getParameter(AUTH_PARAM_NAME);
        if (token == null) {
            // 头部里面读取 token 参数
            final String content = request.getHeader(AUTH_HEADER_NAME);
            if (content != null && content.startsWith(AUTH_HEADER_BEARER)) {
                token = content.substring(7);
            }
        }
        // 通过 token 获取用户数据
        if (token != null) {
            final P6eInfoDto p6eInfoDto = P6eApplication.auth.info(token);
            if (p6eInfoDto.getError() == null) {
                return P6eResultModel.build().setData(p6eInfoDto.getData());
            } else {
                return P6eResultModel.build(p6eInfoDto.getError());
            }
        }
        return P6eResultModel.build(P6eResultModel.Error.PARAMETER_EXCEPTION);
    }

}

