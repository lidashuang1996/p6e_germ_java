package com.p6e.germ.jurisdiction.context.controller;

import com.p6e.germ.jurisdiction.annotation.P6eJurisdiction;
import com.p6e.germ.jurisdiction.application.P6eJurisdictionService;
import com.p6e.germ.jurisdiction.condition.P6eCondition;
import com.p6e.germ.jurisdiction.context.support.P6eBaseController;
import com.p6e.germ.jurisdiction.context.support.P6eJurisdictionConstant;
import com.p6e.germ.jurisdiction.context.support.model.P6eJurisdictionParam;
import com.p6e.germ.jurisdiction.context.support.model.P6eJurisdictionResult;
import com.p6e.germ.jurisdiction.context.support.model.P6eListResult;
import com.p6e.germ.jurisdiction.model.P6eModel;
import com.p6e.germ.jurisdiction.model.dto.P6eJurisdictionDto;
import com.p6e.germ.jurisdiction.utils.P6eCopyUtil;
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
    private P6eJurisdictionService p6eJurisdictionService;


    @GetMapping("/")
    @P6eJurisdiction(values = {
            P6eJurisdictionConstant.ADMIN_AUTH_OWN,
            P6eJurisdictionConstant.ADMIN_JURISDICTION_SELECT_OWN
    }, condition = P6eCondition.AND)
    public P6eModel def(P6eJurisdictionParam param) {
        return select(param);
    }

    @GetMapping("/list")
    @P6eJurisdiction(values = {
            P6eJurisdictionConstant.ADMIN_AUTH_OWN,
            P6eJurisdictionConstant.ADMIN_JURISDICTION_SELECT_OWN
    }, condition = P6eCondition.AND)
    public P6eModel select(P6eJurisdictionParam param) {
        final P6eJurisdictionDto p6eJurisdictionDto =
                p6eJurisdictionService.selectJurisdiction(P6eCopyUtil.run(param, P6eJurisdictionDto.class));
        final P6eListResult<P6eJurisdictionResult> p6eListResult = new P6eListResult<>();
        if (p6eJurisdictionDto.getError() == null) {
            return P6eModel.build().setData(P6eCopyUtil.run(p6eJurisdictionDto, p6eListResult));
        } else {
            return P6eModel.build(p6eJurisdictionDto.getError());
        }
    }

    @GetMapping("/{id}")
    @P6eJurisdiction(values = {
            P6eJurisdictionConstant.ADMIN_AUTH_OWN,
            P6eJurisdictionConstant.ADMIN_JURISDICTION_SELECT_OWN
    }, condition = P6eCondition.AND)
    public P6eModel select(@PathVariable Integer id) {
        final P6eJurisdictionDto p6eJurisdictionDto =
                p6eJurisdictionService.selectOneJurisdiction(new P6eJurisdictionDto().setId(id));
        if (p6eJurisdictionDto.getError() == null) {
            return P6eModel.build().setData(P6eCopyUtil.run(p6eJurisdictionDto, P6eJurisdictionResult.class));
        } else {
            return P6eModel.build(p6eJurisdictionDto.getError());
        }
    }

    @PostMapping("/create")
    @P6eJurisdiction(values = {
            P6eJurisdictionConstant.ADMIN_AUTH_OWN,
            P6eJurisdictionConstant.ADMIN_GROUP_CREATE_OWN
    }, condition = P6eCondition.AND)
    public P6eModel create(@RequestBody P6eJurisdictionParam param) {
        final P6eJurisdictionDto p6eJurisdictionDto =
                p6eJurisdictionService.deleteJurisdiction(P6eCopyUtil.run(param, P6eJurisdictionDto.class).setOperate("TEST"));
        if (p6eJurisdictionDto.getError() == null) {
            return P6eModel.build().setData(P6eCopyUtil.run(p6eJurisdictionDto, P6eJurisdictionResult.class));
        } else {
            return P6eModel.build(p6eJurisdictionDto.getError());
        }
    }

    @PutMapping("/update/{id}")
    @P6eJurisdiction(values = {
            P6eJurisdictionConstant.ADMIN_AUTH_OWN,
            P6eJurisdictionConstant.ADMIN_GROUP_UPDATE_OWN
    }, condition = P6eCondition.AND)
    public P6eModel update(@PathVariable Integer id, @RequestBody P6eJurisdictionParam param) {
        final P6eJurisdictionDto p6eJurisdictionDto =
                p6eJurisdictionService.updateJurisdiction(
                        P6eCopyUtil.run(param, P6eJurisdictionDto.class).setId(id).setOperate("TEST"));
        if (p6eJurisdictionDto.getError() == null) {
            return P6eModel.build().setData(P6eCopyUtil.run(p6eJurisdictionDto, P6eJurisdictionResult.class));
        } else {
            return P6eModel.build(p6eJurisdictionDto.getError());
        }
    }

    @DeleteMapping("/delete/{id}")
    @P6eJurisdiction(values = {
            P6eJurisdictionConstant.ADMIN_AUTH_OWN,
            P6eJurisdictionConstant.ADMIN_GROUP_DELETE_OWN
    }, condition = P6eCondition.AND)
    public P6eModel delete(@PathVariable Integer id) {
        final P6eJurisdictionDto p6eJurisdictionDto =
                p6eJurisdictionService.deleteJurisdiction(new P6eJurisdictionDto().setId(id));
        if (p6eJurisdictionDto.getError() == null) {
            return P6eModel.build().setData(P6eCopyUtil.run(p6eJurisdictionDto, P6eJurisdictionResult.class));
        } else {
            return P6eModel.build(p6eJurisdictionDto.getError());
        }
    }
}
