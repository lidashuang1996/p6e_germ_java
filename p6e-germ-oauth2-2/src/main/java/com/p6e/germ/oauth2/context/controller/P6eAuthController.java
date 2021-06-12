package com.p6e.germ.oauth2.context.controller;

import com.p6e.germ.common.config.P6eConfig;
import com.p6e.germ.common.config.P6eOauth2Config;
import com.p6e.germ.common.utils.P6eCopyUtil;
import com.p6e.germ.common.utils.P6eJsonUtil;
import com.p6e.germ.oauth2.Config;
import com.p6e.germ.oauth2.Utils;
import com.p6e.germ.oauth2.application.P6eApplication;
import com.p6e.germ.oauth2.context.controller.support.P6eBaseController;
import com.p6e.germ.oauth2.model.P6eAuthModel;
import com.p6e.germ.oauth2.model.P6eCacheAuthModel;
import com.p6e.germ.oauth2.model.P6eResultModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Oauth2
 * @author lidashuang
 * @version 1.0
 */
@Api("Oauth2认证入口")
@RestController
@RequestMapping("/auth")
public class P6eAuthController extends P6eBaseController {

    /**
     * 页面
     */
    private final String html;

    /**
     * 允许的类型
     */
    private final P6eOauth2Config.Type[] types;

    /**
     * 构造函数
     * 初始化基本参数
     * @param config 配置文件
     */
    @Autowired
    public P6eAuthController(P6eConfig config) {
        this.types = config.getOauth2().getTypes();
        if (INDEX_HTML == null || INDEX_HTML.isEmpty()) {
            this.html = config.getOauth2().getHtml();
        } else {
            this.html = INDEX_HTML;
        }
    }

    @ApiOperation(
            value = "验证传入的数据是否为客户端数据，正确则返回登录的页面的，否则错误提示"
    )
    @GetMapping
    public P6eResultModel def(P6eAuthModel.VoParam param, String code,
                      HttpServletRequest request, HttpServletResponse response) {
        try {
            // 判断是否为登录成功的回调
            if (code != null && code.length() > 0) {
                return defAuth(code, request, response);
            } else {
                // 登录/注册的处理
                return defSign(param, request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            return P6eResultModel.build(P6eResultModel.Error.SERVICE_EXCEPTION);
        }
    }

    @GetMapping("/confirm")
    public P6eResultModel confirm(String code, HttpServletResponse response) {
        try {
            if (code == null) {
                return P6eResultModel.build(P6eResultModel.Error.PARAMETER_EXCEPTION);
            } else {
                // 清除缓存
                final P6eCacheAuthModel.DtoResult result =
                        P6eApplication.auth.cleanCache(new P6eCacheAuthModel.DtoParam().setKey(code));
                final String content = result.getContent();
                if (content == null) {
                    return P6eResultModel.build(P6eResultModel.Error.RESOURCES_NO_EXIST);
                } else {
                    // 读取数据
                    final Map<String, String> contentMap = P6eJsonUtil.fromJsonToMap(content, String.class, String.class);
                    final String rCode = contentMap.get("code");
                    final String rState = contentMap.get("state");
                    final String rUrl = contentMap.get("redirectUri");
                    if (rCode == null || rUrl == null || rCode.isEmpty() || rUrl.isEmpty()) {
                        return P6eResultModel.build(P6eResultModel.Error.SERVICE_EXCEPTION);
                    } else {
                        // 重定向去需求页面
                        return P6eResultModel.build(rUrl + "?code=" + rCode + (rState == null ? "" : "&state=" + rState));
                    }
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
     * @param content  写入到页面的数据
     */
    public static void page(HttpServletRequest request, HttpServletResponse response, String content) {
        try {
            // 写入数据到缓存
            final P6eCacheAuthModel.DtoResult result =
                    P6eApplication.auth.setCache(new P6eCacheAuthModel.DtoParam().setContent(content));
            response.sendRedirect("/auth?code=" + result.getContent());
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
        }
    }

    /**
     * 默认的登录/注册的处理
     * @param code 请求参数
     * @param request HttpServletRequest request 对象
     * @param response HttpServletResponse response 对象
     * @return 请求结果的返回
     * @throws Exception 写入到返回流中可能出现的异常
     */
    private P6eResultModel defAuth(String code, HttpServletRequest request,
                                   HttpServletResponse response) throws Exception {

        // 读取缓存的数据
        final P6eCacheAuthModel.DtoResult result =
                P6eApplication.auth.getCache(new P6eCacheAuthModel.DtoParam().setKey(code));
        if (result == null) {
            return P6eResultModel.build(P6eResultModel.Error.SERVICE_EXCEPTION);
        } else if (result.getError() != null) {
            return P6eResultModel.build(result.getError());
        } else {
            // 解析缓存的数据
            final String content = result.getContent();
            if (content == null) {
                return P6eResultModel.build(P6eResultModel.Error.RESOURCES_NO_EXIST);
            } else {
                // 前端认证数据的处理策略
                final Map<String, String> contentMap = P6eJsonUtil.fromJsonToMap(content, String.class, String.class);
                contentMap.put("type", "auth");
                final Cookie cookie1 = new Cookie(COOKIES_ACCESS_TOKEN_NAME, contentMap.get("accessToken"));
                cookie1.setPath("/");
                cookie1.setHttpOnly(true);
                cookie1.setMaxAge(3600);
                response.addCookie(cookie1);
                final Cookie cookie2 = new Cookie(COOKIES_REFRESH_TOKEN_NAME, contentMap.get("refreshToken"));
                cookie2.setPath("/");
                cookie2.setHttpOnly(true);
                cookie2.setMaxAge(3600);
                response.addCookie(cookie2);
                // 写入返回的页面数据
                response.setContentType(HTML_CONTENT_TYPE);
                response.getWriter().write(Utils.variableFormatting(html, new String[]{ HTML_DATA_NAME,  P6eJsonUtil.toJson(contentMap) }));
                response.getWriter().flush();
                response.getWriter().close();
                return null;
            }
        }

    }

    /**
     * 默认的登录/注册的处理
     * @param param 请求参数
     * @param request HttpServletRequest request 对象
     * @param response HttpServletResponse response 对象
     * @return 请求结果的返回
     * @throws Exception 写入到返回流中可能出现的异常
     */
    private P6eResultModel defSign(P6eAuthModel.VoParam param,
                                   HttpServletRequest request, HttpServletResponse response) throws Exception {
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
            // 二次验证返回友好的错误提示
            if (param.getClientId() == null) {
                return P6eResultModel.build(P6eResultModel.Error.CLIENT_ID_PARAMETER_EXCEPTION);
            }
            if (param.getRedirectUri() == null) {
                return P6eResultModel.build(P6eResultModel.Error.CLIENT_REDIRECT_URI_PARAMETER_EXCEPTION);
            }
            if (param.getResponseType() == null) {
                return P6eResultModel.build(P6eResultModel.Error.CLIENT_RESPONSE_TYPE_EXCEPTION);
            }
            if (param.getScope() == null) {
                return P6eResultModel.build(P6eResultModel.Error.CLIENT_SCOPE_EXCEPTION);
            }
            // 找不到就参数异常
            return P6eResultModel.build(P6eResultModel.Error.PARAMETER_EXCEPTION);
        } else {
            // 资源类型
            final String responseType = param.getResponseType().toUpperCase();
            // 验证是否开启
            boolean rs = false;
            for (final P6eOauth2Config.Type type : types) {
                if (type.is(responseType)) {
                    rs = true;
                    break;
                }
            }
            if (rs) {
                switch (responseType) {
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
                            // 写入登录页面的 HTML 代码
                            response.setContentType(HTML_CONTENT_TYPE);
                            response.getWriter().write(
                                    // 转换数据并解析数据且写入到返回流
                                    Utils.variableFormatting(html, new String[] { HTML_DATA_NAME,
                                            P6eJsonUtil.toJson(P6eCopyUtil.run(result, P6eAuthModel.VoResult.class)) }));
                            response.getWriter().flush();
                            response.getWriter().close();
                            return null;
                        }
                    default:
                        return P6eResultModel.build(P6eResultModel.Error.CLIENT_RESPONSE_TYPE_EXCEPTION);
                }
            } else {
                return P6eResultModel.build(P6eResultModel.Error.OAUTH2_AUTH_TYPE_NO_ENABLE);
            }
        }
    }

}
