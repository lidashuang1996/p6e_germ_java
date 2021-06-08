package com.p6e.germ.oauth2.context.controller.support;

import com.p6e.germ.common.utils.P6eJsonUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 自定义处理异常
 * @author lidashuang
 * @version 1.0
 */
@Controller
@RequestMapping("${server.error.path:${error.path:/error}}")
public class P6eExceptionController extends BasicErrorController {

    @Value("${server.error.path:${error.path:/error}}")
    private String path;

    /**
     * 自定义处理异常
     * @param server 实现父类的方法
     */
    public P6eExceptionController(ServerProperties server) {
        super(new DefaultErrorAttributes(), server.getError());
    }

    /**
     * 对异常的基础处理
     * @param request HttpServletRequest 请求对象
     * @return 异常结果数据对象
     */
    private Map<String, Object> base(HttpServletRequest request) {
        final Map<String, Object> result = new HashMap<>(20);
        final Map<String, Object> attributes = getErrorAttributes(request, ErrorAttributeOptions.defaults());
        result.put("path", attributes.get("path"));
        result.put("error", attributes.get("error"));
        result.put("status", attributes.get("status"));
        result.put("message", attributes.get("message"));
        result.put("timestamp", attributes.get("timestamp"));
        return result;
    }

    @Override
    public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
        final HttpStatus status = this.getStatus(request);
        return new ResponseEntity<>(base(request), status);
    }

    @Override
    public ModelAndView errorHtml(HttpServletRequest request, HttpServletResponse response) {
        try {
            response.getWriter().write(P6eJsonUtil.toJson(base(request)));
            response.getWriter().flush();
            response.getWriter().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
