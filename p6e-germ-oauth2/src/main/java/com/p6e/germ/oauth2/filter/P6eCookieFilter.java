package com.p6e.germ.oauth2.filter;

import com.p6e.germ.oauth2.utils.CommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 使用 web filter 实现浏览器存在标记
 * @author lidashuang
 * @version 1.0
 */
@WebFilter(filterName = "CookieFilter", urlPatterns = {"*"})
public class P6eCookieFilter implements Filter {

    /**
     * 注入日志系统
     */
    private static final Logger logger = LoggerFactory.getLogger(P6eCookieFilter.class);

    /** COOKIE 的名称 */
    private static final String COOKIE_NAME = "P6E_OAUTH2_COOKIE";

    @Override
    public void init(final FilterConfig filterConfig) {
        logger.info("Filter [ CookieFilter ] init complete ... ");
    }

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        // 获取请求头和返回头
        final HttpServletRequest request = (HttpServletRequest) servletRequest;
        final HttpServletResponse response = (HttpServletResponse) servletResponse;
        final Cookie[] cookies = request.getCookies();
        // 是否存在认证的 cookie 数据
        boolean bool = false;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (COOKIE_NAME.equals(cookie.getName().toUpperCase())) {
                    bool = true;
                    request.setAttribute(COOKIE_NAME, cookie.getValue());
                    break;
                }
            }
        }
        // 不存在 cookie 就写入一个新的
        if (!bool) {
            final String value = CommonUtil.generateUUID();
            request.setAttribute(COOKIE_NAME, value);
            Cookie cookie = new Cookie(COOKIE_NAME, value);
            cookie.setHttpOnly(true);
            cookie.setPath("/");
            response.addCookie(cookie);
        }

        // 进入内部执行后续的方法
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        logger.info("Filter [ CookieFilter ] destroy complete ... ");
    }
}
