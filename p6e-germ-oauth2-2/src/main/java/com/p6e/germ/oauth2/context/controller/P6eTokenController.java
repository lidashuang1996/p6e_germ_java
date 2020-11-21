package com.p6e.germ.oauth2.context.controller;

import com.p6e.germ.oauth2.application.P6eAuthService;
import com.p6e.germ.oauth2.application.P6eLoginService;
import com.p6e.germ.oauth2.context.controller.support.P6eBaseController;
import com.p6e.germ.oauth2.context.controller.support.model.P6eDefaultLoginParam;
import com.p6e.germ.oauth2.context.controller.support.model.P6eModelConfig;
import com.p6e.germ.oauth2.context.controller.support.model.P6eResultModel;
import com.p6e.germ.oauth2.context.controller.support.model.P6eTokenModelParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author lidashuang
 * @version 1.0
 */
@RestController
@RequestMapping("/token")
public class P6eTokenController extends P6eBaseController {

    private static final String AUTH_CODE_TYPE = "AUTHORIZATION_CODE";

    @Resource
    private P6eAuthService p6eAuthService;

    /**
     *
     * http://127.0.0.1:9900/token?client_id=1234567890&grant_type=AUTHORIZATION_CODE&redirect_uri=http://127.0.0.1:10000&client_secret=1234567890&code=8ada3b01ddf448799279ec0969ccd0ed
     * @return
     */
    @RequestMapping
    public P6eResultModel def(P6eTokenModelParam param) {
        try {
            if (param == null
                    || param.getClient_id() == null
                    || param.getClient_secret() == null
                    || param.getCode() == null
                    || param.getRedirect_uri() == null
                    || param.getGrant_type() == null) {
                return P6eResultModel.build(P6eModelConfig.ERROR_PARAM_EXCEPTION);
            } else {
                switch (param.getGrant_type().toUpperCase()) {
                    case AUTH_CODE_TYPE:
                        return P6eResultModel.build(P6eModelConfig.SUCCESS, p6eAuthService.codeModeExecute(param));
                    default:
                        break;
                }
                // http://localhost:8001/oauth/token?username=liuzj&password=123&grant_type=password&client_id=client&client_secret=123456 （POST）
                // password 模式一次发过来
                return P6eResultModel.build(P6eModelConfig.ERROR_SERVICE_INSIDE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            return P6eResultModel.build(P6eModelConfig.ERROR_SERVICE_INSIDE);
        }
    }
}
