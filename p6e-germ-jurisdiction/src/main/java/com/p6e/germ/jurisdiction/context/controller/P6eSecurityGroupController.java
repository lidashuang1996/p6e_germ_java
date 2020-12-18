//package com.p6e.germ.jurisdiction.context.controller;
//
//
//
//import com.p6e.germ.jurisdiction.annotation.P6eJurisdiction;
//import com.p6e.germ.jurisdiction.context.support.P6eJurisdictionConstant;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.annotation.Resource;
//
///**
// * 权限组的接口
// * @author lidashuang
// * @version 1.0
// */
//@RestController
//@RequestMapping("/security/group")
//public class P6eSecurityGroupController extends P6eBaseController {
//
//    /** 资源不存在 */
//    public static final String ERROR_RESOURCES_NO_EXIST = "ERROR_RESOURCES_NO_EXIST";
//    /** 资源存在关联数据 */
//    public static final String ERROR_RESOURCES_EXISTENCE_RELATION_DATA = "ERROR_RESOURCES_EXISTENCE_RELATION_DATA";
//    /** 服务器内部出现异常 */
//    public static final String ERROR_SERVICE_INSIDE = "ERROR_SERVICE_INSIDE";
//
//    /**
//     * 注入的服务对象
//     */
//    @Resource
//    private P6eSecurityGroupService securityGroupService;
//
//    @Resource
//    private P6eConfig config;
//
//    @GetMapping("/")
//    @P6eJurisdiction(values = {
//            P6eJurisdictionConstant.ADMIN_AUTH_OWN,
//            P6eJurisdictionConstant.ADMIN_GROUP_SELECT_OWN
//    }, condition = P6eSecurityType.Condition.AND)
//    public P6eResultModel def(P6eSecurityGroupParamVo param) {
//        return select(param);
//    }
//
//    @GetMapping("/token")
//    public P6eResultModel defToken(String token, P6eSecurityGroupParamVo param) {
//        if (token != null
//                && config != null
//                && config.getSecurity() != null
//                && config.getSecurity().getToken() != null
//                && token.equals(config.getSecurity().getToken())) {
//            return select(param);
//        } else {
//            return P6eResultModel.build(P6eResultConfig.ERROR_TOKEN_NO_EXISTENCE);
//        }
//    }
//
//    @GetMapping("/list")
//    @P6eJurisdiction(values = {
//            P6eJurisdictionConstant.ADMIN_AUTH_OWN,
//            P6eJurisdictionConstant.ADMIN_GROUP_SELECT_OWN
//    }, condition = P6eSecurityType.Condition.AND)
//    public P6eResultModel select(P6eSecurityGroupParamVo param) {
//        try {
//            final P6eListResultDto<P6eSecurityGroupResultDto> p6eListResultDto =
//                    securityGroupService.select(CopyUtil.run(param, P6eSecurityGroupParamDto.class));
//            final P6eListResultVo<P6eSecurityGroupResultVo> p6eListResultVo = new P6eListResultVo<>();
//            p6eListResultVo.setPage(p6eListResultDto.getPage());
//            p6eListResultVo.setSize(p6eListResultDto.getSize());
//            p6eListResultVo.setTotal(p6eListResultDto.getTotal());
//            p6eListResultVo.setList(CopyUtil.run(p6eListResultDto.getList(), P6eSecurityGroupResultVo.class));
//            return P6eResultModel.build(P6eResultConfig.SUCCESS, p6eListResultVo);
//        } catch (Exception e) {
//            e.printStackTrace();
//            LOGGER.error(e.getMessage());
//            return P6eResultModel.build(P6eResultConfig.ERROR_SERVICE_INSIDE, e.getMessage());
//        }
//    }
//
//    @P6eAuth
//    @GetMapping("/{id}")
//    @P6eSecurity(values = {
//            P6eSecurityConstant.ADMIN_AUTH_OWN,
//            P6eSecurityConstant.ADMIN_GROUP_SELECT_OWN
//    }, condition = P6eSecurityType.Condition.AND)
//    public P6eResultModel select(@PathVariable Integer id) {
//        try {
//            final P6eSecurityGroupResultDto p6eSecurityGroupResultDto =
//                    securityGroupService.selectOneData(new P6eSecurityGroupParamDto().setId(id));
//            return P6eResultModel.build(P6eResultConfig.SUCCESS,
//                    CopyUtil.run(p6eSecurityGroupResultDto, P6eSecurityGroupResultVo.class));
//        } catch (Exception e) {
//            e.printStackTrace();
//            LOGGER.error(e.getMessage());
//            return P6eResultModel.build(P6eResultConfig.ERROR_SERVICE_INSIDE, e.getMessage());
//        }
//    }
//
//    @P6eAuth
//    @PostMapping("/create")
//    @P6eSecurity(values = {
//            P6eSecurityConstant.ADMIN_AUTH_OWN,
//            P6eSecurityConstant.ADMIN_GROUP_CREATE_OWN
//    }, condition = P6eSecurityType.Condition.AND)
//    public P6eResultModel create(@RequestBody P6eSecurityGroupParamVo param) {
//        try {
//            if (param == null
//                    || param.getName() == null
//                    || param.getDescribe() == null
//                    || param.getWeight() == null) {
//                return P6eResultModel.build(P6eResultConfig.ERROR_PARAM_EXCEPTION);
//            } else {
//                final P6eSecurityGroupResultDto p6eSecurityGroupResultDto =
//                        securityGroupService.create(CopyUtil.run(param, P6eSecurityGroupParamDto.class));
//                return P6eResultModel.build(P6eResultConfig.SUCCESS,
//                        CopyUtil.run(p6eSecurityGroupResultDto, P6eSecurityGroupResultVo.class));
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            LOGGER.error(e.getMessage());
//            return P6eResultModel.build(P6eResultConfig.ERROR_SERVICE_INSIDE, e.getMessage());
//        }
//    }
//
//    @P6eAuth
//    @PutMapping("/update/{id}")
//    @P6eSecurity(values = {
//            P6eSecurityConstant.ADMIN_AUTH_OWN,
//            P6eSecurityConstant.ADMIN_GROUP_UPDATE_OWN
//    }, condition = P6eSecurityType.Condition.AND)
//    public P6eResultModel update(@PathVariable Integer id, @RequestBody P6eSecurityGroupParamVo param) {
//        try {
//            if (param == null) {
//                return P6eResultModel.build(P6eResultConfig.ERROR_PARAM_EXCEPTION);
//            } else {
//                final P6eSecurityGroupResultDto p6eSecurityGroupResultDto =
//                        securityGroupService.update(CopyUtil.run(param, P6eSecurityGroupParamDto.class).setId(id));
//                if (p6eSecurityGroupResultDto == null) {
//                    return P6eResultModel.build(P6eResultConfig.ERROR_RESOURCES_NO_EXIST);
//                } else {
//                    return P6eResultModel.build(P6eResultConfig.SUCCESS,
//                            CopyUtil.run(p6eSecurityGroupResultDto, P6eSecurityGroupResultVo.class));
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            LOGGER.error(e.getMessage());
//            return P6eResultModel.build(P6eResultConfig.ERROR_SERVICE_INSIDE, e.getMessage());
//        }
//    }
//
//    @P6eAuth
//    @DeleteMapping("/delete/{id}")
//    @P6eSecurity(values = {
//            P6eSecurityConstant.ADMIN_AUTH_OWN,
//            P6eSecurityConstant.ADMIN_GROUP_DELETE_OWN
//    }, condition = P6eSecurityType.Condition.AND)
//    public P6eResultModel delete(@PathVariable Integer id) {
//        try {
//            final P6eSecurityGroupResultDto p6eSecurityGroupResultDto =
//                    securityGroupService.delete(new P6eSecurityGroupParamDto().setId(id));
//            if (p6eSecurityGroupResultDto == null) {
//                return P6eResultModel.build(P6eResultConfig.ERROR_RESOURCES_NO_EXIST);
//            } else if (ERROR_SERVICE_INSIDE.equals(p6eSecurityGroupResultDto.getError())) {
//                return P6eResultModel.build(P6eResultConfig.ERROR_SERVICE_INSIDE);
//            } else if (ERROR_RESOURCES_EXISTENCE_RELATION_DATA.equals(p6eSecurityGroupResultDto.getError())) {
//                return P6eResultModel.build(P6eResultConfig.ERROR_RESOURCES_EXISTENCE_RELATION_DATA);
//            } else if (ERROR_RESOURCES_NO_EXIST.equals(p6eSecurityGroupResultDto.getError())) {
//                return P6eResultModel.build(P6eResultConfig.ERROR_RESOURCES_NO_EXIST);
//            } else {
//                return P6eResultModel.build(P6eResultConfig.SUCCESS,
//                        CopyUtil.run(p6eSecurityGroupResultDto, P6eSecurityGroupResultVo.class));
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            LOGGER.error(e.getMessage());
//            return P6eResultModel.build(P6eResultConfig.ERROR_SERVICE_INSIDE, e.getMessage());
//        }
//    }
//}
