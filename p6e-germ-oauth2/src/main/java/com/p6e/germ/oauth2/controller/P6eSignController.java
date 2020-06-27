package com.p6e.germ.oauth2.controller;

import com.p6e.germ.oauth2.controller.support.P6eBaseController;
import com.p6e.germ.oauth2.model.P6eResultConfig;
import com.p6e.germ.oauth2.model.P6eResultModel;
import com.p6e.germ.oauth2.model.vo.P6eSignInParamVo;
import com.p6e.germ.oauth2.utils.GsonUtil;
import org.springframework.web.bind.annotation.*;

/**
 * @author lidashuang
 * @version 1.0
 */
@RestController
@RequestMapping("/sign")
public class P6eSignController extends P6eBaseController {

    @PostMapping("/in")
    public P6eResultModel in(@RequestBody P6eSignInParamVo param) {
        try {
            logger.info(GsonUtil.toJson(param));
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
