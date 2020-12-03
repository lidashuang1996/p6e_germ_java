package com.p6e.germ.oauth2.context.controller;

import com.p6e.germ.oauth2.application.P6eApplication;
import com.p6e.germ.oauth2.context.controller.support.P6eBaseController;
import com.p6e.germ.oauth2.context.controller.support.model.P6eDefaultLoginParam;
import com.p6e.germ.oauth2.context.controller.support.model.P6eDefaultLoginResult;
import com.p6e.germ.oauth2.infrastructure.utils.P6eCopyUtil;
import com.p6e.germ.oauth2.model.P6eModel;
import com.p6e.germ.oauth2.model.dto.P6eDefaultLoginDto;
import com.p6e.germ.oauth2.model.dto.P6eLoginDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lidashuang
 * @version 1.0
 */
@RestController
@RequestMapping("/login")
public class P6eLoginController extends P6eBaseController {

    @PostMapping("/")
    public P6eModel def(@RequestBody P6eDefaultLoginParam param) {
        final P6eLoginDto p6eLoginDto = P6eApplication.login.defaultLogin(P6eCopyUtil.run(param, P6eDefaultLoginDto.class));
        if (p6eLoginDto.getError() == null) {
            return P6eModel.build().setData(P6eCopyUtil.run(p6eLoginDto, P6eDefaultLoginResult.class));
        } else {
            return P6eModel.build(p6eLoginDto.getError());
        }
    }
}
