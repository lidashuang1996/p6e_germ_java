package com.p6e.germ.oauth2.context.controller;

import com.p6e.germ.common.utils.P6eCopyUtil;
import com.p6e.germ.oauth2.application.P6eApplication;
import com.p6e.germ.oauth2.context.controller.support.P6eBaseController;
import com.p6e.germ.oauth2.model.P6eResultModel;
import com.p6e.germ.oauth2.model.P6eInfoAuthModel;
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
    public P6eResultModel def(HttpServletRequest request,
                              P6eInfoAuthModel.VoParam param) {
        try {
            // 读取参数
            String token = param.getAccessToken();
            if (token == null) {
                // 请求参数中读取 token 参数
                token = request.getParameter(AUTH_PARAM_NAME);
                if (token == null) {
                    // 头部里面读取 token 参数
                    final String content = request.getHeader(AUTH_HEADER_NAME);
                    if (content != null && content.startsWith(AUTH_HEADER_BEARER)) {
                        token = content.substring(AUTH_HEADER_BEARER_LENGTH);
                    }
                }
            }
            // 通过 token 获取用户数据
            if (token != null) {
                final P6eInfoAuthModel.DtoResult result =
                        P6eApplication.auth.info(new P6eInfoAuthModel.DtoParam().setAccessToken(token));
                if (result == null) {
                    return P6eResultModel.build(P6eResultModel.Error.PARAMETER_EXCEPTION);
                } else if (result.getError() != null) {
                    return P6eResultModel.build(result.getError());
                } else {
                    final  P6eInfoAuthModel.VoResult r = P6eCopyUtil.run(result, P6eInfoAuthModel.VoResult.class);
                    return P6eResultModel.build(r == null ? null : r.getData());
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

