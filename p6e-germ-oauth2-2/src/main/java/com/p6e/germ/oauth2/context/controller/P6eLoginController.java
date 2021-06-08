package com.p6e.germ.oauth2.context.controller;

import com.p6e.germ.common.utils.P6eCopyUtil;
import com.p6e.germ.common.utils.P6eFormatUtil;
import com.p6e.germ.common.utils.P6eJsonUtil;
import com.p6e.germ.oauth2.application.P6eApplication;
import com.p6e.germ.oauth2.context.controller.support.P6eBaseController;
import com.p6e.germ.oauth2.model.*;
import com.p6e.germ.oauth2.model.P6eResultModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录服务的接口
 * @author lidashuang
 * @version 1.0
 */
@Api("登录接口")
@RestController
public class P6eLoginController extends P6eBaseController {

    @ApiOperation(
            value = "账号密码登录的密码加密凭证接口"
    )
    @GetMapping("/login/secret_voucher")
    public P6eResultModel secretVoucher(P6eSecretVoucherModel.VoParam param) {
        try {
            if (param == null || param.getMark() == null) {
                return P6eResultModel.build(P6eResultModel.Error.PARAMETER_EXCEPTION);
            } else {
                final P6eSecretVoucherModel.DtoResult result =
                        P6eApplication.login.secretVoucher(P6eCopyUtil.run(param, P6eSecretVoucherModel.DtoParam.class));
                if (result == null) {
                    return P6eResultModel.build(P6eResultModel.Error.SERVICE_EXCEPTION);
                } else if (result.getError() != null) {
                    return P6eResultModel.build(result.getError());
                } else {
                    return P6eResultModel.build(P6eCopyUtil.run(result, P6eSecretVoucherModel.VoResult.class));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            return P6eResultModel.build(P6eResultModel.Error.SERVICE_EXCEPTION);
        }
    }

    @ApiOperation(
            value = "验证登录的接口"
    )
    @GetMapping("/login/verification")
    public P6eResultModel verification(HttpServletRequest request,
                                       P6eApModel.VerificationVoParam param) {
        try {
            // 读取 ACCESS TOKEN 数据
            if (param.getAccessToken() == null) {
                String accessToken = request.getParameter(AUTH_PARAM_NAME);
                if (accessToken == null) {
                    final String content = request.getHeader(AUTH_HEADER_NAME);
                    if (content != null
                            && content.startsWith(AUTH_HEADER_BEARER)
                            && content.length() > AUTH_HEADER_BEARER_LENGTH) {
                        accessToken = content.substring(AUTH_HEADER_BEARER_LENGTH);
                    }
                }
                param.setAccessToken(accessToken);
            }
            if (param.getMark() == null || param.getAccessToken() == null) {
                return P6eResultModel.build(P6eResultModel.Error.PARAMETER_EXCEPTION);
            } else {
                // 执行登录验证服务
                final P6eApModel.DtoResult result =
                        P6eApplication.login.verification(P6eCopyUtil.run(param, P6eApModel.VerificationDtoParam.class));
                if (result == null) {
                    return P6eResultModel.build(P6eResultModel.Error.SERVICE_EXCEPTION);
                } else if (result.getError() != null) {
                    return P6eResultModel.build(result.getError());
                } else {
                    return P6eResultModel.build(P6eCopyUtil.run(result, P6eApModel.VerificationDtoResult.class));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            return P6eResultModel.build(P6eResultModel.Error.SERVICE_EXCEPTION);
        }
    }

    @ApiOperation(
            value = "账号密码登录的接口"
    )
    @PostMapping("/login/account_password")
    public P6eResultModel accountPassword(@RequestBody P6eApModel.VoParam param,
                                          HttpServletRequest request, HttpServletResponse response) {
        try {
            if (param == null
                    || param.getAccount() == null
                    || param.getPassword() == null
                    || param.getMark() == null
                    || param.getVoucher() == null) {
                return P6eResultModel.build(P6eResultModel.Error.PARAMETER_EXCEPTION);
            } else {
                // 执行登录服务
                final P6eApModel.DtoResult result = P6eApplication.login.accountPassword(
                        P6eCopyUtil.run(param, P6eApModel.DtoParam.class));
                if (result == null) {
                    return P6eResultModel.build(P6eResultModel.Error.SERVICE_EXCEPTION);
                } else if (result.getError() != null) {
                    return P6eResultModel.build(result.getError());
                } else {
                    // 重定向去新的页面
                    P6eAuthController.page(request, response, P6eJsonUtil.toJson(
                            P6eCopyUtil.run(result, P6eApModel.DtoResult.class)));
                    return null;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            return P6eResultModel.build(P6eResultModel.Error.SERVICE_EXCEPTION);
        }
    }








    @ApiOperation(
            value = "获取登录验证码的接口"
    )
    @GetMapping("/login/nr_code")
    public P6eResultModel nrCode(P6eNrCodeModel.VoParam param) {
        try {
            if (param == null || param.getMark() == null || param.getAccount() == null) {
                return P6eResultModel.build(P6eResultModel.Error.PARAMETER_EXCEPTION);
            } else {
                final String account = param.getAccount();
                // 验证账号格式是否正确
                if (P6eFormatUtil.emailVerification(account) || P6eFormatUtil.phoneVerification(account)) {
                    // 复制数据写入 IP 地址
                    final P6eNrCodeModel.DtoResult result = P6eApplication.login.nrCode(
                            P6eCopyUtil.run(param, P6eNrCodeModel.DtoParam.class).setIp(obtainIp()));
                    if (result == null) {
                        return P6eResultModel.build(P6eResultModel.Error.SERVICE_EXCEPTION);
                    } else if (result.getError() != null) {
                        return P6eResultModel.build(result.getError());
                    } else {
                        return P6eResultModel.build(P6eCopyUtil.run(result, P6eNrCodeModel.VoResult.class));
                    }
                } else {
                    return P6eResultModel.build(P6eResultModel.Error.PARAMETER_EXCEPTION);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            return P6eResultModel.build(P6eResultModel.Error.SERVICE_EXCEPTION);
        }
    }

    @ApiOperation(
            value = "采用登录验证码登录的接口"
    )
    @PostMapping("/login/nr_code/data")
    public P6eResultModel nrCodeData(@RequestBody P6eNrCodeModel.VoParam param,
                                     HttpServletRequest request, HttpServletResponse response) {
        try {
            if (param == null
                    || param.getMark() == null
                    || param.getAccount() == null
                    || param.getCodeKey() == null
                    || param.getCodeContent() == null) {
                return P6eResultModel.build(P6eResultModel.Error.PARAMETER_EXCEPTION);
            } else {
                // 复制数据写入 IP 地址
                final P6eNrCodeModel.DtoResult result = P6eApplication.login.nrCodeLogin(
                        P6eCopyUtil.run(param, P6eNrCodeModel.DtoParam.class).setIp(obtainIp()));
                if (result == null) {
                    return P6eResultModel.build(P6eResultModel.Error.SERVICE_EXCEPTION);
                } else if (result.getError() != null) {
                    return P6eResultModel.build(result.getError());
                } else {
                    // 重定向去新的页面
                    P6eAuthController.page(request, response, P6eJsonUtil.toJson(
                            P6eCopyUtil.run(result, P6eNrCodeModel.VoResult.class)));
                    return null;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            return P6eResultModel.build(P6eResultModel.Error.SERVICE_EXCEPTION);
        }
    }









    @ApiOperation(
            value = "获取二维码登录的接口"
    )
    @GetMapping("/login/qr_code")
    public P6eResultModel qrCode(P6eQrCodeModel.VoParam param) {
        try {
            if (param == null || param.getMark() == null) {
                return P6eResultModel.build(P6eResultModel.Error.PARAMETER_EXCEPTION);
            } else {
                final P6eQrCodeModel.DtoResult result =
                        P6eApplication.login.qrCode(P6eCopyUtil.run(param, P6eQrCodeModel.DtoParam.class));
                if (result == null) {
                    return P6eResultModel.build(P6eResultModel.Error.SERVICE_EXCEPTION);
                } else if (result.getError() != null) {
                    return P6eResultModel.build(result.getError());
                } else {
                    return P6eResultModel.build(P6eCopyUtil.run(result, P6eQrCodeModel.VoResult.class));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            return P6eResultModel.build(P6eResultModel.Error.SERVICE_EXCEPTION);
        }
    }

    @ApiOperation(
            value = "获取二维码登录信息的接口"
    )
    @GetMapping("/login/qr_code/data")
    public P6eResultModel qrCodeData(P6eQrCodeModel.VoParam param) {
        try {
            if (param == null
                    || param.getMark() == null
                    || param.getCode() == null) {
                return P6eResultModel.build(P6eResultModel.Error.PARAMETER_EXCEPTION);
            } else {
                final P6eQrCodeModel.DtoResult result =
                        P6eApplication.login.qrCodeLogin(P6eCopyUtil.run(param, P6eQrCodeModel.DtoParam.class));
                if (result == null) {
                    return P6eResultModel.build(P6eResultModel.Error.SERVICE_EXCEPTION);
                } else if (result.getError() != null) {
                    return P6eResultModel.build(result.getError());
                } else {
                    return P6eResultModel.build(P6eCopyUtil.run(result, P6eNrCodeModel.VoResult.class));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            return P6eResultModel.build(P6eResultModel.Error.SERVICE_EXCEPTION);
        }
    }










    @ApiOperation(
            value = "QQ登录的接口"
    )
    @GetMapping("/login/qq")
    public P6eResultModel qqLogin(P6eOtherLoginModel.VoParam param) {
        try {
            if (param == null || param.getMark() == null) {
                return P6eResultModel.build(P6eResultModel.Error.PARAMETER_EXCEPTION);
            } else {
                final P6eOtherLoginModel.DtoResult result =
                        P6eApplication.login.qqInfo(P6eCopyUtil.run(param, P6eOtherLoginModel.DtoParam.class));
                if (result == null) {
                    return P6eResultModel.build(P6eResultModel.Error.SERVICE_EXCEPTION);
                } else if (result.getError() != null) {
                    return P6eResultModel.build(result.getError());
                } else {
                    return P6eResultModel.build(P6eCopyUtil.run(result, P6eQrCodeModel.VoResult.class));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            return P6eResultModel.build(P6eResultModel.Error.SERVICE_EXCEPTION);
        }
    }

    @ApiOperation(
            value = "QQ登录的回调接口"
    )
    @GetMapping("/login/qq/callback")
    public P6eResultModel qqLoginCallback(HttpServletRequest request,
                                          HttpServletResponse response,
                                          P6eOtherLoginModel.VoParam param) {
        try {
            if (param == null
                    || param.getCode() == null
                    || param.getState() == null) {
                return P6eResultModel.build(P6eResultModel.Error.PARAMETER_EXCEPTION);
            } else {
                final P6eOtherLoginModel.DtoResult result =
                        P6eApplication.login.qqLogin(P6eCopyUtil.run(param, P6eOtherLoginModel.DtoParam.class));
                // 写入数据到页面
                P6eAuthController.page(request, response, P6eJsonUtil.toJson(result));
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            return P6eResultModel.build(P6eResultModel.Error.SERVICE_EXCEPTION);
        }
    }

    @ApiOperation(
            value = "微信登录的接口"
    )
    @GetMapping("/login/we_chat")
    public P6eResultModel weChatLogin(P6eOtherLoginModel.VoParam param) {
        try {
            if (param == null || param.getMark() == null) {
                return P6eResultModel.build(P6eResultModel.Error.PARAMETER_EXCEPTION);
            } else {
                final P6eOtherLoginModel.DtoResult result =
                        P6eApplication.login.weChatInfo(P6eCopyUtil.run(param, P6eOtherLoginModel.DtoParam.class));
                if (result == null) {
                    return P6eResultModel.build(P6eResultModel.Error.SERVICE_EXCEPTION);
                } else if (result.getError() != null) {
                    return P6eResultModel.build(result.getError());
                } else {
                    return P6eResultModel.build(P6eCopyUtil.run(result, P6eQrCodeModel.VoResult.class));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            return P6eResultModel.build(P6eResultModel.Error.SERVICE_EXCEPTION);
        }
    }

    @ApiOperation(
            value = "微信登录的回调接口"
    )
    @GetMapping("/login/we_chat/callback")
    public P6eResultModel weChatLoginCallback(HttpServletRequest request,
                                              HttpServletResponse response,
                                              P6eOtherLoginModel.VoParam param) {
        try {
            if (param == null
                    || param.getCode() == null
                    || param.getState() == null) {
                return P6eResultModel.build(P6eResultModel.Error.PARAMETER_EXCEPTION);
            } else {
                final P6eOtherLoginModel.DtoResult result =
                        P6eApplication.login.weChatLogin(P6eCopyUtil.run(param, P6eOtherLoginModel.DtoParam.class));
                // 写入数据到页面
                P6eAuthController.page(request, response, P6eJsonUtil.toJson(result));
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            return P6eResultModel.build(P6eResultModel.Error.SERVICE_EXCEPTION);
        }
    }

    @ApiOperation(
            value = "新浪登录的接口"
    )
    @GetMapping("/login/sina")
    public P6eResultModel sinaLogin(P6eOtherLoginModel.VoParam param) {
        try {
            if (param == null || param.getMark() == null) {
                return P6eResultModel.build(P6eResultModel.Error.PARAMETER_EXCEPTION);
            } else {
                final P6eOtherLoginModel.DtoResult result =
                        P6eApplication.login.sinaInfo(P6eCopyUtil.run(param, P6eOtherLoginModel.DtoParam.class));
                if (result == null) {
                    return P6eResultModel.build(P6eResultModel.Error.SERVICE_EXCEPTION);
                } else if (result.getError() != null) {
                    return P6eResultModel.build(result.getError());
                } else {
                    return P6eResultModel.build(P6eCopyUtil.run(result, P6eQrCodeModel.VoResult.class));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            return P6eResultModel.build(P6eResultModel.Error.SERVICE_EXCEPTION);
        }
    }

    @ApiOperation(
            value = "新浪登录的回调接口"
    )
    @GetMapping("/login/sina/callback")
    public P6eResultModel sinaLoginCallback(HttpServletRequest request,
                                            HttpServletResponse response,
                                            P6eOtherLoginModel.VoParam param) {
        try {
            if (param == null
                    || param.getCode() == null
                    || param.getState() == null) {
                return P6eResultModel.build(P6eResultModel.Error.PARAMETER_EXCEPTION);
            } else {
                final P6eOtherLoginModel.DtoResult result =
                        P6eApplication.login.sinaLogin(P6eCopyUtil.run(param, P6eOtherLoginModel.DtoParam.class));
                // 写入数据到页面
                P6eAuthController.page(request, response, P6eJsonUtil.toJson(result));
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            return P6eResultModel.build(P6eResultModel.Error.SERVICE_EXCEPTION);
        }
    }
}
