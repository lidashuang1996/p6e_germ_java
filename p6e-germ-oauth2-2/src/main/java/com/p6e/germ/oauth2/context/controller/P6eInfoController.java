package com.p6e.germ.oauth2.context.controller;

import com.p6e.germ.oauth2.application.P6eInfoService;
import com.p6e.germ.oauth2.context.controller.support.P6eBaseController;
import com.p6e.germ.oauth2.context.controller.support.model.P6eModelConfig;
import com.p6e.germ.oauth2.context.controller.support.model.P6eResultModel;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 获取信息的接口
 * @author lidashuang
 * @version 1.0
 */
@RestController
@RequestMapping("/info")
public class P6eInfoController extends P6eBaseController {
    /**
     * 请求的携带认证信息的参数
     */
    private static final String AUTH_PARAM_NAME = "access_token";

    /**
     * 请求头内容的前缀
     */
    private static final String AUTH_HEADER_BEARER = "Bearer ";

    /**
     * 请求头名称
     */
    private static final String AUTH_HEADER_NAME = "authentication";

    @Resource
    private P6eInfoService p6eInfoService;

    @RequestMapping
    public P6eResultModel def(final HttpServletRequest request) {
        try {
            String token = request.getParameter(AUTH_PARAM_NAME);
            if (token == null) {
                final String content = request.getHeader(AUTH_HEADER_NAME);
                if (content != null && content.startsWith(AUTH_HEADER_BEARER)) {
                    token = content.substring(7);
                }
            }
            if (token != null) {
                final Object data = p6eInfoService.execute(token);
                if (data == null) {
                    return P6eResultModel.build(P6eModelConfig.ERROR_RESOURCES_NO_EXIST, "TOKEN 过期");
                } else {
                    return P6eResultModel.build(P6eModelConfig.SUCCESS, data);
                }
            }
            return P6eResultModel.build(P6eModelConfig.ERROR_PARAM_EXCEPTION);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            return P6eResultModel.build(P6eModelConfig.ERROR_SERVICE_INSIDE);
        }
    }

}

