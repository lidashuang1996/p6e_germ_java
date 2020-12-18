//package com.p6e.germ.jurisdiction.context.controller;
//
//import com.p6e.germ.jurisdiction.annotation.P6eJurisdiction;
//import com.p6e.germ.jurisdiction.condition.P6eCondition;
//import com.p6e.germ.jurisdiction.context.support.P6eBaseController;
//import com.p6e.germ.jurisdiction.context.support.P6eJurisdictionConstant;
//import com.p6e.germ.jurisdiction.model.P6eModel;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.annotation.Resource;
//
///**
// * 查询全部数据的接口
// * @author lidashuang
// * @version 1.0
// */
//@RestController
//@RequestMapping("/security/whole")
//public class P6eSecurityWholeDataController extends P6eBaseController {
//
//    /**
//     * 注入数据对象
//     */
//    @Resource
//    private P6eSecurityWholeDataService securityWholeDataService;
//
//    @GetMapping("/group/{id}")
//    @P6eJurisdiction(values = {
//            P6eJurisdictionConstant.ADMIN_AUTH_OWN,
//            P6eJurisdictionConstant.ADMIN_JURISDICTION_SELECT_OWN,
//            P6eJurisdictionConstant.ADMIN_GROUP_RELATION_JURISDICTION_OWN
//    }, condition = P6eCondition.AND)
//    public P6eModel group(@PathVariable Integer id) {
//        try {
//            final List<P6eSecurityWholeDataGroupResultDto> resultDtoList = securityWholeDataService.group(id);
//            return P6eResultModel.build(P6eResultConfig.SUCCESS,
//                    CopyUtil.run(resultDtoList, P6eSecurityWholeDataGroupResultVo.class));
//        } catch (Exception e) {
//            e.printStackTrace();
//            LOGGER.error(e.getMessage());
//            return P6eResultModel.build(P6eResultConfig.ERROR_SERVICE_INSIDE, e.getMessage());
//        }
//    }
//
//    @GetMapping("/user/{id}")
//    @P6eJurisdiction(values = {
//            P6eJurisdictionConstant.ADMIN_AUTH_OWN,
//            P6eJurisdictionConstant.ADMIN_USER_SELECT_OWN,
//            P6eJurisdictionConstant.ADMIN_USER_RELATION_GROUP_OWN,
//            P6eJurisdictionConstant.ADMIN_GROUP_RELATION_JURISDICTION_OWN
//    }, condition = P6eCondition.AND)
//    public P6eModel user(@PathVariable Integer id) {
//        try {
//            final P6eSecurityWholeDataUserResultDto resultDto = securityWholeDataService.user(id);
//            return P6eResultModel.build(P6eResultConfig.SUCCESS,
//                    CopyUtil.run(resultDto, P6eSecurityWholeDataUserResultVo.class));
//        } catch (Exception e) {
//            e.printStackTrace();
//            LOGGER.error(e.getMessage());
//            return P6eResultModel.build(P6eResultConfig.ERROR_SERVICE_INSIDE, e.getMessage());
//        }
//    }
//
//}
