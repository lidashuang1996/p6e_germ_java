package com.p6e.germ.oauth2.context.controller;

import com.p6e.germ.common.utils.P6eCopyUtil;
import com.p6e.germ.common.utils.P6eFormatUtil;
import com.p6e.germ.oauth2.application.P6eApplication;
import com.p6e.germ.oauth2.context.controller.support.P6eBaseController;
import com.p6e.germ.oauth2.model.*;
import com.p6e.germ.oauth2.model.P6eResultModel;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;

/**
 * 登录服务的接口
 * @author lidashuang
 * @version 1.0
 */
@RestController
public class P6eLoginController extends P6eBaseController {

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

    @GetMapping("/login/verification")
    public P6eResultModel verification(HttpServletRequest request,
                                       P6eLoginModel.VerificationVoParam param) {
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
                final P6eLoginModel.DtoResult result =
                        P6eApplication.login.verification(P6eCopyUtil.run(param, P6eLoginModel.VerificationDtoParam.class));
                if (result == null) {
                    return P6eResultModel.build(P6eResultModel.Error.SERVICE_EXCEPTION);
                } else if (result.getError() != null) {
                    return P6eResultModel.build(result.getError());
                } else {
                    return P6eResultModel.build(P6eCopyUtil.run(result, P6eLoginModel.VerificationDtoResult.class));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            return P6eResultModel.build(P6eResultModel.Error.SERVICE_EXCEPTION);
        }
    }

    @PostMapping("/login/account_password")
    public P6eResultModel accountPassword(@RequestBody P6eLoginModel.AccountPasswordVoParam param) {
        try {
            if (param == null
                    || param.getAccount() == null
                    || param.getPassword() == null
                    || param.getMark() == null
                    || param.getVoucher() == null) {
                return P6eResultModel.build(P6eResultModel.Error.PARAMETER_EXCEPTION);
            } else {
                // 执行登录服务
                final P6eLoginModel.AccountPasswordDtoResult result = P6eApplication.login.accountPassword(
                        P6eCopyUtil.run(param, P6eLoginModel.AccountPasswordDtoParam.class));
                if (result == null) {
                    return P6eResultModel.build(P6eResultModel.Error.SERVICE_EXCEPTION);
                } else if (result.getError() != null) {
                    return P6eResultModel.build(result.getError());
                } else {
                    return P6eResultModel.build(P6eCopyUtil.run(result, P6eLoginModel.VoResult.class));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            return P6eResultModel.build(P6eResultModel.Error.SERVICE_EXCEPTION);
        }
    }

    @GetMapping("/login/nr_code")
    public P6eResultModel nrCode(P6eNrCodeModel.VoParam param) {
        try {
            if (param == null || param.getMark() == null || param.getAccount() == null) {
                return P6eResultModel.build(P6eResultModel.Error.PARAMETER_EXCEPTION);
            } else {
                final String account = param.getAccount();
                if (P6eFormatUtil.emailVerification(account) || P6eFormatUtil.phoneVerification(account)) {
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

    @PostMapping("/login/nr_code/data")
    public P6eResultModel nrCodeData(P6eNrCodeModel.VoParam param) {
        try {
            if (param == null
                    || param.getMark() == null
                    || param.getCode() == null
                    || param.getAccount() == null) {
                return P6eResultModel.build(P6eResultModel.Error.PARAMETER_EXCEPTION);
            } else {
                final P6eNrCodeModel.DtoResult result = P6eApplication.login.nrCodeLogin(
                        P6eCopyUtil.run(param, P6eNrCodeModel.DtoParam.class).setIp(obtainIp()));
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

    @GetMapping("/login/qr_code/data")
    public P6eResultModel qrCodeData() {
        try {
            return P6eResultModel.build();
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            return P6eResultModel.build(P6eResultModel.Error.SERVICE_EXCEPTION);
        }
    }

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

    @GetMapping("/login/qq/callback")
    public P6eResultModel qqLoginCallback(P6eOtherLoginModel.VoParam param) {
        try {
            return P6eResultModel.build("返回脚本内容，让脚本完成跳转");
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            return P6eResultModel.build(P6eResultModel.Error.SERVICE_EXCEPTION);
        }
    }

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

    @GetMapping("/login/we_chat/callback")
    public P6eResultModel weChatLoginCallback(P6eOtherLoginModel.VoParam param) {
        try {
            return P6eResultModel.build("返回脚本内容，让脚本完成跳转");
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            return P6eResultModel.build(P6eResultModel.Error.SERVICE_EXCEPTION);
        }
    }

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

    @GetMapping("/login/sina/callback")
    public P6eResultModel sinaLoginCallback(P6eOtherLoginModel.VoParam param) {
        try {
            return P6eResultModel.build("返回脚本内容，让脚本完成跳转");
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            return P6eResultModel.build(P6eResultModel.Error.SERVICE_EXCEPTION);
        }
    }
}
