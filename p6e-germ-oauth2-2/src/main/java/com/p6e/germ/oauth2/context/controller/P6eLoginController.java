package com.p6e.germ.oauth2.context.controller;

import com.p6e.germ.common.utils.P6eCopyUtil;
import com.p6e.germ.common.utils.P6eJsonUtil;
import com.p6e.germ.oauth2.application.P6eApplication;
import com.p6e.germ.oauth2.context.controller.support.P6eBaseController;
import com.p6e.germ.oauth2.model.*;
import com.p6e.germ.oauth2.model.P6eResultModel;
import com.p6e.germ.oauth2.model.dto.*;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
                String token = request.getParameter(AUTH_PARAM_NAME);
                if (token == null) {
                    final String content = request.getHeader(AUTH_HEADER_NAME);
                    if (content != null
                            && content.startsWith(AUTH_HEADER_BEARER)
                            && content.length() > AUTH_HEADER_BEARER_LENGTH) {
                        token = content.substring(AUTH_HEADER_BEARER_LENGTH);
                    }
                }
                param.setAccessToken(token);
            }
            if (param.getAccessToken() == null) {
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
                final P6eNrCodeModel.DtoResult result =
                        P6eApplication.login.nrCode(P6eCopyUtil.run(param, P6eNrCodeModel.DtoParam.class));
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

    @PostMapping("/login/nr_code/data")
    public P6eResultModel nrCodeData() {
        try {
            return P6eResultModel.build();
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



//
//
//    @GetMapping("/login/code/generate")
//    public P6eResultModel generateCode(P6eCodeLoginParam param) {
//        final P6eGenerateCodeLoginDto p6eGenerateCodeLoginDto = P6eApplication.login.generateCode(param.getMark());
//        if (p6eGenerateCodeLoginDto.getError() == null) {
//            return P6eResultModel.build().setData(P6eCopyUtil.run(p6eGenerateCodeLoginDto, P6eCodeLoginResult.class));
//        } else {
//            return P6eResultModel.build(p6eGenerateCodeLoginDto.getError());
//        }
//    }

//    @GetMapping("/login/code/get")
//    public P6eResultModel getCode(P6eCodeLoginParam param) {
//        final P6eLoginDto p6eLoginDto = P6eApplication.login.getCodeLogin(param.getCode());
//        if (p6eLoginDto.getError() == null) {
//            if (p6eLoginDto.getAccessToken() == null) {
//                return P6eResultModel.build();
//            } else {
//                return P6eResultModel.build().setData(P6eCopyUtil.run(p6eLoginDto, P6eDefaultLoginResult.class));
//            }
//        } else {
//            return P6eResultModel.build(p6eLoginDto.getError());
//        }
//    }





    @RequestMapping("/login/qq")
    public void qqLogin(HttpServletResponse response, P6eQqLoginParam param) throws Exception {
        final P6eUrlLoginDto p6eUrlLoginDto = P6eApplication.login.qqInfo(param.getMark(), param.getDisplay());
        if (p6eUrlLoginDto.getError() == null) {
            // 重定向 URL
            response.sendRedirect(p6eUrlLoginDto.getUrl());
        } else {
            // 写入返回的数据
            response.getWriter().write(P6eJsonUtil.toJson(P6eResultModel.build(p6eUrlLoginDto.getError())));
            response.getWriter().flush();
            response.getWriter().close();
        }
    }

    @RequestMapping("/login/qq/callback")
    public void qqLoginCallback(P6eQqCallbackLoginParam param, HttpServletResponse response) throws Exception {
        final String dataType = "data";
        if (dataType.equals(param.getType())) {
            // 通过首页发送请求，然后跳转
            final P6eResultModel p6eModel;
            final P6eLoginDto p6eLoginDto =
                    P6eApplication.login.qqLogin(P6eCopyUtil.run(param, P6eQqLoginDto.class));
            response.getWriter().write(P6eJsonUtil.toJson(loginResult(p6eLoginDto)));
            response.getWriter().flush();
            response.getWriter().close();
        } else {
            // 回到首页
            response.sendRedirect("/index.html?type=qq&code=" + param.getCode() + "&state=" + param.getState());
        }
    }

    @RequestMapping("/login/we_chat")
    public void weChatLogin(HttpServletResponse response, P6eWeChatLoginParam param) throws Exception {
        final P6eUrlLoginDto p6eUrlLoginDto = P6eApplication.login.weChatInfo(param.getMark());
        if (p6eUrlLoginDto.getError() == null) {
            // 重定向 URL
            response.sendRedirect(p6eUrlLoginDto.getUrl());
        } else {
            // 写入返回的数据
            response.getWriter().write(P6eJsonUtil.toJson(P6eResultModel.build(p6eUrlLoginDto.getError())));
            response.getWriter().flush();
            response.getWriter().close();
        }
    }

    @RequestMapping("/login/we_chat/callback")
    public void weChatLoginCallback(P6eQqCallbackLoginParam param, HttpServletResponse response) throws Exception {
        final String dataType = "data";
        if (dataType.equals(param.getType())) {
            // 通过首页发送请求，然后跳转
            final P6eResultModel p6eModel;
            final P6eLoginDto p6eLoginDto =
                    P6eApplication.login.weChatLogin(P6eCopyUtil.run(param, P6eWeChatLoginDto.class));
            response.getWriter().write(P6eJsonUtil.toJson(loginResult(p6eLoginDto)));
            response.getWriter().flush();
            response.getWriter().close();
        } else {
            // 回到首页
            response.sendRedirect("/index.html?type=we_chat&code=" + param.getCode() + "&state=" + param.getState());
        }
    }


    /**
     * 登录返回
     * @param p6eLoginDto 登录的服务
     * @return 返回的类型
     */
    private P6eResultModel loginResult(P6eLoginDto p6eLoginDto) {
        final P6eResultModel p6eModel;
        if (p6eLoginDto.getError() == null) {
            p6eModel = P6eResultModel.build().setData(P6eCopyUtil.run(p6eLoginDto, P6eVerificationLoginResult.class));
        } else {
            p6eModel = P6eResultModel.build(p6eLoginDto.getError());
        }
        return p6eModel;
    }

}
