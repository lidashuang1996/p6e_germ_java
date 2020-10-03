package com.p6e.germ.security.controller;

import com.p6e.germ.oauth2.controller.support.P6eBaseController;
import com.p6e.germ.oauth2.model.P6eResultConfig;
import com.p6e.germ.oauth2.model.P6eResultModel;
import com.p6e.germ.oauth2.utils.CopyUtil;
import com.p6e.germ.security.config.P6eSecurityConstant;
import com.p6e.germ.security.model.dto.*;
import com.p6e.germ.security.model.vo.*;
import com.p6e.germ.security.service.P6eSecurityUserService;
import com.p6e.germ.starter.oauth2.P6eAuth;
import com.p6e.germ.starter.security.P6eSecurity;
import com.p6e.germ.starter.security.P6eSecurityType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author lidashuang
 * @version 1.0
 */
@RestController
@RequestMapping("/security/user")
public class P6eSecurityUserController extends P6eBaseController {

    @Resource
    private P6eSecurityUserService securityUserService;

    @P6eAuth
    @GetMapping("/")
    @P6eSecurity(values = {
            P6eSecurityConstant.ADMIN_AUTH_OWN,
            P6eSecurityConstant.ADMIN_USER_SELECT_OWN
    }, condition = P6eSecurityType.Condition.AND)
    public P6eResultModel def(P6eSecurityUserParamVo param) {
        return select(param);
    }

    @P6eAuth
    @GetMapping("/list")
    @P6eSecurity(values = {
            P6eSecurityConstant.ADMIN_AUTH_OWN,
            P6eSecurityConstant.ADMIN_USER_SELECT_OWN
    }, condition = P6eSecurityType.Condition.AND)
    public P6eResultModel select(P6eSecurityUserParamVo param) {
        try {
            final P6eListResultDto<P6eSecurityUserResultDto> p6eListResultDto =
                    securityUserService.select(CopyUtil.run(param, P6eSecurityUserParamDto.class));
            final P6eListResultVo<P6eSecurityUserResultVo> p6eListResultVo = new P6eListResultVo<>();
            p6eListResultVo.setPage(p6eListResultDto.getPage());
            p6eListResultVo.setSize(p6eListResultDto.getSize());
            p6eListResultVo.setTotal(p6eListResultDto.getTotal());
            p6eListResultVo.setList(CopyUtil.run(p6eListResultDto.getList(), P6eSecurityUserResultVo.class));
            return P6eResultModel.build(P6eResultConfig.SUCCESS, p6eListResultVo);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            return P6eResultModel.build(P6eResultConfig.ERROR_SERVICE_INSIDE, e.getMessage());
        }
    }

    @P6eAuth
    @GetMapping("/{id}")
    @P6eSecurity(values = {
            P6eSecurityConstant.ADMIN_AUTH_OWN,
            P6eSecurityConstant.ADMIN_USER_SELECT_OWN
    }, condition = P6eSecurityType.Condition.AND)
    public P6eResultModel select(@PathVariable Integer id) {
        try {
            final P6eSecurityUserResultDto p6eSecurityUserResultDto =
                    securityUserService.selectOneData(new P6eSecurityUserParamDto().setId(id));
            return P6eResultModel.build(P6eResultConfig.SUCCESS,
                    CopyUtil.run(p6eSecurityUserResultDto, P6eSecurityUserResultVo.class));
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            return P6eResultModel.build(P6eResultConfig.ERROR_SERVICE_INSIDE, e.getMessage());
        }
    }

    @P6eAuth
    @PostMapping("/create")
    @P6eSecurity(values = {
            P6eSecurityConstant.ADMIN_AUTH_OWN,
            P6eSecurityConstant.ADMIN_USER_CREATE_OWN
    }, condition = P6eSecurityType.Condition.AND)
    public P6eResultModel create(@RequestBody P6eSecurityUserParamVo param) {
        try {
            if (param == null
                    || param.getName() == null
                    || param.getNickname() == null
                    || param.getSex() == null
                    || param.getAccount() == null
                    || param.getPassword() == null) {
                return P6eResultModel.build(P6eResultConfig.ERROR_PARAM_EXCEPTION);
            } else {
                final P6eSecurityUserResultDto p6eSecurityUserResultDto =
                        securityUserService.create(CopyUtil.run(param, P6eSecurityUserParamDto.class));
                return P6eResultModel.build(P6eResultConfig.SUCCESS,
                        CopyUtil.run(p6eSecurityUserResultDto, P6eSecurityUserResultVo.class));
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            return P6eResultModel.build(P6eResultConfig.ERROR_SERVICE_INSIDE, e.getMessage());
        }
    }

    @P6eAuth
    @PutMapping("/update/{id}")
    @P6eSecurity(values = {
            P6eSecurityConstant.ADMIN_AUTH_OWN,
            P6eSecurityConstant.ADMIN_USER_UPDATE_OWN
    }, condition = P6eSecurityType.Condition.AND)
    public P6eResultModel update(@PathVariable Integer id, @RequestBody P6eSecurityUserParamVo param) {
        try {
            if (param == null) {
                return P6eResultModel.build(P6eResultConfig.ERROR_PARAM_EXCEPTION);
            } else {
                final P6eSecurityUserResultDto p6eSecurityUserResultDto =
                        securityUserService.update(CopyUtil.run(param, P6eSecurityUserParamDto.class).setId(id));
                if (p6eSecurityUserResultDto == null) {
                    return P6eResultModel.build(P6eResultConfig.ERROR_RESOURCES_NO_EXIST);
                } else {
                    return P6eResultModel.build(P6eResultConfig.SUCCESS,
                            CopyUtil.run(p6eSecurityUserResultDto, P6eSecurityUserResultVo.class));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            return P6eResultModel.build(P6eResultConfig.ERROR_SERVICE_INSIDE, e.getMessage());
        }
    }

    @P6eAuth
    @DeleteMapping("/delete/{id}")
    @P6eSecurity(values = {
            P6eSecurityConstant.ADMIN_AUTH_OWN,
            P6eSecurityConstant.ADMIN_USER_DELETE_OWN
    }, condition = P6eSecurityType.Condition.AND)
    public P6eResultModel delete(@PathVariable Integer id) {
        try {
            final P6eSecurityUserResultDto p6eSecurityUserResultDto =
                    securityUserService.delete(new P6eSecurityUserParamDto().setId(id));
            if (p6eSecurityUserResultDto == null) {
                return P6eResultModel.build(P6eResultConfig.ERROR_RESOURCES_NO_EXIST);
            } else {
                return P6eResultModel.build(P6eResultConfig.SUCCESS,
                        CopyUtil.run(p6eSecurityUserResultDto, P6eSecurityUserResultVo.class));
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            return P6eResultModel.build(P6eResultConfig.ERROR_SERVICE_INSIDE, e.getMessage());
        }
    }

}
