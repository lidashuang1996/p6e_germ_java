package com.p6e.germ.oauth2.context.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lidashuang
 * @version 1.0
 */
@RestController
@RequestMapping("/context/login")
public class P6eLoginContext {

    /**
     * 请求的携带认证信息的参数
     */
    private static final String AUTH_PARAM_NAME = "access_token";
//
//    @RequestMapping("/mark/{id}")
//    public P6eResultModel mark(@PathVariable("id") String mark) {
//        final P6eAuthDto p6eAuthDto = P6eApplication.auth.mark(mark);
//        if (p6eAuthDto.getError() == null) {
//            return P6eResultModel.build().setData(P6eCopyUtil.run(p6eAuthDto, P6eAuthResult.class));
//        } else {
//            return P6eResultModel.build(p6eAuthDto.getError());
//        }
//    }




}
