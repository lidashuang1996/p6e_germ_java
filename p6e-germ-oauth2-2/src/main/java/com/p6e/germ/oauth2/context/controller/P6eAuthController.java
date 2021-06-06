package com.p6e.germ.oauth2.context.controller;

import com.p6e.germ.common.utils.P6eCopyUtil;
import com.p6e.germ.common.utils.P6eJsonUtil;
import com.p6e.germ.oauth2.Config;
import com.p6e.germ.oauth2.Utils;
import com.p6e.germ.oauth2.application.P6eApplication;
import com.p6e.germ.oauth2.context.controller.support.P6eBaseController;
import com.p6e.germ.oauth2.model.P6eAuthModel;
import com.p6e.germ.oauth2.model.P6eResultModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Oauth2
 * @author lidashuang
 * @version 1.0
 */
@Api("Oauth2认证入口")
@RestController
@RequestMapping("/auth")
public class P6eAuthController extends P6eBaseController {

    @ApiOperation(
            value = "验证传入的数据是否为客户端数据，正确则返回登录的页面的，否则错误提示"
    )
    @GetMapping
    public Object def(P6eAuthModel.VoParam param, String code,
                      HttpServletRequest request, HttpServletResponse response) {
        try {
            if (code != null && code.length() > 0) {
                response.setContentType(HTML_CONTENT_TYPE);
                response.getWriter().write(Utils.variableFormatting(Config.getHtml(), new String[]{ HTML_DATA_NAME, code }));
                response.getWriter().flush();
                response.getWriter().close();
                return null;
            }
            // 获取请求的数据
            if (param.getClientId() == null) {
                param.setClientId(request.getParameter(CLIENT_ID_PARAM));
            }
            if (param.getRedirectUri() == null) {
                param.setRedirectUri(request.getParameter(REDIRECT_URI_PARAM));
            }
            if (param.getResponseType() == null) {
                param.setResponseType(request.getParameter(RESPONSE_TYPE_PARAM));
            }
            if (param.getScope() == null
                    || param.getClientId() == null
                    || param.getRedirectUri() == null
                    || param.getResponseType() == null) {
                // 参数异常
                return P6eResultModel.build(P6eResultModel.Error.PARAMETER_EXCEPTION);
            } else {
                // 资源类型
                final String responseType = param.getResponseType();
                switch (responseType.toUpperCase()) {
                    case CODE_TYPE:
                    case TOKEN_TYPE:
                        // 验证参数是否正确
                        final P6eAuthModel.DtoResult result =
                                P6eApplication.auth.verification(P6eCopyUtil.run(param, P6eAuthModel.DtoParam.class));
                        if (result == null) {
                            return P6eResultModel.build(P6eResultModel.Error.SERVICE_EXCEPTION);
                        } else if (result.getError() != null) {
                            return P6eResultModel.build(result.getError());
                        } else {
                            // 写入登录页面的 HTML
                            response.setContentType(HTML_CONTENT_TYPE);
                            response.getWriter().write(Utils.variableFormatting(Config.getHtml(),
                                    new String[]{ HTML_DATA_NAME, P6eJsonUtil.toJson(P6eCopyUtil.run(result, P6eAuthModel.VoResult.class)) }));
                            response.getWriter().flush();
                            response.getWriter().close();
                            return null;
                        }
                    default:
                        return P6eResultModel.build(P6eResultModel.Error.PARAMETER_EXCEPTION);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            return P6eResultModel.build(P6eResultModel.Error.SERVICE_EXCEPTION);
        }
    }


    /**
     * 写入认证信息的页面
     * @param request HttpServletRequest 请求
     * @param response HttpServletResponse 结果
     */
    public static void page(HttpServletRequest request, HttpServletResponse response, String code) {
        try {
            response.setContentType(HTML_CONTENT_TYPE);
            response.getWriter().write(Utils.variableFormatting(Config.getHtml(),
                    new String[]{ HTML_DATA_NAME, code }));
            response.getWriter().flush();
            response.getWriter().close();
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
        }
    }

}
