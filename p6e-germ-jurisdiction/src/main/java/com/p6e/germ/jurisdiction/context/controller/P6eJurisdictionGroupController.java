package com.p6e.germ.jurisdiction.context.controller;



import com.p6e.germ.jurisdiction.annotation.P6eJurisdiction;
import com.p6e.germ.jurisdiction.application.P6eJurisdictionService;
import com.p6e.germ.jurisdiction.condition.P6eCondition;
import com.p6e.germ.jurisdiction.context.support.P6eBaseController;
import com.p6e.germ.jurisdiction.context.support.P6eJurisdictionConstant;
import com.p6e.germ.jurisdiction.context.support.model.*;
import com.p6e.germ.jurisdiction.model.P6eModel;
import com.p6e.germ.jurisdiction.model.dto.P6eJurisdictionDto;
import com.p6e.germ.jurisdiction.model.dto.P6eJurisdictionGroupDto;
import com.p6e.germ.jurisdiction.utils.P6eCopyUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 权限组的接口
 * @author lidashuang
 * @version 1.0
 */
@RestController
@RequestMapping("/security/group")
public class P6eJurisdictionGroupController extends P6eBaseController {

    /** 资源不存在 */
    public static final String ERROR_RESOURCES_NO_EXIST = "ERROR_RESOURCES_NO_EXIST";
    /** 资源存在关联数据 */
    public static final String ERROR_RESOURCES_EXISTENCE_RELATION_DATA = "ERROR_RESOURCES_EXISTENCE_RELATION_DATA";
    /** 服务器内部出现异常 */
    public static final String ERROR_SERVICE_INSIDE = "ERROR_SERVICE_INSIDE";

    /**
     * 注入的服务对象
     */
    @Resource
    private P6eJurisdictionService p6eJurisdictionService;

    @GetMapping("/")
    @P6eJurisdiction(values = {
            P6eJurisdictionConstant.ADMIN_AUTH_OWN,
            P6eJurisdictionConstant.ADMIN_GROUP_SELECT_OWN
    }, condition = P6eCondition.AND)
    public P6eModel def(P6eJurisdictionGroupParam param) {
        return select(param);
    }

    @GetMapping("/list")
    @P6eJurisdiction(values = {
            P6eJurisdictionConstant.ADMIN_AUTH_OWN,
            P6eJurisdictionConstant.ADMIN_GROUP_SELECT_OWN
    }, condition = P6eCondition.AND)
    public P6eModel select(P6eJurisdictionGroupParam param) {
        final P6eJurisdictionGroupDto p6eJurisdictionGroupDto =
                p6eJurisdictionService.selectJurisdictionGroup(P6eCopyUtil.run(param, P6eJurisdictionGroupDto.class));
        final P6eListResult<P6eJurisdictionGroupResult> p6eListResult = new P6eListResult<>();
        if (p6eJurisdictionGroupDto.getError() == null) {
            return P6eModel.build().setData(P6eCopyUtil.run(p6eJurisdictionGroupDto, p6eListResult));
        } else {
            return P6eModel.build(p6eJurisdictionGroupDto.getError());
        }
    }

    @GetMapping("/{id}")
    @P6eJurisdiction(values = {
            P6eJurisdictionConstant.ADMIN_AUTH_OWN,
            P6eJurisdictionConstant.ADMIN_GROUP_SELECT_OWN
    }, condition = P6eCondition.AND)
    public P6eModel select(@PathVariable Integer id) {
        final P6eJurisdictionGroupDto p6eJurisdictionGroupDto =
                p6eJurisdictionService.selectOneJurisdictionGroup(new P6eJurisdictionGroupDto().setId(id));
        if (p6eJurisdictionGroupDto.getError() == null) {
            return P6eModel.build().setData(P6eCopyUtil.run(p6eJurisdictionGroupDto, P6eJurisdictionGroupResult.class));
        } else {
            return P6eModel.build(p6eJurisdictionGroupDto.getError());
        }
    }

    @PostMapping("/create")
    @P6eJurisdiction(values = {
            P6eJurisdictionConstant.ADMIN_AUTH_OWN,
            P6eJurisdictionConstant.ADMIN_GROUP_CREATE_OWN
    }, condition = P6eCondition.AND)
    public P6eModel create(@RequestBody P6eJurisdictionGroupParam param) {
        final P6eJurisdictionGroupDto p6eJurisdictionGroupDto =
                p6eJurisdictionService.createJurisdictionGroup(P6eCopyUtil.run(param, P6eJurisdictionGroupDto.class).setOperate("TEST"));
        if (p6eJurisdictionGroupDto.getError() == null) {
            return P6eModel.build().setData(P6eCopyUtil.run(p6eJurisdictionGroupDto, P6eJurisdictionGroupResult.class));
        } else {
            return P6eModel.build(p6eJurisdictionGroupDto.getError());
        }
    }

    @PutMapping("/update/{id}")
    @P6eJurisdiction(values = {
            P6eJurisdictionConstant.ADMIN_AUTH_OWN,
            P6eJurisdictionConstant.ADMIN_GROUP_UPDATE_OWN
    }, condition = P6eCondition.AND)
    public P6eModel update(@PathVariable Integer id, @RequestBody P6eJurisdictionParam param) {
        final P6eJurisdictionGroupDto p6eJurisdictionGroupDto =
                p6eJurisdictionService.updateJurisdictionGroup(
                        P6eCopyUtil.run(param, P6eJurisdictionGroupDto.class).setId(id).setOperate("TEST"));
        if (p6eJurisdictionGroupDto.getError() == null) {
            return P6eModel.build().setData(P6eCopyUtil.run(p6eJurisdictionGroupDto, P6eJurisdictionGroupResult.class));
        } else {
            return P6eModel.build(p6eJurisdictionGroupDto.getError());
        }
    }

    @DeleteMapping("/delete/{id}")
    @P6eJurisdiction(values = {
            P6eJurisdictionConstant.ADMIN_AUTH_OWN,
            P6eJurisdictionConstant.ADMIN_GROUP_DELETE_OWN
    }, condition = P6eCondition.AND)
    public P6eModel delete(@PathVariable Integer id) {
        final P6eJurisdictionGroupDto p6eJurisdictionGroupDto =
                p6eJurisdictionService.deleteJurisdictionGroup(new P6eJurisdictionGroupDto().setId(id));
        if (p6eJurisdictionGroupDto.getError() == null) {
            return P6eModel.build().setData(P6eCopyUtil.run(p6eJurisdictionGroupDto, P6eJurisdictionGroupResult.class));
        } else {
            return P6eModel.build(p6eJurisdictionGroupDto.getError());
        }
    }
}
