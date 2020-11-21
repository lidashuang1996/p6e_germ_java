package com.p6e.germ.oauth2.context.controller;

import com.p6e.germ.oauth2.application.P6eAuthService;
import com.p6e.germ.oauth2.context.controller.support.P6eBaseController;
import com.p6e.germ.oauth2.context.controller.support.model.P6eAuthModelParam;
import com.p6e.germ.oauth2.context.controller.support.model.P6eAuthModelResult;
import com.p6e.germ.oauth2.context.controller.support.model.P6eModelConfig;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Oauth2 接口
 * @author lidashuang
 * @version 1.0
 */
@Controller
@RequestMapping("/auth")
public class P6eAuthController extends P6eBaseController {

    /** JSP 页面的名称 ==> 登录 */
    private static final String LOGIN_JSP_PAGE = "oauth2_login";
    /** JSP 页面的名称 ==> 错误 */
    private static final String ERROR_JSP_PAGE = "oauth2_error";
    /** JSP 页面的名称 ==> 成功 */
    private static final String SUCCESS_JSP_PAGE = "oauth2_success";

    /**
     * 注入认证的服务
     */
    @Resource
    private P6eAuthService p6eAuthService;

    /**
     * 1. 客户端模式
     * 2. 授权码模式
     * 3. 密码模式
     * 4. 简化模式
     *
     * http://127.0.0.1:9900/auth?client_id=1234567890&response_type=code&redirect_uri=http://127.0.0.1:10000&scope=123
     *
     *
     * // 简化模式
     * // // http://localhost:8888/oauth/authorize?client_id=cms&redirect_uri=http://127.0.0.1:8084/cms/login&response_type=token&scope=all
     *
     *
     * // 密码模式支持，但是只能查看你自己数据，也就是查询自己的 clien 信息
     *                         // http://localhost:8888/oauth/token?client_id=cms&client_secret=secret&grant_type=client_credentials&scope=all
     * @param param
     * @return
     */
    @RequestMapping
    public ModelAndView def(final HttpServletRequest request, final P6eAuthModelParam param) {
        try {
            if (param == null
                    || param.getScope() == null
                    || param.getClient_id() == null
                    || param.getRedirect_uri() == null
                    || param.getResponse_type() == null) {
                // 参数异常
                return errorPage(P6eModelConfig.ERROR_OAUTH2_PARAM_EXCEPTION);
            } else {
                // 读取浏览器数据，验证当前用户是否登录
                final String content = p6eAuthService.verification(request, param);
                if (content != null) {
                    return successPage(content);
                } else {
                    final String responseType = param.getResponse_type();
                    switch (responseType.toUpperCase()) {
                        // CODE 的认证模式
                        case "CODE":
                            final P6eAuthModelResult codeP6eAuthModelResult = p6eAuthService.codeMode(param);
                            if (codeP6eAuthModelResult.getError() == null) {
                                return loginPage(codeP6eAuthModelResult.getMark(),
                                        codeP6eAuthModelResult.getVoucher(), codeP6eAuthModelResult.getPublicKey());
                            } else {
                                return errorPage(codeP6eAuthModelResult.getError());
                            }
                        // 简化模式
                        case "TOKEN":
                            final P6eAuthModelResult tokenP6eAuthModelResult = p6eAuthService.tokenMode(param);
                            if (tokenP6eAuthModelResult.getError() == null) {
                                return loginPage(tokenP6eAuthModelResult.getMark(),
                                        tokenP6eAuthModelResult.getVoucher(), tokenP6eAuthModelResult.getPublicKey());
                            } else {
                                return errorPage(tokenP6eAuthModelResult.getError());
                            }
                        default:
                            // 参数异常
                            return errorPage(P6eModelConfig.ERROR_OAUTH2_PARAM_EXCEPTION);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            return errorPage(P6eModelConfig.ERROR_OAUTH2_SERVICE_INSIDE);
        }
    }

    /**
     * 跳转去登录页面
     * @param mark 记号
     * @param voucher 凭证
     * @return ModelAndView 视图对象
     */
    private ModelAndView loginPage(final String mark, final String voucher, final String publicKey) {
        final Map<String, String> map = new HashMap<>(1);
        map.put("mark", mark);
        map.put("voucher", voucher);
        map.put("publicKey", publicKey);
        return new ModelAndView(LOGIN_JSP_PAGE, map);
    }

    /**
     * 错误页面
     * @param content 错误页面描述的内容
     * @return ModelAndView 视图对象
     */
    private ModelAndView errorPage(final String content) {
        final Map<String, String> map = new HashMap<>(1);
        map.put("content", content);
        return new ModelAndView(ERROR_JSP_PAGE, map);
    }

    /**
     * 错误页面
     * @param content 成功页面描述的内容
     * @return ModelAndView 视图对象
     */
    private ModelAndView successPage(final String content) {
        final Map<String, String> map = new HashMap<>(1);
        map.put("content", content);
        return new ModelAndView(SUCCESS_JSP_PAGE, map);
    }
}
