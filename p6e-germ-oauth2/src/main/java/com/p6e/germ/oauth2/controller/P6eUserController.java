package com.p6e.germ.oauth2.controller;

import com.p6e.germ.oauth2.auth.P6eUserAuth;
import com.p6e.germ.oauth2.auth.P6eUserAuthModel;
import com.p6e.germ.oauth2.controller.support.P6eBaseController;
import com.p6e.germ.oauth2.model.P6eResultConfig;
import com.p6e.germ.oauth2.model.P6eResultModel;
import com.p6e.germ.oauth2.model.dto.P6eUserParamDto;
import com.p6e.germ.oauth2.model.dto.P6eUserResultDto;
import com.p6e.germ.oauth2.model.vo.P6eUserResultVo;
import com.p6e.germ.oauth2.service.P6eUserService;
import com.p6e.germ.oauth2.utils.CopyUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 用户操作的接口
 * 由于本系统是作为的单点登录系统的支持
 * 获取用户信息，基本上要被子系统重写
 * 故考虑 oauth2 系统保存用户信息是否画蛇添足
 * @author lidashuang
 * @version 1.0
 */
@RestController
@RequestMapping("/user")
public class P6eUserController extends P6eBaseController {

    @Resource
    private P6eUserService p6eUserService;

    @P6eUserAuth
    @GetMapping
    public P6eResultModel get(P6eUserAuthModel p6EUserAuthModel) {
        try {
            // 查询用户信息
            // 查询和平台的关系
            final P6eUserResultDto p6eUserResultDto
                    = p6eUserService.select(new P6eUserParamDto(p6EUserAuthModel.getId()));
            if (p6eUserResultDto == null || p6eUserResultDto.getError() != null) {
                return P6eResultModel.build(P6eResultConfig.ERROR_RESOURCES_NO_EXIST);
            } else {
                return P6eResultModel.build(P6eResultConfig.SUCCESS,
                        CopyUtil.run(p6eUserResultDto, P6eUserResultVo.class));
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return P6eResultModel.build(P6eResultConfig.ERROR_SERVICE_INSIDE);
        }
    }

}
