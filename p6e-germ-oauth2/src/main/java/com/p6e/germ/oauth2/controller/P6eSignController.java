package com.p6e.germ.oauth2.controller;

import com.p6e.germ.oauth2.controller.support.P6eBaseController;
import com.p6e.germ.oauth2.model.P6eResultConfig;
import com.p6e.germ.oauth2.model.P6eResultModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lidashuang
 * @version 1.0
 */
@RestController
@RequestMapping("/sign")
public class P6eSignController extends P6eBaseController {

    @PostMapping("/in")
    public P6eResultModel in() {
        try {
            return P6eResultModel.build(P6eResultConfig.ERROR_SERVICE_INSIDE);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return P6eResultModel.build(P6eResultConfig.ERROR_SERVICE_INSIDE);
        }
    }

    @GetMapping("/refresh")
    public P6eResultModel refresh() {
        try {
            return P6eResultModel.build(P6eResultConfig.ERROR_SERVICE_INSIDE);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return P6eResultModel.build(P6eResultConfig.ERROR_SERVICE_INSIDE);
        }
    }

    @GetMapping("/out")
    public P6eResultModel out() {
        try {
            return P6eResultModel.build(P6eResultConfig.ERROR_SERVICE_INSIDE);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return P6eResultModel.build(P6eResultConfig.ERROR_SERVICE_INSIDE);
        }
    }

}
