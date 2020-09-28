//package com.p6e.germ.oauth2.controller;
//
//import com.p6e.germ.oauth2.controller.support.P6eBaseController;
//import com.p6e.germ.oauth2.model.P6eResultConfig;
//import com.p6e.germ.oauth2.model.dto.P6eAuthParamDto;
//import com.p6e.germ.oauth2.model.dto.P6eAuthResultDto;
//import com.p6e.germ.oauth2.model.dto.P6eAuthTokenParamDto;
//import com.p6e.germ.oauth2.model.dto.P6eAuthTokenResultDto;
//import com.p6e.germ.oauth2.model.vo.P6eAuthParamVo;
//import com.p6e.germ.oauth2.service.P6eAuthService;
//import com.p6e.germ.oauth2.utils.CopyUtil;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.servlet.ModelAndView;
//
//import javax.annotation.Resource;
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * 该类为 oauth2 认证的接口
// * @author lidashuang
// * @version 1.0
// */
//@Controller
//@RequestMapping("/auth")
//public class P6eAuthController extends P6eBaseController {
//
//    private static final String LOGIN_JSP_PAGE = "login";
//    private static final String ERROR_JSP_PAGE = "error";
//
//    @Resource
//    private P6eAuthService p6eAuthService;
//
//    @RequestMapping
//    public ModelAndView def(P6eAuthParamVo param) {
//        try {
//            if (param == null) {
//                // 参数异常
//                return errorPage(P6eResultConfig.ERROR_PARAM_EXCEPTION);
//            } else {
//                final String responseType = param.getResponse_type();
//                if (responseType == null) {
//                    // code 参数异常
//                    return errorPage(P6eResultConfig.ERROR_PARAM_EXCEPTION);
//                } else {
//                    switch (responseType.toUpperCase()) {
//                        // CODE 的认证模式
//                        case "CODE":
//                            P6eAuthResultDto p6eAuthResultDtoCode
//                                    = p6eAuthService.code(CopyUtil.run(param, P6eAuthParamDto.class));
//                            if (p6eAuthResultDtoCode.getError() != null) {
//                                return errorPage(p6eAuthResultDtoCode.getError());
//                            } else {
//                                return loginPage(p6eAuthResultDtoCode.getMark(), "CODE");
//                            }
//                        // 简化模式
//                        case "TOKEN":
//                            P6eAuthTokenResultDto p6eAuthTokenResultDto
//                                    = p6eAuthService.token(CopyUtil.run(param, P6eAuthTokenParamDto.class));
//                            if (p6eAuthTokenResultDto.getError() != null) {
//                                return errorPage(p6eAuthTokenResultDto.getError());
//                            } else {
//                                return loginPage(p6eAuthTokenResultDto.getMark(), "TOKEN");
//                            }
//                        case "PASSWORD":
//                        default:
//                            break;
//                    }
//                }
//            }
//            return errorPage(P6eResultConfig.ERROR_SERVICE_INSIDE);
//        } catch (Exception e) {
//            e.printStackTrace();
//            LOGGER.error(e.getMessage());
//            return errorPage(P6eResultConfig.ERROR_SERVICE_INSIDE);
//        }
//    }
//
//    private ModelAndView loginPage(final String mark, final String mode) {
//        final Map<String, String> map = new HashMap<>(1);
//        map.put("mark", mark);
//        map.put("mode", mode);
//        return new ModelAndView(LOGIN_JSP_PAGE, map);
//    }
//
//    private ModelAndView errorPage(final String content) {
//        System.out.println(content);
//        final Map<String, String> map = new HashMap<>(1);
//        map.put("content", content);
//        return new ModelAndView(ERROR_JSP_PAGE, map);
//    }
//
//}
