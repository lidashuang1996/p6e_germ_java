package com.p6e.germ.oauth2.context.controller;

import com.p6e.germ.oauth2.context.controller.support.P6eBaseController;
import com.p6e.germ.oauth2.context.controller.support.model.P6eModelConfig;
import com.p6e.germ.oauth2.context.controller.support.model.P6eResultModel;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 获取信息的接口
 * @author lidashuang
 * @version 1.0
 */
@RestController
@RequestMapping("/info")
public class P6eInfoController extends P6eBaseController {

    @RequestMapping
    public P6eResultModel def(final HttpServletRequest request, String access_token) {
        try {
            boolean status = false;
            final String authentication = request.getHeader("authentication");
            if (authentication != null) {
                // 执行一次
            } else {
                if (access_token != null) {
                    // 执行一次
                }
            }
            return P6eResultModel.build(P6eModelConfig.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            return P6eResultModel.build(P6eModelConfig.ERROR_SERVICE_INSIDE);
        }
    }

}

