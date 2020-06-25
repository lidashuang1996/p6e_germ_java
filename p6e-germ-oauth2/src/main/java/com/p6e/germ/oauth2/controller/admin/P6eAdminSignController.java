package com.p6e.germ.oauth2.controller.admin;

import com.p6e.germ.oauth2.controller.support.P6eBaseController;
import com.p6e.germ.oauth2.model.P6eResultConfig;
import com.p6e.germ.oauth2.model.P6eResultModel;
import com.p6e.germ.oauth2.model.dto.P6eAdminSignParamDto;
import com.p6e.germ.oauth2.model.dto.P6eAdminSignResultDto;
import com.p6e.germ.oauth2.model.vo.P6eAdminSignParamVo;
import com.p6e.germ.oauth2.service.P6eAdminSignService;
import com.p6e.germ.oauth2.utils.CopyUtil;
import org.apache.ibatis.annotations.Delete;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 该类为管理员账号，登陆、注册、退出、刷新 的接口实现
 * @author lidashuang
 * @version 1.0
 */
@RestController
@RequestMapping("/admin/sign")
public class P6eAdminSignController extends P6eBaseController {

    /**
     * 注入管理员账号管理的服务对象
     */
    @Resource
    private P6eAdminSignService adminSignService;

    @PostMapping("/in")
    public P6eResultModel in(@RequestBody P6eAdminSignParamVo param) {
        try {
            if (param == null
                || param.getAccount() == null
                || param.getPassword() == null) {
                return P6eResultModel.build(P6eResultConfig.ERROR_PARAM_EXCEPTION);
            } else {
                P6eAdminSignResultDto result = adminSignService.in(CopyUtil.run(param, P6eAdminSignParamDto.class));
                if (result == null || result.getError() != null) {
                    return P6eResultModel.build(P6eResultConfig.ERROR_ACCOUNT_OR_PASSOWR);
                } else {
                    return P6eResultModel.build(P6eResultConfig.SUCCESS, result);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return P6eResultModel.build(P6eResultConfig.ERROR_SERVICE_INSIDE);
        }
    }

    @PostMapping("/up")
    public P6eResultModel up(@RequestBody P6eAdminSignParamVo param) {
        try {
            if (param == null
                || param.getAccount() == null
                || param.getPassword() == null) {
                return P6eResultModel.build(P6eResultConfig.ERROR_PARAM_EXCEPTION);
            } else {
                P6eAdminSignResultDto p6eAdminSignResultDto = adminSignService.up(CopyUtil.run(param, P6eAdminSignParamDto.class));
                return P6eResultModel.build(P6eResultConfig.ERROR_SERVICE_INSIDE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return P6eResultModel.build(P6eResultConfig.ERROR_SERVICE_INSIDE);
        }
    }


    @Delete("/out")
    public P6eResultModel out(@RequestBody P6eAdminSignParamVo param) {
        try {
            if (param == null || param.getToken() == null) {
                return P6eResultModel.build(P6eResultConfig.ERROR_PARAM_EXCEPTION);
            } else {
                P6eAdminSignResultDto p6eAdminSignResultDto = adminSignService.out(CopyUtil.run(param, P6eAdminSignParamDto.class));
                return P6eResultModel.build(P6eResultConfig.ERROR_SERVICE_INSIDE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return P6eResultModel.build(P6eResultConfig.ERROR_SERVICE_INSIDE);
        }
    }


    @GetMapping("/refresh")
    public P6eResultModel refresh(P6eAdminSignParamVo param) {
        try {
            if (param == null || param.getToken() == null) {
                return P6eResultModel.build(P6eResultConfig.ERROR_PARAM_EXCEPTION);
            } else {
                P6eAdminSignResultDto p6eAdminSignResultDto = adminSignService.refresh(CopyUtil.run(param, P6eAdminSignParamDto.class));
                return P6eResultModel.build(P6eResultConfig.ERROR_SERVICE_INSIDE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return P6eResultModel.build(P6eResultConfig.ERROR_SERVICE_INSIDE);
        }
    }
}
