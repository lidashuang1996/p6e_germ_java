package com.p6e.germ.oauth2.controller;

import com.p6e.germ.oauth2.controller.support.P6eBaseController;
import com.p6e.germ.oauth2.model.P6eOauth2Type;
import com.p6e.germ.oauth2.model.P6eResultConfig;
import com.p6e.germ.oauth2.model.P6eResultModel;
import com.p6e.germ.oauth2.model.dto.P6eAuthParamDto;
import com.p6e.germ.oauth2.model.vo.P6eAuthParamVo;
import com.p6e.germ.oauth2.service.P6eAuthService;
import com.p6e.germ.oauth2.utils.CopyUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 该类为 oauth2 认证的接口
 * @author lidashuang
 * @version 1.0
 */
@RestController
@RequestMapping("/auth")
public class P6eAuthController extends P6eBaseController {

    @Resource
    private P6eAuthService p6eAuthService;

    @RequestMapping
    public P6eResultModel def(P6eAuthParamVo param) {
        try {
            if (param == null) {
                // 参数异常
                return P6eResultModel.build(P6eResultConfig.ERROR_PARAM_EXCEPTION);
            } else {
                final String responseType = param.getResponseType();
                if (responseType == null) {
                    // code 参数异常
                    return P6eResultModel.build(P6eResultConfig.ERROR_PARAM_EXCEPTION);
                } else {
                    switch (responseType.toUpperCase()) {
                        case "CODE":
                            p6eAuthService.code(CopyUtil.run(param, P6eAuthParamDto.class));
                            break;
                        default:
                            break;
                    }
                }
            }
            return P6eResultModel.build(P6eResultConfig.ERROR_SERVICE_INSIDE);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return P6eResultModel.build(P6eResultConfig.ERROR_SERVICE_INSIDE);
        }
    }

}
