package com.p6e.germ.security.controller;

import com.p6e.germ.oauth2.controller.support.P6eBaseController;
import com.p6e.germ.oauth2.model.P6eResultConfig;
import com.p6e.germ.oauth2.model.P6eResultModel;
import com.p6e.germ.oauth2.utils.CopyUtil;
import com.p6e.germ.security.config.P6eSecurityConstant;
import com.p6e.germ.security.model.dto.*;
import com.p6e.germ.security.model.vo.*;
import com.p6e.germ.security.service.P6eSecurityJurisdictionRelationGroupService;
import com.p6e.germ.starter.oauth2.P6eAuth;
import com.p6e.germ.starter.security.P6eSecurity;
import com.p6e.germ.starter.security.P6eSecurityType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 权限关联安全组接口
 * @author lidashuang
 * @version 1.0
 */
@RestController
@RequestMapping("/security/jurisdiction/group")
public class P6eSecurityJurisdictionRelationGroupController extends P6eBaseController {

    /**
     * 注入的服务对象
     */
    @Resource
    private P6eSecurityJurisdictionRelationGroupService securityJurisdictionRelationGroupService;

    @P6eAuth
    @GetMapping("/")
    @P6eSecurity(values = {
            P6eSecurityConstant.ADMIN_AUTH_OWN,
            P6eSecurityConstant.ADMIN_GROUP_RELATION_JURISDICTION_OWN
    }, condition = P6eSecurityType.Condition.AND)
    public P6eResultModel def(P6eSecurityJurisdictionRelationGroupParamVo param) {
        return list(param);
    }

    @P6eAuth
    @GetMapping("/list")
    @P6eSecurity(values = {
            P6eSecurityConstant.ADMIN_AUTH_OWN,
            P6eSecurityConstant.ADMIN_GROUP_RELATION_JURISDICTION_OWN
    }, condition = P6eSecurityType.Condition.AND)
    public P6eResultModel list(P6eSecurityJurisdictionRelationGroupParamVo param) {
        try {
            final P6eListResultDto<P6eSecurityJurisdictionRelationGroupResultDto> p6eListResultDto =
                    securityJurisdictionRelationGroupService.select(
                            CopyUtil.run(param, P6eSecurityJurisdictionRelationGroupParamDto.class));
            final P6eListResultVo<P6eSecurityJurisdictionRelationGroupResultVo> p6eListResultVo = new P6eListResultVo<>();
            p6eListResultVo.setPage(p6eListResultDto.getPage());
            p6eListResultVo.setSize(p6eListResultDto.getSize());
            p6eListResultVo.setTotal(p6eListResultDto.getTotal());
            p6eListResultVo.setList(
                    CopyUtil.run(p6eListResultDto.getList(), P6eSecurityJurisdictionRelationGroupResultVo.class));
            return P6eResultModel.build(P6eResultConfig.SUCCESS, p6eListResultVo);
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
            P6eSecurityConstant.ADMIN_GROUP_RELATION_JURISDICTION_OWN
    }, condition = P6eSecurityType.Condition.AND)
    public P6eResultModel create(@RequestBody P6eSecurityJurisdictionRelationGroupParamVo param) {
        try {
            if (param == null
                || param.getGid() == null
                || param.getJurisdictionId() == null
                || param.getJurisdictionParam() == null) {
                return P6eResultModel.build(P6eResultConfig.ERROR_PARAM_EXCEPTION);
            } else {
                final P6eSecurityJurisdictionRelationGroupResultDto p6eSecurityJurisdictionRelationGroupResultDto =
                        securityJurisdictionRelationGroupService.create(
                                CopyUtil.run(param, P6eSecurityJurisdictionRelationGroupParamDto.class));
                if (p6eSecurityJurisdictionRelationGroupResultDto == null) {
                    return P6eResultModel.build(P6eResultConfig.ERROR_RESOURCES_NO_EXIST);
                } else {
                    return P6eResultModel.build(P6eResultConfig.SUCCESS,
                            CopyUtil.run(p6eSecurityJurisdictionRelationGroupResultDto,
                                    P6eSecurityJurisdictionRelationGroupResultVo.class));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            return P6eResultModel.build(P6eResultConfig.ERROR_SERVICE_INSIDE, e.getMessage());
        }
    }

    @P6eAuth
    @DeleteMapping("/delete")
    @P6eSecurity(values = {
            P6eSecurityConstant.ADMIN_AUTH_OWN,
            P6eSecurityConstant.ADMIN_GROUP_RELATION_JURISDICTION_OWN
    }, condition = P6eSecurityType.Condition.AND)
    public P6eResultModel delete(P6eSecurityJurisdictionRelationGroupParamVo param) {
        try {
            final List<P6eSecurityJurisdictionRelationGroupResultDto> p6eSecurityJurisdictionRelationGroupResultDtoList =
                    securityJurisdictionRelationGroupService.delete(
                            CopyUtil.run(param, P6eSecurityJurisdictionRelationGroupParamDto.class));
            if (p6eSecurityJurisdictionRelationGroupResultDtoList == null) {
                return P6eResultModel.build(P6eResultConfig.ERROR_RESOURCES_OPERATION);
            } else {
                return P6eResultModel.build(P6eResultConfig.SUCCESS,
                        CopyUtil.run(p6eSecurityJurisdictionRelationGroupResultDtoList,
                                P6eSecurityJurisdictionRelationGroupResultVo.class));
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            return P6eResultModel.build(P6eResultConfig.ERROR_SERVICE_INSIDE, e.getMessage());
        }
    }
}
