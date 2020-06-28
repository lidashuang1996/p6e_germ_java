package com.p6e.germ.oauth2.controller;

import com.p6e.germ.oauth2.controller.support.P6eBaseController;
import com.p6e.germ.oauth2.model.P6eResultConfig;
import com.p6e.germ.oauth2.model.P6eResultModel;
import com.p6e.germ.oauth2.model.dto.P6eAuthParamDto;
import com.p6e.germ.oauth2.model.dto.P6eAuthResultDto;
import com.p6e.germ.oauth2.model.vo.P6eAuthCodeResultVo;
import com.p6e.germ.oauth2.model.vo.P6eTokenParamVo;
import com.p6e.germ.oauth2.service.P6eAuthService;
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

    private static final String AUTH_CODE = "AUTHORIZATION_CODE";

    @Resource
    private P6eAuthService p6eAuthService;

    public P6eResultModel def(P6eTokenParamVo param) {
        try {
            if (param == null
                    || param.getClient_id() == null
                    || param.getClient_secret() == null
                    || param.getCode() == null
                    || param.getGrant_type() == null
                    || param.getRedirect_uri() == null) {
                return P6eResultModel.build(P6eResultConfig.ERROR_PARAM_EXCEPTION);
            } else {
                switch (param.getGrant_type().toUpperCase()) {
                    case AUTH_CODE:
                        P6eAuthParamDto p6eAuthParamDto = new P6eAuthParamDto();
                        p6eAuthParamDto.setCode(param.getCode());
                        // code换取用户的信息
                        P6eAuthResultDto p6eAuthResultDto = p6eAuthService.collateCode(p6eAuthParamDto);
                        if (p6eAuthResultDto.getData() == null) {
                            return P6eResultModel.build(P6eResultConfig.ERROR_SERVICE_INSIDE);
                        } else {
                            // 写入 token 数据
                            P6eAuthCodeResultVo p6eAuthCodeResultVo = new P6eAuthCodeResultVo();
                            return P6eResultModel.build(P6eResultConfig.SUCCESS, p6eAuthCodeResultVo);
                        }
                    case "2":
                    default:
                        break;
                }
                return P6eResultModel.build(P6eResultConfig.ERROR_SERVICE_INSIDE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return P6eResultModel.build(P6eResultConfig.ERROR_SERVICE_INSIDE);
        }
    }


}
