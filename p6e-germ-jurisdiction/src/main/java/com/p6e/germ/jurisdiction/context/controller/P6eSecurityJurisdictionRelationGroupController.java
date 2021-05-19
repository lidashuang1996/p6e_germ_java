//package com.p6e.germ.jurisdiction.context.controller;
//import com.p6e.germ.jurisdiction.annotation.P6eJurisdiction;
//import com.p6e.germ.jurisdiction.application.P6eJurisdictionService;
//import com.p6e.germ.jurisdiction.condition.P6eCondition;
//import com.p6e.germ.jurisdiction.context.support.P6eBaseController;
//import com.p6e.germ.jurisdiction.context.support.P6eJurisdictionConstant;
//import com.p6e.germ.jurisdiction.context.support.model.*;
//import com.p6e.germ.jurisdiction.model.P6eModel;
//import com.p6e.germ.jurisdiction.model.dto.P6eJurisdictionGroupDto;
//import com.p6e.germ.jurisdiction.model.dto.P6eJurisdictionRelationGroupDto;
//import com.p6e.germ.jurisdiction.utils.P6eCopyUtil;
//import org.springframework.web.bind.annotation.*;
//
//import javax.annotation.Resource;
//import java.util.List;
//
///**
// * 权限关联安全组接口
// * @author lidashuang
// * @version 1.0
// */
//@RestController
//@RequestMapping("/security/jurisdiction/group")
//public class P6eSecurityJurisdictionRelationGroupController extends P6eBaseController {
//
//    /**
//     * 注入的服务对象
//     */
//    @Resource
//    private P6eJurisdictionService p6eJurisdictionService;
//
//    @GetMapping("/")
//    @P6eJurisdiction(values = {
//            P6eJurisdictionConstant.ADMIN_AUTH_OWN,
//            P6eJurisdictionConstant.ADMIN_GROUP_RELATION_JURISDICTION_OWN
//    }, condition = P6eCondition.AND)
//    public P6eModel def(P6eJurisdictionRelationGroupParam param) {
//        return list(param);
//    }
//
//    @GetMapping("/list")
//    @P6eJurisdiction(values = {
//            P6eJurisdictionConstant.ADMIN_AUTH_OWN,
//            P6eJurisdictionConstant.ADMIN_GROUP_RELATION_JURISDICTION_OWN
//    }, condition = P6eCondition.AND)
//    public P6eModel list(P6eJurisdictionRelationGroupParam param) {
//        final P6eJurisdictionRelationGroupDto p6eJurisdictionRelationGroupDto =
//                p6eJurisdictionService.selectJurisdictionRelationGroup(P6eCopyUtil.run(param, P6eJurisdictionRelationGroupDto.class));
//        final P6eListResult<P6eJurisdictionRelationGroupResult> p6eListResult = new P6eListResult<>();
//        if (p6eJurisdictionRelationGroupDto.getError() == null) {
//            return P6eModel.build().setData(P6eCopyUtil.run(p6eJurisdictionRelationGroupDto, p6eListResult));
//        } else {
//            return P6eModel.build(p6eJurisdictionRelationGroupDto.getError());
//        }
//    }
//
//    @GetMapping("/get")
//    @P6eJurisdiction(values = {
//            P6eJurisdictionConstant.ADMIN_AUTH_OWN,
//            P6eJurisdictionConstant.ADMIN_GROUP_RELATION_JURISDICTION_OWN
//    }, condition = P6eCondition.AND)
//    public P6eModel get(P6eJurisdictionRelationGroupParam param) {
//        final P6eJurisdictionRelationGroupDto p6eJurisdictionRelationGroupDto =
//                p6eJurisdictionService.selectOneJurisdictionRelationGroup(P6eCopyUtil.run(param, P6eJurisdictionRelationGroupDto.class));
//        if (p6eJurisdictionRelationGroupDto.getError() == null) {
//            return P6eModel.build().setData(P6eCopyUtil.run(p6eJurisdictionRelationGroupDto, P6eJurisdictionRelationGroupResult.class));
//        } else {
//            return P6eModel.build(p6eJurisdictionRelationGroupDto.getError());
//        }
//    }
//
//    @PostMapping("/create")
//    @P6eJurisdiction(values = {
//            P6eJurisdictionConstant.ADMIN_AUTH_OWN,
//            P6eJurisdictionConstant.ADMIN_GROUP_RELATION_JURISDICTION_OWN
//    }, condition = P6eCondition.AND)
//    public P6eModel create(@RequestBody P6eJurisdictionRelationGroupParam param) {
//        final P6eJurisdictionRelationGroupDto p6eJurisdictionRelationGroupDto =
//                p6eJurisdictionService.createJurisdictionRelationGroup(
//                        P6eCopyUtil.run(param, P6eJurisdictionRelationGroupDto.class).setOperate("TEST"));
//        if (p6eJurisdictionRelationGroupDto.getError() == null) {
//            return P6eModel.build().setData(P6eCopyUtil.run(p6eJurisdictionRelationGroupDto, P6eJurisdictionRelationGroupResult.class));
//        } else {
//            return P6eModel.build(p6eJurisdictionRelationGroupDto.getError());
//        }
//    }
//
//    @DeleteMapping("/delete")
//    @P6eJurisdiction(values = {
//            P6eJurisdictionConstant.ADMIN_AUTH_OWN,
//            P6eJurisdictionConstant.ADMIN_GROUP_RELATION_JURISDICTION_OWN
//    }, condition = P6eCondition.AND)
//    public P6eModel delete(P6eJurisdictionRelationGroupParam param) {
//        final P6eJurisdictionRelationGroupDto p6eJurisdictionRelationGroupDto =
//                p6eJurisdictionService.deleteJurisdictionRelationGroup(P6eCopyUtil.run(param, P6eJurisdictionRelationGroupDto.class));
//        if (p6eJurisdictionRelationGroupDto.getError() == null) {
//            return P6eModel.build().setData(P6eCopyUtil.run(p6eJurisdictionRelationGroupDto, P6eJurisdictionRelationGroupResult.class));
//        } else {
//            return P6eModel.build(p6eJurisdictionRelationGroupDto.getError());
//        }
//    }
//}
