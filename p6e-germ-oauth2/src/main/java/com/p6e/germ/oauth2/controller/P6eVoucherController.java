package com.p6e.germ.oauth2.controller;

import com.p6e.germ.oauth2.controller.support.P6eBaseController;
import com.p6e.germ.oauth2.model.P6eResultConfig;
import com.p6e.germ.oauth2.model.P6eResultModel;
import com.p6e.germ.oauth2.model.dto.P6eVoucherResultDto;
import com.p6e.germ.oauth2.model.vo.P6eVoucherResultVo;
import com.p6e.germ.oauth2.service.P6eVoucherService;
import com.p6e.germ.oauth2.utils.CopyUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 获取凭证的接口
 * @author lidashuang
 * @version 1.0
 */
@RestController
@RequestMapping("/voucher")
public class P6eVoucherController extends P6eBaseController {

    /** 注入 凭证 服务 */
    @Resource
    private P6eVoucherService p6eVoucherService;

    /**
     * 默认实现凭证的获取的方法
     * @return 通用返回对象
     */
    @RequestMapping
    public P6eResultModel def() {
        try {
            P6eVoucherResultDto p6eVoucherResultDto = p6eVoucherService.generate();
            if (p6eVoucherResultDto == null) {
                return P6eResultModel.build(P6eResultConfig.ERROR_VOUCHER_GENERATE);
            } else {
                return P6eResultModel.build(P6eResultConfig.SUCCESS,
                        CopyUtil.run(p6eVoucherResultDto, P6eVoucherResultVo.class));
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            return P6eResultModel.build(P6eResultConfig.ERROR_SERVICE_INSIDE);
        }
    }

}
