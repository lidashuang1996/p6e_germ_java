package com.p6e.germ.oauth2.controller.admin;

import com.p6e.germ.oauth2.auth.P6eAdminAuth;
import com.p6e.germ.oauth2.auth.P6eAdminAuthModel;
import com.p6e.germ.oauth2.config.P6eOauth2Config;
import com.p6e.germ.oauth2.controller.support.P6eBaseController;
import com.p6e.germ.oauth2.model.P6eResultConfig;
import com.p6e.germ.oauth2.model.P6eResultModel;
import com.p6e.germ.oauth2.model.dto.P6eAdminSignParamDto;
import com.p6e.germ.oauth2.model.dto.P6eAdminSignResultDto;
import com.p6e.germ.oauth2.model.vo.P6eAdminSignParamVo;
import com.p6e.germ.oauth2.model.vo.P6eAdminSignResultVo;
import com.p6e.germ.oauth2.service.P6eAdminSignService;
import com.p6e.germ.oauth2.utils.CopyUtil;
import org.apache.ibatis.annotations.Delete;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;

/**
 * 该类为管理员账号，登录、注册、退出、刷新 的接口实现
 * @author lidashuang
 * @version 1.0
 */
@RestController
@RequestMapping("/admin/sign")
public class P6eAdminSignController extends P6eBaseController {

    /**
     * 注入认证的配置文件
     */
    @Resource
    private P6eOauth2Config oauth2Config;

    /**
     * 注入管理员账号管理的服务对象
     */
    @Resource
    private P6eAdminSignService adminSignService;

    /**
     * 管理员登录
     * @param param 管理员登录的参数
     * @return 通用返回的对象
     */
    @PostMapping("/in")
    public P6eResultModel in(@RequestBody P6eAdminSignParamVo param) {
        try {
            if (param == null
                || param.getAccount() == null
                || param.getPassword() == null) {
                return P6eResultModel.build(P6eResultConfig.ERROR_PARAM_EXCEPTION);
            } else {
                final P6eAdminSignResultDto p6eAdminSignResultDto =
                        adminSignService.in(CopyUtil.run(param, P6eAdminSignParamDto.class));
                if (p6eAdminSignResultDto == null || p6eAdminSignResultDto.getError() != null) {
                    return P6eResultModel.build(P6eResultConfig.ERROR_ACCOUNT_OR_PASSWORD);
                } else {
                    return P6eResultModel.build(P6eResultConfig.SUCCESS,
                            CopyUtil.run(p6eAdminSignResultDto, P6eAdminSignResultVo.class));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            return P6eResultModel.build(P6eResultConfig.ERROR_SERVICE_INSIDE, e.getMessage());
        }
    }

    /**
     * 管理员注册
     * @param param 管理员注册的参数
     * @return 通用返回的对象
     */
    @P6eAdminAuth
    @PostMapping("/up")
    public P6eResultModel up(@RequestBody P6eAdminSignParamVo param, P6eAdminAuthModel p6eAdminAuthModel) {
        try {
            // 读取配置的参数
            final int registerStatus = oauth2Config.getAdmin().getRegisterStatus();
            // 匹配参数
            switch (registerStatus) {
                case -1:
                    LOGGER.info("registration has been prohibited. " +
                            "if you want to use registration, please modify the configuration file" +
                            "[ register-status = 0 / 1 ]");
                    return P6eResultModel.build(P6eResultConfig.ERROR_FUNCTION_DISABLE);
                case 0:
                    // 判断是否认证成功
                    if (p6eAdminAuthModel == null
                            || p6eAdminAuthModel.getId() == null) {
                        return P6eResultModel.build(P6eResultConfig.ERROR_AUTH);
                    }
                case 1:
                    if (param == null
                            || param.getAccount() == null
                            || param.getPassword() == null) {
                        return P6eResultModel.build(P6eResultConfig.ERROR_PARAM_EXCEPTION);
                    } else {
                        final P6eAdminSignResultDto p6eAdminSignResultDto =
                                adminSignService.up(CopyUtil.run(param, P6eAdminSignParamDto.class));
                        return P6eResultModel.build(P6eResultConfig.SUCCESS,
                                CopyUtil.run(p6eAdminSignResultDto, P6eAdminSignResultVo.class));
                    }
                default:
                    LOGGER.error("no corresponding state processor was found.");
                    return P6eResultModel.build(P6eResultConfig.ERROR_SERVICE_INSIDE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            return P6eResultModel.build(P6eResultConfig.ERROR_SERVICE_INSIDE, e.getMessage());
        }
    }

    /**
     * 管理员推出登录
     * @param token 管理员退出登录的参数
     * @return 通用返回的对象
     */
    @Delete("/out")
    public P6eResultModel out(String token) {
        try {
            if (token == null) {
                return P6eResultModel.build(P6eResultConfig.ERROR_PARAM_EXCEPTION);
            } else {
                final P6eAdminSignResultDto p6eAdminSignResultDto =
                        adminSignService.out(new P6eAdminSignParamDto().setToken(token));
                return result(p6eAdminSignResultDto);
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            return P6eResultModel.build(P6eResultConfig.ERROR_SERVICE_INSIDE, e.getMessage());
        }
    }

    /**
     * 管理员 token 刷新
     * @param refreshToken refreshToken
     * @return 通用返回的对象
     */
    @GetMapping("/refresh/{refreshToken}")
    public P6eResultModel refresh(@PathVariable String refreshToken) {
        try {
            final P6eAdminSignResultDto p6eAdminSignResultDto =
                    adminSignService.refresh(new P6eAdminSignParamDto().setRefreshToken(refreshToken));
            return result(p6eAdminSignResultDto);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            return P6eResultModel.build(P6eResultConfig.ERROR_SERVICE_INSIDE);
        }
    }

    /**
     * 结果公共的转换器
     * @param p6eAdminSignResultDto dto 结果对象
     * @return 通用结果返回
     */
    private P6eResultModel result(P6eAdminSignResultDto p6eAdminSignResultDto) {
        if (p6eAdminSignResultDto == null) {
            return P6eResultModel.build(P6eResultConfig.ERROR_RESOURCES_NO_EXIST);
        } else if (p6eAdminSignResultDto.getError() != null) {
            return P6eResultModel.build(P6eResultConfig.ERROR_SERVICE_INSIDE, p6eAdminSignResultDto.getError());
        } else {
            return P6eResultModel.build(P6eResultConfig.SUCCESS,
                    CopyUtil.run(p6eAdminSignResultDto, P6eAdminSignResultVo.class));
        }
    }
}
