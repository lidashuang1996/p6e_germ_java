package com.p6e.germ.oauth2.context.rest;

import com.p6e.germ.common.utils.P6eCopyUtil;
import com.p6e.germ.oauth2.application.P6eApplication;
import com.p6e.germ.oauth2.context.controller.support.P6eBaseController;
import com.p6e.germ.oauth2.model.P6eQrCodeModel;
import com.p6e.germ.oauth2.model.P6eResultModel;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lidashuang
 * @version 1.0
 */
@RestController
@RequestMapping("/context/login")
public class P6eLoginContext extends P6eBaseController {

    @RequestMapping("/test")
    public P6eResultModel test(P6eQrCodeModel.VoParam param) {
        final String accessToken = getAccessToken(param.getAccessToken());
        try {
            // 写入用户信息
            final P6eQrCodeModel.DtoResult result = P6eApplication.login.qrCodeLogin(
                    P6eCopyUtil.run(param, P6eQrCodeModel.DtoParam.class).setAccessToken(accessToken));
            if (result == null) {
                return P6eResultModel.build(P6eResultModel.Error.SERVICE_EXCEPTION);
            } else if (result.getError() != null) {
                return P6eResultModel.build(result.getError());
            } else {
                return P6eResultModel.build(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            return P6eResultModel.build(P6eResultModel.Error.SERVICE_EXCEPTION);
        }
    }




}
