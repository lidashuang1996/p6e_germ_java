package com.p6e.germ.security.controller;

import com.p6e.germ.oauth2.controller.support.P6eBaseController;
import com.p6e.germ.oauth2.model.P6eResultConfig;
import com.p6e.germ.oauth2.model.P6eResultModel;
import com.p6e.germ.oauth2.utils.CopyUtil;
import com.p6e.germ.security.config.P6eSecurityConstant;
import com.p6e.germ.security.model.dto.*;
import com.p6e.germ.security.model.vo.*;
import com.p6e.germ.security.service.P6eSecurityJurisdictionService;
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
@RequestMapping("/security/jurisdiction")
public class P6eSecurityJurisdictionController extends P6eBaseController {

    /** 资源不存在 */
    public static final String ERROR_RESOURCES_NO_EXIST = "400-ERROR_RESOURCES_NO_EXIST";
    /** 资源存在关联数据 */
    public static final String ERROR_RESOURCES_EXISTENCE_RELATION_DATA = "ERROR_RESOURCES_EXISTENCE_RELATION_DATA";
    /** 服务器内部出现异常 */
    public static final String ERROR_SERVICE_INSIDE = "ERROR_SERVICE_INSIDE";

    /**
     * 注入的服务对象
     */
    @Resource
    private P6eSecurityJurisdictionService securityJurisdictionService;

    @P6eAuth
    @GetMapping("/")
    @P6eSecurity(values = {
            P6eSecurityConstant.ADMIN_AUTH_OWN,
            P6eSecurityConstant.ADMIN_JURISDICTION_SELECT_OWN
    }, condition = P6eSecurityType.Condition.AND)
    public P6eResultModel def(P6eSecurityJurisdictionParamVo param) {
        return select(param);
    }

    @P6eAuth
    @GetMapping("/list")
    @P6eSecurity(values = {
            P6eSecurityConstant.ADMIN_AUTH_OWN,
            P6eSecurityConstant.ADMIN_JURISDICTION_SELECT_OWN
    }, condition = P6eSecurityType.Condition.AND)
    public P6eResultModel select(P6eSecurityJurisdictionParamVo param) {
        try {
            final P6eListResultDto<P6eSecurityJurisdictionResultDto> p6eListResultDto =
                    securityJurisdictionService.select(CopyUtil.run(param, P6eSecurityJurisdictionParamDto.class));
            final P6eListResultVo<P6eSecurityJurisdictionResultVo> p6eListResultVo = new P6eListResultVo<>();
            p6eListResultVo.setPage(p6eListResultDto.getPage());
            p6eListResultVo.setSize(p6eListResultDto.getSize());
            p6eListResultVo.setTotal(p6eListResultDto.getTotal());
            p6eListResultVo.setList(CopyUtil.run(p6eListResultDto.getList(), P6eSecurityJurisdictionResultVo.class));
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
            P6eSecurityConstant.ADMIN_JURISDICTION_SELECT_OWN
    }, condition = P6eSecurityType.Condition.AND)
    public P6eResultModel select(@PathVariable Integer id) {
        try {
            final P6eSecurityJurisdictionResultDto p6eSecurityJurisdictionResultDto =
                    securityJurisdictionService.selectOneData(new P6eSecurityJurisdictionParamDto().setId(id));
            if (p6eSecurityJurisdictionResultDto == null) {
                return P6eResultModel.build(P6eResultConfig.ERROR_RESOURCES_NO_EXIST);
            } else {
                return P6eResultModel.build(P6eResultConfig.SUCCESS,
                        CopyUtil.run(p6eSecurityJurisdictionResultDto, P6eSecurityJurisdictionResultVo.class));
            }
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
            P6eSecurityConstant.ADMIN_JURISDICTION_CREATE_OWN
    }, condition = P6eSecurityType.Condition.AND)
    public P6eResultModel create(@RequestBody P6eSecurityJurisdictionParamVo param) {
        try {
            if (param == null
                    || param.getContent() == null
                    || param.getName() == null
                    || param.getDescribe() == null
                    || param.getCode() == null) {
                return P6eResultModel.build(P6eResultConfig.ERROR_PARAM_EXCEPTION);
            } else {
                final P6eSecurityJurisdictionResultDto p6eSecurityJurisdictionResultDto =
                        securityJurisdictionService.create(CopyUtil.run(param, P6eSecurityJurisdictionParamDto.class));
                if (p6eSecurityJurisdictionResultDto == null) {
                    return P6eResultModel.build(P6eResultConfig.ERROR_PARAM_EXCEPTION);
                } else {
                    return P6eResultModel.build(P6eResultConfig.SUCCESS,
                            CopyUtil.run(p6eSecurityJurisdictionResultDto, P6eSecurityJurisdictionResultVo.class));
                }
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
            P6eSecurityConstant.ADMIN_JURISDICTION_UPDATE_OWN
    }, condition = P6eSecurityType.Condition.AND)
    public P6eResultModel update(@PathVariable Integer id, @RequestBody P6eSecurityJurisdictionParamVo param) {
        try {
            if (param == null) {
                return P6eResultModel.build(P6eResultConfig.ERROR_PARAM_EXCEPTION);
            } else {
                final P6eSecurityJurisdictionResultDto p6eSecurityJurisdictionResultDto =
                        securityJurisdictionService.update(CopyUtil.run(param, P6eSecurityJurisdictionParamDto.class).setId(id));
                if (p6eSecurityJurisdictionResultDto == null) {
                    return P6eResultModel.build(P6eResultConfig.ERROR_PARAM_EXCEPTION);
                } else {
                    return P6eResultModel.build(P6eResultConfig.SUCCESS,
                            CopyUtil.run(p6eSecurityJurisdictionResultDto, P6eSecurityJurisdictionResultVo.class));
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
            P6eSecurityConstant.ADMIN_JURISDICTION_DELETE_OWN
    }, condition = P6eSecurityType.Condition.AND)
    public P6eResultModel delete(@PathVariable Integer id) {
        try {
            final P6eSecurityJurisdictionResultDto p6eSecurityJurisdictionResultDto =
                    securityJurisdictionService.delete(new P6eSecurityJurisdictionParamDto().setId(id));
            if (p6eSecurityJurisdictionResultDto == null) {
                return P6eResultModel.build(P6eResultConfig.ERROR_RESOURCES_NO_EXIST);
            } else if (ERROR_SERVICE_INSIDE.equals(p6eSecurityJurisdictionResultDto.getError())) {
                return P6eResultModel.build(P6eResultConfig.ERROR_SERVICE_INSIDE);
            } else if (ERROR_RESOURCES_EXISTENCE_RELATION_DATA.equals(p6eSecurityJurisdictionResultDto.getError())) {
                return P6eResultModel.build(P6eResultConfig.ERROR_RESOURCES_EXISTENCE_RELATION_DATA);
            } else if (ERROR_RESOURCES_NO_EXIST.equals(p6eSecurityJurisdictionResultDto.getError())) {
                return P6eResultModel.build(P6eResultConfig.ERROR_RESOURCES_NO_EXIST);
            } else {
                return P6eResultModel.build(P6eResultConfig.SUCCESS,
                        CopyUtil.run(p6eSecurityJurisdictionResultDto, P6eSecurityJurisdictionResultVo.class));
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            return P6eResultModel.build(P6eResultConfig.ERROR_SERVICE_INSIDE, e.getMessage());
        }
    }
}
