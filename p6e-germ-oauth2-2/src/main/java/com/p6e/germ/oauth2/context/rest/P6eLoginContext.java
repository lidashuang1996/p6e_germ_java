package com.p6e.germ.oauth2.context.rest;

import com.p6e.germ.oauth2.application.P6eApplication;
import com.p6e.germ.oauth2.context.support.model.P6eCodeLoginParam;
import com.p6e.germ.oauth2.context.support.model.P6eDefaultLoginResult;
import com.p6e.germ.oauth2.infrastructure.utils.P6eCopyUtil;
import com.p6e.germ.oauth2.model.P6eModel;
import com.p6e.germ.oauth2.model.dto.P6eCodeLoginDto;
import com.p6e.germ.oauth2.model.dto.P6eLoginDto;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lidashuang
 * @version 1.0
 */
@RestController
@RequestMapping("/context/login")
public class P6eLoginContext {

    @RequestMapping("/code")
    public P6eModel code(P6eCodeLoginParam param) {
        final P6eLoginDto p6eLoginDto = P6eApplication.login.codeLogin(P6eCopyUtil.run(param, P6eCodeLoginDto.class));
        if (p6eLoginDto.getError() == null) {
            return P6eModel.build().setData(P6eCopyUtil.run(p6eLoginDto, P6eDefaultLoginResult.class));
        } else {
            return P6eModel.build(p6eLoginDto.getError());
        }
    }
}
