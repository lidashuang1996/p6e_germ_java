package com.p6e.germ.security.controller;

import com.p6e.germ.oauth2.controller.support.P6eBaseController;
import com.p6e.germ.oauth2.model.P6eResultConfig;
import com.p6e.germ.oauth2.model.P6eResultModel;
import com.p6e.germ.oauth2.utils.CopyUtil;
import com.p6e.germ.security.model.dto.*;
import com.p6e.germ.security.model.vo.*;
import com.p6e.germ.security.service.P6eSecurityJurisdictionService;
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

    @Resource
    private P6eSecurityJurisdictionService securityJurisdictionService;

    @GetMapping("/")
    public P6eResultModel def(P6eSecurityJurisdictionParamVo param) {
        return select(param);
    }

    @GetMapping("/list")
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

    @GetMapping("/{id}")
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

    @PostMapping("/create")
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

    @PutMapping("/update/{id}")
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

    @DeleteMapping("/delete/{id}")
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
