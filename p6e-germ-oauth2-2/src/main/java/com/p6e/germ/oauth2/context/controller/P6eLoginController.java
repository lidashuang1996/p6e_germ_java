package com.p6e.germ.oauth2.context.controller;

import com.p6e.germ.oauth2.application.P6eLoginService;
import com.p6e.germ.oauth2.context.controller.support.P6eBaseController;
import com.p6e.germ.oauth2.context.controller.support.model.P6eDefaultLoginParam;
import com.p6e.germ.oauth2.context.controller.support.model.P6eModelConfig;
import com.p6e.germ.oauth2.context.controller.support.model.P6eResultModel;
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
@RequestMapping("/login")
public class P6eLoginController extends P6eBaseController {

    @Resource
    private P6eLoginService p6eLoginService;

    @PostMapping("/")
    public P6eResultModel def(@RequestBody P6eDefaultLoginParam param) {
        try {
            if (param == null
                    || param.getAccount() == null
                    || param.getPassword() == null
                    || param.getVoucher() == null
                    || param.getMark() == null) {
                return P6eResultModel.build(P6eModelConfig.ERROR_PARAM_EXCEPTION);
            } else {
                return P6eResultModel.build(P6eModelConfig.SUCCESS, p6eLoginService.defaultLogin(param));
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            return P6eResultModel.build(P6eModelConfig.ERROR_SERVICE_INSIDE);
        }
    }
}
