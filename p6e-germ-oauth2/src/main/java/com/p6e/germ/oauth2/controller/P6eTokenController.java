package com.p6e.germ.oauth2.controller;

import com.p6e.germ.oauth2.controller.support.P6eBaseController;
import com.p6e.germ.oauth2.model.P6eResultConfig;
import com.p6e.germ.oauth2.model.P6eResultModel;
import com.p6e.germ.oauth2.model.dto.*;
import com.p6e.germ.oauth2.model.vo.P6eTokenParamVo;
import com.p6e.germ.oauth2.service.P6eAuthService;
import com.p6e.germ.oauth2.service.P6eTokenService;
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

    private static final String PASSWORD_TYPE = "PASSWORD";

    @Resource
    private P6eAuthService p6eAuthService;

    @Resource
    private P6eTokenService p6eTokenService;

    @RequestMapping
    public P6eResultModel def(P6eTokenParamVo param) {
        try {
            if (param == null
                    || param.getClient_id() == null
                    || param.getClient_secret() == null
//                    || param.getCode() == null
//                    || param.getRedirect_uri() == null
                    || param.getGrant_type() == null) {
                return P6eResultModel.build(P6eResultConfig.ERROR_PARAM_EXCEPTION);
            } else {
                switch (param.getGrant_type().toUpperCase()) {
                    case AUTH_CODE_TYPE:
                        // 校验客户端的数据
                        P6eAuthCollateCodeParamDto p6eAuthCollateCodeParamDto = new P6eAuthCollateCodeParamDto();
                        p6eAuthCollateCodeParamDto.setCodeVoucher(param.getCode());
                        // code换取用户的信息
                        P6eAuthCollateCodeResultDto p6eAuthCollateCodeResultDto
                                = p6eAuthService.collateCode(p6eAuthCollateCodeParamDto);
                        if (p6eAuthCollateCodeResultDto.getData() == null) {
                            return P6eResultModel.build(P6eResultConfig.ERROR_SERVICE_INSIDE);
                        } else {
                            // 写入 token 数据
                            P6eTokenParamDto p6eTokenParamDto = new P6eTokenParamDto();
                            p6eTokenParamDto.setData(p6eAuthCollateCodeResultDto.getData());
                            P6eTokenResultDto p6eTokenResultDto = p6eTokenService.set(p6eTokenParamDto);
                            return P6eResultModel.build(P6eResultConfig.SUCCESS, p6eTokenResultDto);
                        }
                    case PASSWORD_TYPE:
                        // 验证账号密码
                        // 发送 token
                        // 校验客户端的数据
                        P6eAuthCollatePasswordParamDto
                                p6eAuthCollatePasswordParamDto = new P6eAuthCollatePasswordParamDto();
                        p6eAuthCollatePasswordParamDto.setAccount(param.getAccount());
                        p6eAuthCollatePasswordParamDto.setPassword(param.getPassword());
                        P6eAuthCollatePasswordResultDto p6eAuthCollatePasswordResultDto
                                = p6eAuthService.collatePassword(p6eAuthCollatePasswordParamDto);

                        if (p6eAuthCollatePasswordResultDto == null
                                || p6eAuthCollatePasswordResultDto.getData() == null) {
                            return P6eResultModel.build(P6eResultConfig.ERROR_ACCOUNT_OR_PASSWORD);
                        } else {
                            // 写入 token 数据
                            P6eTokenParamDto p6eTokenParamDto = new P6eTokenParamDto();
                            p6eTokenParamDto.setData(p6eAuthCollatePasswordResultDto.getData());
                            P6eTokenResultDto p6eTokenResultDto = p6eTokenService.set(p6eTokenParamDto);
                            return P6eResultModel.build(P6eResultConfig.SUCCESS, p6eTokenResultDto);
                        }
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
