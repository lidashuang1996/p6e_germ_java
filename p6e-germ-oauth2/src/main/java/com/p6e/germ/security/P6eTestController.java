package com.p6e.germ.security;

import com.p6e.germ.oauth2.controller.support.P6eBaseController;
import com.p6e.germ.security.achieve.P6eSecurity;
import com.p6e.germ.security.achieve.P6eSecurityModel;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lidashuang
 * @version 1.0
 */
@RestController
@RequestMapping("/test")
public class P6eTestController extends P6eBaseController {

    @P6eSecurity
    @RequestMapping("/a")
    public Object a(P6eSecurityModel model) {
        return "3131313";
    }

}
