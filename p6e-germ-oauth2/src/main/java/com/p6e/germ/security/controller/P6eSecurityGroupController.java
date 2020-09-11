package com.p6e.germ.security.controller;

import com.p6e.germ.oauth2.controller.support.P6eBaseController;
import com.p6e.germ.oauth2.model.P6eResultConfig;
import com.p6e.germ.oauth2.model.P6eResultModel;
import com.p6e.germ.oauth2.utils.CopyUtil;
import com.p6e.germ.security.model.dto.P6eListResultDto;
import com.p6e.germ.security.model.dto.P6eSecurityGroupParamDto;
import com.p6e.germ.security.model.dto.P6eSecurityGroupResultDto;
import com.p6e.germ.security.model.vo.P6eListResultVo;
import com.p6e.germ.security.model.vo.P6eSecurityGroupParamVo;
import com.p6e.germ.security.model.vo.P6eSecurityGroupResultVo;
import com.p6e.germ.security.service.P6eSecurityGroupService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author lidashuang
 * @version 1.0
 */
@RestController
@RequestMapping("/security/group")
public class P6eSecurityGroupController extends P6eBaseController {

    /** 资源不存在 */
    public static final String ERROR_RESOURCES_NO_EXIST = "400-ERROR_RESOURCES_NO_EXIST";
    /** 资源存在关联数据 */
    public static final String ERROR_RESOURCES_EXISTENCE_RELATION_DATA = "ERROR_RESOURCES_EXISTENCE_RELATION_DATA";
    /** 服务器内部出现异常 */
    public static final String ERROR_SERVICE_INSIDE = "ERROR_SERVICE_INSIDE";

    @Resource
    private P6eSecurityGroupService securityGroupService;

    @GetMapping("/")
    public P6eResultModel def(P6eSecurityGroupParamVo param) {
        return select(param);
    }

    @GetMapping("/list")
    public P6eResultModel select(P6eSecurityGroupParamVo param) {
        try {
            final P6eListResultDto<P6eSecurityGroupResultDto> p6eListResultDto =
                    securityGroupService.select(CopyUtil.run(param, P6eSecurityGroupParamDto.class));
            final P6eListResultVo<P6eSecurityGroupResultVo> p6eListResultVo = new P6eListResultVo<>();
            p6eListResultVo.setPage(p6eListResultDto.getPage());
            p6eListResultVo.setSize(p6eListResultDto.getSize());
            p6eListResultVo.setTotal(p6eListResultDto.getTotal());
            p6eListResultVo.setList(CopyUtil.run(p6eListResultDto.getList(), P6eSecurityGroupResultVo.class));
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
            final P6eSecurityGroupResultDto p6eSecurityGroupResultDto =
                    securityGroupService.selectOneData(new P6eSecurityGroupParamDto().setId(id));
            return P6eResultModel.build(P6eResultConfig.SUCCESS,
                    CopyUtil.run(p6eSecurityGroupResultDto, P6eSecurityGroupResultVo.class));
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            return P6eResultModel.build(P6eResultConfig.ERROR_SERVICE_INSIDE, e.getMessage());
        }
    }

    @PostMapping("/create")
    public P6eResultModel create(@RequestBody P6eSecurityGroupParamVo param) {
        try {
            if (param == null
                    || param.getName() == null
                    || param.getDescribe() == null
                    || param.getWeight() == null) {
                return P6eResultModel.build(P6eResultConfig.ERROR_PARAM_EXCEPTION);
            } else {
                final P6eSecurityGroupResultDto p6eSecurityGroupResultDto =
                        securityGroupService.create(CopyUtil.run(param, P6eSecurityGroupParamDto.class));
                return P6eResultModel.build(P6eResultConfig.SUCCESS,
                        CopyUtil.run(p6eSecurityGroupResultDto, P6eSecurityGroupResultVo.class));
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            return P6eResultModel.build(P6eResultConfig.ERROR_SERVICE_INSIDE, e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public P6eResultModel update(@PathVariable Integer id, @RequestBody P6eSecurityGroupParamVo param) {
        try {
            if (param == null) {
                return P6eResultModel.build(P6eResultConfig.ERROR_PARAM_EXCEPTION);
            } else {
                final P6eSecurityGroupResultDto p6eSecurityGroupResultDto =
                        securityGroupService.update(CopyUtil.run(param, P6eSecurityGroupParamDto.class).setId(id));
                if (p6eSecurityGroupResultDto == null) {
                    return P6eResultModel.build(P6eResultConfig.ERROR_RESOURCES_NO_EXIST);
                } else {
                    return P6eResultModel.build(P6eResultConfig.SUCCESS,
                            CopyUtil.run(p6eSecurityGroupResultDto, P6eSecurityGroupResultVo.class));
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
            final P6eSecurityGroupResultDto p6eSecurityGroupResultDto =
                    securityGroupService.delete(new P6eSecurityGroupParamDto().setId(id));
            if (p6eSecurityGroupResultDto == null) {
                return P6eResultModel.build(P6eResultConfig.ERROR_RESOURCES_NO_EXIST);
            } else if (ERROR_SERVICE_INSIDE.equals(p6eSecurityGroupResultDto.getError())) {
                return P6eResultModel.build(P6eResultConfig.ERROR_SERVICE_INSIDE);
            } else if (ERROR_RESOURCES_EXISTENCE_RELATION_DATA.equals(p6eSecurityGroupResultDto.getError())) {
                return P6eResultModel.build(P6eResultConfig.ERROR_RESOURCES_EXISTENCE_RELATION_DATA);
            } else if (ERROR_RESOURCES_NO_EXIST.equals(p6eSecurityGroupResultDto.getError())) {
                return P6eResultModel.build(P6eResultConfig.ERROR_RESOURCES_NO_EXIST);
            } else {
                return P6eResultModel.build(P6eResultConfig.SUCCESS,
                        CopyUtil.run(p6eSecurityGroupResultDto, P6eSecurityGroupResultVo.class));
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            return P6eResultModel.build(P6eResultConfig.ERROR_SERVICE_INSIDE, e.getMessage());
        }
    }
}
