package com.p6e.germ.oauth2.context.controller;

import com.p6e.germ.oauth2.application.P6eAuthService;
import com.p6e.germ.oauth2.context.controller.support.P6eBaseController;
import com.p6e.germ.oauth2.context.controller.support.model.P6eDefaultLoginParam;
import com.p6e.germ.oauth2.context.controller.support.model.P6eModelConfig;
import com.p6e.germ.oauth2.context.controller.support.model.P6eResultModel;
import com.p6e.germ.oauth2.context.controller.support.model.P6eTokenModelParam;
import com.p6e.germ.oauth2.domain.keyvalue.P6eClientModeKeyValue;
import com.p6e.germ.oauth2.domain.keyvalue.P6ePasswordModeKeyValue;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author lidashuang
 * @version 1.0
 */
@RestController
@RequestMapping("/token")
public class P6eTokenController extends P6eBaseController {

    private static final String PASSWORD_TYPE = "PASSWORD";
    private static final String AUTH_CODE_TYPE = "AUTHORIZATION_CODE";
    private static final String CLIENT_TYPE = "CLIENT_CREDENTIALS";
    private static final String REFRESH_TOKEN_TYPE  = "REFRESH_TOKEN";

    @Resource
    private P6eAuthService p6eAuthService;

    /**
     * http://127.0.0.1:9900/token?client_id=1234567890&grant_type=CLIENT_CREDENTIALS&redirect_uri=http://127.0.0.1:10000&client_secret=1234567890
     *
     *http://127.0.0.1:9900/token?client_id=1234567890&grant_type=password&redirect_uri=http://127.0.0.1:10000&client_secret=1234567890&account=15549562863&password=123456
     *
     * http://127.0.0.1:9900/token?client_id=1234567890&grant_type=AUTHORIZATION_CODE&redirect_uri=http://127.0.0.1:10000&client_secret=1234567890&code=8ada3b01ddf448799279ec0969ccd0ed
     * @return
     */
    @RequestMapping
    public P6eResultModel def(final HttpServletRequest request, final P6eTokenModelParam param) {
        try {
            if (param == null
                    || param.getClient_id() == null
                    || param.getClient_secret() == null
                    || param.getRedirect_uri() == null
                    || param.getGrant_type() == null) {
                return P6eResultModel.build(P6eModelConfig.ERROR_PARAM_EXCEPTION);
            } else {
                switch (param.getGrant_type().toUpperCase()) {
                    case AUTH_CODE_TYPE:
                        // code 执行模式
                        return P6eResultModel.build(P6eModelConfig.SUCCESS, p6eAuthService.codeModeExecute(param));
                    case PASSWORD_TYPE:
                        // password 执行模式
//                        if (HttpMethod.POST.name().equals(request.getMethod().toUpperCase())) {
                            return P6eResultModel.build(P6eModelConfig.SUCCESS, p6eAuthService.passwordModeExecute(
                                    new P6ePasswordModeKeyValue(
                                            param.getAccount(),
                                            param.getPassword(),
                                            param.getGrant_type(),
                                            param.getRedirect_uri(),
                                            param.getClient_id(),
                                            param.getClient_secret(),
                                            param.getScope()
                                    )));
//                        } else {
//                            return P6eResultModel.build(P6eModelConfig.ERROR_SERVICE_INSIDE);
//                        }
                    case CLIENT_TYPE:
                        return P6eResultModel.build(P6eModelConfig.SUCCESS, p6eAuthService.clientModeExecute(
                                new P6eClientModeKeyValue(
                                        param.getGrant_type(),
                                        param.getRedirect_uri(),
                                        param.getClient_id(),
                                        param.getClient_secret(),
                                        param.getScope()
                                )));
                    case REFRESH_TOKEN_TYPE:

                        break;
                    default:
                        break;
                }
                return P6eResultModel.build(P6eModelConfig.ERROR_SERVICE_INSIDE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            return P6eResultModel.build(P6eModelConfig.ERROR_SERVICE_INSIDE);
        }
    }
}
