package com.p6e.germ.oauth2.context.controller;

import com.p6e.germ.oauth2.application.P6eApplication;
import com.p6e.germ.oauth2.context.controller.support.P6eBaseController;
import com.p6e.germ.oauth2.model.P6eResultModel;
import com.p6e.germ.oauth2.model.P6eInfoAuthModel;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 获取信息的接口
 * @author lidashuang
 * @version 1.0
 */
@RestController
@RequestMapping("/info")
public class P6eInfoController extends P6eBaseController {

    @RequestMapping
    public P6eResultModel def(P6eInfoAuthModel.VoParam param) {
        try {
            // 读取参数
            final String token = getAccessToken(param.getAccessToken());
            // 通过 token 获取用户数据
            if (token == null) {
                return P6eResultModel.build(P6eResultModel.Error.OAUTH2_TOKEN_AUTH_EXCEPTION);
            } else {
                final P6eInfoAuthModel.DtoResult result =
                        P6eApplication.auth.info(new P6eInfoAuthModel.DtoParam().setAccessToken(token));
                if (result == null) {
                    return P6eResultModel.build(P6eResultModel.Error.SERVICE_EXCEPTION);
                } else if (result.getError() != null) {
                    return P6eResultModel.build(result.getError());
                } else {
                    return P6eResultModel.build(result.getData());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            return P6eResultModel.build(P6eResultModel.Error.SERVICE_EXCEPTION);
        }
    }

}

