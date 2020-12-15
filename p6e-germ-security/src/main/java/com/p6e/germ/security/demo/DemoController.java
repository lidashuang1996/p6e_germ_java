package com.p6e.germ.security.demo;

import com.p6e.germ.security.annotation.P6eSecurity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lidashuang
 * @version 1.0
 */
@RestController
@RequestMapping("/demo")
public class DemoController {

    /** 注入日志系统 */
    private static final Logger LOGGER = LoggerFactory.getLogger(DemoController.class);

    @P6eSecurity
    @RequestMapping("/")
    public Object def(DemoModel model) {
        LOGGER.info("model ==> " +  model.toString());
        return model;
    }

}
