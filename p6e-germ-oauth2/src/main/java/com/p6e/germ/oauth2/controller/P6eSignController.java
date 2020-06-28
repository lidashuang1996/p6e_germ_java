package com.p6e.germ.oauth2.controller;

import com.p6e.germ.oauth2.controller.support.P6eBaseController;
import com.p6e.germ.oauth2.model.P6eResultConfig;
import com.p6e.germ.oauth2.model.P6eResultModel;
import com.p6e.germ.oauth2.model.dto.*;
import com.p6e.germ.oauth2.model.vo.P6eSignInParamVo;
import com.p6e.germ.oauth2.model.vo.P6eSignInResultVo;
import com.p6e.germ.oauth2.service.P6eAuthService;
import com.p6e.germ.oauth2.service.P6eSignService;
import com.p6e.germ.oauth2.service.P6eVoucherService;
import com.p6e.germ.oauth2.utils.CopyUtil;
import com.p6e.germ.oauth2.utils.GsonUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 用户登录的接口
 * @author lidashuang
 * @version 1.0
 */
@RestController
@RequestMapping("/sign")
public class P6eSignController extends P6eBaseController {

    @Resource
    private P6eSignService p6eSignService;

    @Resource
    private P6eAuthService p6eAuthService;

    @Resource
    private P6eVoucherService p6eVoucherService;


    @PostMapping("/in")
    public P6eResultModel in(@RequestBody P6eSignInParamVo param) {
        try {
            if (param == null
                    || param.getMode() == null
                    || param.getVoucher() == null
                    || param.getAccount() == null
                    || param.getPassword() == null) {
                // 参数异常
                return P6eResultModel.build(P6eResultConfig.ERROR_PARAM_EXCEPTION);
            } else {
                // 1. 读取信息
                P6eVoucherParamDto p6eVoucherParamDto = new P6eVoucherParamDto();
                p6eVoucherParamDto.setVoucher(param.getVoucher());
                p6eVoucherParamDto.setContent(param.getPassword());
                P6eVoucherResultDto p6eVoucherResultDto = p6eVoucherService.verify(p6eVoucherParamDto);
                if (p6eVoucherResultDto != null) {
                    // 重写密码数据
                    param.setPassword(p6eVoucherResultDto.getContent());
                    // 登录的操作
                    P6eSignResultDto p6eSignResultDto
                            = p6eSignService.in(CopyUtil.run(param, P6eSignParamDto.class));
                    if (p6eSignResultDto == null || p6eSignResultDto.getError() != null) {
                        // 参数异常
                        return P6eResultModel.build(P6eResultConfig.ERROR_ACCOUNT_OR_PASSWORD);
                    } else {
                        final String mode = param.getMode();
                        switch (mode) {
                            case "CODE":
                                final String mark = param.getMark();
                                if (mark == null) {
                                    // 参数异常
                                    return P6eResultModel.build(P6eResultConfig.ERROR_PARAM_EXCEPTION);
                                } else {
                                    System.out.println("111111111");
                                    // 读取记号
                                    P6eAuthParamDto p6eAuthParamDto = new P6eAuthParamDto();
                                    p6eAuthParamDto.setMark(mark);
                                    p6eAuthParamDto.setData(GsonUtil.toJson(p6eSignResultDto));
                                    P6eAuthResultDto p6eAuthResultDto = p6eAuthService.getCache(p6eAuthParamDto);
                                    if (p6eAuthResultDto == null) {
                                        System.out.println("22222");
                                        return P6eResultModel.build(P6eResultConfig.ERROR_PARAM_EXCEPTION);
                                    } else {
                                        // 缓存用户信息---code
                                        P6eSignInResultVo p6eSignInResultVo = new P6eSignInResultVo();
                                        p6eSignInResultVo.setCode(p6eAuthResultDto.getCode());
                                        p6eSignInResultVo.setUri(p6eAuthResultDto.getRedirectUri());
                                        p6eSignInResultVo.setData(GsonUtil.toJson(p6eSignResultDto));
                                        return P6eResultModel.build(P6eResultConfig.SUCCESS, p6eSignInResultVo);
                                    }
                                }
                            case "2":
                            default:
                                return P6eResultModel.build(P6eResultConfig.ERROR_SERVICE_INSIDE);
                        }
                    }
                } else {
                    return P6eResultModel.build(P6eResultConfig.ERROR_SERVICE_INSIDE);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return P6eResultModel.build(P6eResultConfig.ERROR_SERVICE_INSIDE);
        }
    }

    @GetMapping("/refresh")
    public P6eResultModel refresh() {
        try {
            return P6eResultModel.build(P6eResultConfig.ERROR_SERVICE_INSIDE);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return P6eResultModel.build(P6eResultConfig.ERROR_SERVICE_INSIDE);
        }
    }

    @GetMapping("/out")
    public P6eResultModel out() {
        try {
            return P6eResultModel.build(P6eResultConfig.ERROR_SERVICE_INSIDE);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return P6eResultModel.build(P6eResultConfig.ERROR_SERVICE_INSIDE);
        }
    }

}
