package com.p6e.germ.oauth2.application;

import com.p6e.germ.oauth2.context.controller.support.model.P6eDefaultLoginParam;
import com.p6e.germ.oauth2.domain.aggregate.P6eDefaultLoginAggregate;
import com.p6e.germ.oauth2.domain.keyvalue.P6eDefaultAuthKeyValue;
import org.springframework.stereotype.Service;

/**
 * @author lidashuang
 * @version 1.0
 */
@Service
public class P6eLoginService {

    /**
     * 默认登录的 application 层
     * @param param 默认的登录的参数
     */
    public P6eDefaultAuthKeyValue defaultLogin(P6eDefaultLoginParam param) {
        return P6eDefaultLoginAggregate.create(
                param.getMark(),
                param.getVoucher(),
                param.getAccount(),
                param.getPassword()
        ).verification();
    }

}
