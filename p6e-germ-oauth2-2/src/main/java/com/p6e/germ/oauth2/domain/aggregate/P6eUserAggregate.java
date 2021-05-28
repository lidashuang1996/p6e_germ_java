package com.p6e.germ.oauth2.domain.aggregate;

import com.p6e.germ.oauth2.domain.entity.P6eOtherQqLoginEntity;

import java.util.Map;

/**
 * @author lidashuang
 * @version 1.0
 */
public class P6eUserAggregate {
    public static P6eUserAggregate getQqLoginData(String a, String b) {
        // 获取的用户信息
//        final Map<String, String> map = P6eOtherQqLoginEntity.getData(param.getMark());
        return new P6eUserAggregate();
    }
}
