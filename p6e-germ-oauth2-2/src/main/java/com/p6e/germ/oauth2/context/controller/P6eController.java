package com.p6e.germ.oauth2.context.controller;

import com.p6e.germ.oauth2.context.controller.support.P6eBaseController;
import com.p6e.germ.oauth2.model.P6eAuthModel;
import com.p6e.germ.oauth2.model.P6eResultModel;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author lidashuang
 * @version 1.0
 */
@Api("Oauth2认证入口")
@RestController
public class P6eController extends P6eBaseController {

    @Resource
    private P6eAuthController controller;

    @RequestMapping("/")
    public P6eResultModel def(P6eAuthModel.VoParam param, String code,
                              HttpServletRequest request, HttpServletResponse response) {
        return controller.def(param, code, request, response);
    }

}
