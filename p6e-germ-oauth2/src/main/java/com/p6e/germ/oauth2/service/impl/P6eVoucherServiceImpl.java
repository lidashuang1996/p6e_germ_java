package com.p6e.germ.oauth2.service.impl;

import com.p6e.germ.oauth2.service.P6eVoucherService;
import com.p6e.germ.oauth2.utils.CommonUtil;

/**
 * @author lidashuang
 * @version 1.0
 */
public class P6eVoucherServiceImpl implements P6eVoucherService {

    @Override
    public String generate() {
        CommonUtil.generateUUID();
        return null;
    }
}
