package com.p6e.germ.oauth2.controller;

import com.p6e.germ.oauth2.controller.support.P6eBaseController;
import com.p6e.germ.oauth2.model.P6eResultConfig;
import com.p6e.germ.oauth2.model.P6eResultModel;
import com.p6e.germ.oauth2.model.dto.*;
import com.p6e.germ.oauth2.model.vo.P6eSignInParamVo;
import com.p6e.germ.oauth2.service.P6eAuthService;
import com.p6e.germ.oauth2.service.P6eSignService;
import com.p6e.germ.oauth2.service.P6eVoucherService;
import com.p6e.germ.oauth2.utils.CopyUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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

    /** COOKIE 的名称 */
    private static final String COOKIE_NAME = "P6E_OAUTH2_COOKIE";

    @PostMapping("/in")
    public Object in(HttpServletRequest request, @RequestBody P6eSignInParamVo param) {
        try {
            final Object cookie = request.getAttribute(COOKIE_NAME);
            if (param == null
                    || cookie == null
                    || param.getMode() == null
                    || param.getVoucher() == null
                    || param.getAccount() == null
                    || param.getPassword() == null) {
                // 参数异常
                return P6eResultModel.build(P6eResultConfig.ERROR_PARAM_EXCEPTION);
            } else {
                // 读取信息对密码解密
                P6eVoucherParamDto p6eVoucherParamDto = new P6eVoucherParamDto();
                p6eVoucherParamDto.setVoucher(param.getVoucher());
                p6eVoucherParamDto.setContent(param.getPassword());
                P6eVoucherResultDto p6eVoucherResultDto = p6eVoucherService.verify(p6eVoucherParamDto);
                if (p6eVoucherResultDto != null) {
                    // 重写密码数据
                    param.setPassword(p6eVoucherResultDto.getContent());
                    param.setCookie(String.valueOf(cookie));
                    // 登录的操作
                    // cookie 绑定 用户信息
                    P6eSignResultDto p6eSignResultDto
                            = p6eSignService.in(CopyUtil.run(param, P6eSignParamDto.class));
                    if (p6eSignResultDto == null || p6eSignResultDto.getError() != null) {
                        // 账号/密码错误
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
                                    // 读取记号
                                    P6eAuthManageCodeParamDto p6eAuthManageCodeParamDto = new P6eAuthManageCodeParamDto();
                                    p6eAuthManageCodeParamDto.setMark(mark);
                                    p6eAuthManageCodeParamDto.setData(CopyUtil.toMap(p6eSignResultDto));

                                    // 验证 code
                                    P6eAuthManageCodeResultDto p6eAuthManageCodeResultDto
                                            = p6eAuthService.manageCode(p6eAuthManageCodeParamDto);

                                    if (p6eAuthManageCodeResultDto == null) {
                                        return P6eResultModel.build(P6eResultConfig.ERROR_PARAM_EXCEPTION);
                                    } else {
                                        // 缓存用户信息---code
                                        return P6eResultModel.build(P6eResultConfig.SUCCESS,
                                                p6eAuthManageCodeResultDto.getRedirectUri()
                                                        + "?code=" + p6eAuthManageCodeResultDto.getCode());
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
