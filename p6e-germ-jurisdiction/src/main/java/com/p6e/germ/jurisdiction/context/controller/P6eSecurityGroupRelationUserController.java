package com.p6e.germ.jurisdiction.context.controller;

import com.p6e.germ.jurisdiction.annotation.P6eJurisdiction;
import com.p6e.germ.jurisdiction.application.P6eJurisdictionService;
import com.p6e.germ.jurisdiction.condition.P6eCondition;
import com.p6e.germ.jurisdiction.context.support.P6eBaseController;
import com.p6e.germ.jurisdiction.context.support.P6eJurisdictionConstant;
import com.p6e.germ.jurisdiction.context.support.model.P6eListResult;
import com.p6e.germ.jurisdiction.context.support.model.P6eSecurityGroupRelationUserParam;
import com.p6e.germ.jurisdiction.context.support.model.P6eSecurityGroupRelationUserResult;
import com.p6e.germ.jurisdiction.model.P6eModel;
import com.p6e.germ.jurisdiction.model.dto.P6eJurisdictionGroupRelationUserDto;
import com.p6e.germ.jurisdiction.utils.P6eCopyUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 安全组关联用户接口
 * @author lidashuang
 * @version 1.0
 */
@RestController
@RequestMapping("/security/group/user")
public class P6eSecurityGroupRelationUserController extends P6eBaseController {

    /**
     * 注入的服务对象
     */
    @Resource
    private P6eJurisdictionService p6eJurisdictionService;

    @GetMapping("/")
    @P6eJurisdiction(values = {
            P6eJurisdictionConstant.ADMIN_AUTH_OWN,
            P6eJurisdictionConstant.ADMIN_USER_RELATION_GROUP_OWN
    }, condition = P6eCondition.AND)
    public P6eModel def(P6eSecurityGroupRelationUserParam param) {
        return list(param);
    }

    @GetMapping("/list")
    @P6eJurisdiction(values = {
            P6eJurisdictionConstant.ADMIN_AUTH_OWN,
            P6eJurisdictionConstant.ADMIN_USER_RELATION_GROUP_OWN
    }, condition = P6eCondition.AND)
    public P6eModel list(P6eSecurityGroupRelationUserParam param) {
        final P6eJurisdictionGroupRelationUserDto p6eJurisdictionGroupRelationUserDto =
                p6eJurisdictionService.deleteJurisdictionGroupRelationUser(P6eCopyUtil.run(param, P6eJurisdictionGroupRelationUserDto.class));
        final P6eListResult<P6eSecurityGroupRelationUserResult> p6eListResult = new P6eListResult<>();
        if (p6eJurisdictionGroupRelationUserDto.getError() == null) {
            return P6eModel.build().setData(P6eCopyUtil.run(p6eJurisdictionGroupRelationUserDto, p6eListResult));
        } else {
            return P6eModel.build(p6eJurisdictionGroupRelationUserDto.getError());
        }
    }

    @GetMapping("/get")
    @P6eJurisdiction(values = {
            P6eJurisdictionConstant.ADMIN_AUTH_OWN,
            P6eJurisdictionConstant.ADMIN_USER_RELATION_GROUP_OWN
    }, condition = P6eCondition.AND)
    public P6eModel get(P6eSecurityGroupRelationUserParam param) {
        final P6eJurisdictionGroupRelationUserDto p6eJurisdictionGroupRelationUserDto =
                p6eJurisdictionService.selectOneJurisdictionGroupRelationUser(P6eCopyUtil.run(param, P6eJurisdictionGroupRelationUserDto.class));
        if (p6eJurisdictionGroupRelationUserDto.getError() == null) {
            return P6eModel.build().setData(P6eCopyUtil.run(p6eJurisdictionGroupRelationUserDto, P6eSecurityGroupRelationUserResult.class));
        } else {
            return P6eModel.build(p6eJurisdictionGroupRelationUserDto.getError());
        }
    }

    @PostMapping("/create")
    @P6eJurisdiction(values = {
            P6eJurisdictionConstant.ADMIN_AUTH_OWN,
            P6eJurisdictionConstant.ADMIN_USER_RELATION_GROUP_OWN
    }, condition = P6eCondition.AND)
    public P6eModel create(@RequestBody P6eSecurityGroupRelationUserParam param) {
        final P6eJurisdictionGroupRelationUserDto p6eJurisdictionGroupRelationUserDto =
                p6eJurisdictionService.createJurisdictionGroupRelationUser(
                        P6eCopyUtil.run(param, P6eJurisdictionGroupRelationUserDto.class).setOperate("TEST"));
        if (p6eJurisdictionGroupRelationUserDto.getError() == null) {
            return P6eModel.build().setData(P6eCopyUtil.run(p6eJurisdictionGroupRelationUserDto, P6eSecurityGroupRelationUserResult.class));
        } else {
            return P6eModel.build(p6eJurisdictionGroupRelationUserDto.getError());
        }
    }

    @DeleteMapping("/delete")
    @P6eJurisdiction(values = {
            P6eJurisdictionConstant.ADMIN_AUTH_OWN,
            P6eJurisdictionConstant.ADMIN_USER_RELATION_GROUP_OWN
    }, condition = P6eCondition.AND)
    public P6eModel delete(P6eSecurityGroupRelationUserParam param) {
        final P6eJurisdictionGroupRelationUserDto p6eJurisdictionGroupRelationUserDto =
                p6eJurisdictionService.deleteJurisdictionGroupRelationUser(P6eCopyUtil.run(param, P6eJurisdictionGroupRelationUserDto.class));
        if (p6eJurisdictionGroupRelationUserDto.getError() == null) {
            return P6eModel.build().setData(P6eCopyUtil.run(p6eJurisdictionGroupRelationUserDto, P6eSecurityGroupRelationUserResult.class));
        } else {
            return P6eModel.build(p6eJurisdictionGroupRelationUserDto.getError());
        }
    }
}
