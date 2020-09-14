package com.p6e.germ.security.controller;

import com.p6e.germ.oauth2.controller.support.P6eBaseController;
import com.p6e.germ.oauth2.model.P6eResultConfig;
import com.p6e.germ.oauth2.model.P6eResultModel;
import com.p6e.germ.oauth2.utils.CopyUtil;
import com.p6e.germ.security.model.dto.P6eListResultDto;
import com.p6e.germ.security.model.dto.P6eSecurityGroupRelationUserParamDto;
import com.p6e.germ.security.model.dto.P6eSecurityGroupRelationUserResultDto;
import com.p6e.germ.security.model.vo.P6eListResultVo;
import com.p6e.germ.security.model.vo.P6eSecurityGroupRelationUserParamVo;
import com.p6e.germ.security.model.vo.P6eSecurityGroupRelationUserResultVo;
import com.p6e.germ.security.service.P6eSecurityGroupRelationUserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author lidashuang
 * @version 1.0
 */
@RestController
@RequestMapping("/security/group/user")
public class P6eSecurityGroupRelationUserController extends P6eBaseController {

    @Resource
    private P6eSecurityGroupRelationUserService securityGroupRelationUserService;

    @GetMapping("/")
    public P6eResultModel def(P6eSecurityGroupRelationUserParamVo param) {
        return list(param);
    }

    @GetMapping("/list")
    public P6eResultModel list(P6eSecurityGroupRelationUserParamVo param) {
        try {
            final P6eListResultDto<P6eSecurityGroupRelationUserResultDto> p6eListResultDto =
                    securityGroupRelationUserService.select(CopyUtil.run(param, P6eSecurityGroupRelationUserParamDto.class));
            final P6eListResultVo<P6eSecurityGroupRelationUserResultVo> p6eListResultVo = new P6eListResultVo<>();
            p6eListResultVo.setPage(p6eListResultDto.getPage());
            p6eListResultVo.setSize(p6eListResultDto.getSize());
            p6eListResultVo.setTotal(p6eListResultDto.getTotal());
            p6eListResultVo.setList(CopyUtil.run(p6eListResultDto.getList(), P6eSecurityGroupRelationUserResultVo.class));
            return P6eResultModel.build(P6eResultConfig.SUCCESS, p6eListResultVo);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            return P6eResultModel.build(P6eResultConfig.ERROR_SERVICE_INSIDE, e.getMessage());
        }
    }

    @PostMapping("/create")
    public P6eResultModel create(@RequestBody P6eSecurityGroupRelationUserParamVo param) {
        try {
            if (param == null
                    || param.getGid() == null
                    || param.getUid() == null) {
                return P6eResultModel.build(P6eResultConfig.ERROR_PARAM_EXCEPTION);
            } else {
                final P6eSecurityGroupRelationUserResultDto p6eSecurityGroupRelationUserResultDto =
                        securityGroupRelationUserService.create(
                                CopyUtil.run(param, P6eSecurityGroupRelationUserParamDto.class));
                if (p6eSecurityGroupRelationUserResultDto == null) {
                    return P6eResultModel.build(P6eResultConfig.ERROR_RESOURCES_NO_EXIST);
                } else {
                    return P6eResultModel.build(P6eResultConfig.SUCCESS, CopyUtil.run(
                            p6eSecurityGroupRelationUserResultDto, P6eSecurityGroupRelationUserResultVo.class));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            return P6eResultModel.build(P6eResultConfig.ERROR_SERVICE_INSIDE, e.getMessage());
        }
    }

    @DeleteMapping("/delete")
    public P6eResultModel delete(P6eSecurityGroupRelationUserParamVo param) {
        try {
            final List<P6eSecurityGroupRelationUserResultDto> p6eSecurityGroupRelationUserResultDtoList =
                    securityGroupRelationUserService.delete(CopyUtil.run(param, P6eSecurityGroupRelationUserParamDto.class));
            if (p6eSecurityGroupRelationUserResultDtoList == null) {
                return P6eResultModel.build(P6eResultConfig.ERROR_RESOURCES_OPERATION);
            } else {
                return P6eResultModel.build(P6eResultConfig.SUCCESS, CopyUtil.run(
                        p6eSecurityGroupRelationUserResultDtoList, P6eSecurityGroupRelationUserResultVo.class));
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            return P6eResultModel.build(P6eResultConfig.ERROR_SERVICE_INSIDE, e.getMessage());
        }
    }

}
