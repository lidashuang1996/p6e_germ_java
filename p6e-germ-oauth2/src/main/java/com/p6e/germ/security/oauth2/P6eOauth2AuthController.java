package com.p6e.germ.security.oauth2;

import com.p6e.germ.oauth2.controller.support.P6eBaseController;
import com.p6e.germ.oauth2.model.P6eResultConfig;
import com.p6e.germ.oauth2.model.P6eResultModel;
import com.p6e.germ.oauth2.utils.CopyUtil;
import com.p6e.germ.security.config.P6eAuthModel;
import com.p6e.germ.security.model.dto.P6eOauth2AuthParamDto;
import com.p6e.germ.security.model.dto.P6eOauth2AuthResultDto;
import com.p6e.germ.security.model.vo.P6eOauth2AuthParamVo;
import com.p6e.germ.security.model.vo.P6eOauth2AuthResultVo;
import com.p6e.germ.security.service.P6eOauth2AuthService;
import com.p6e.germ.starter.oauth2.P6eAuth;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Oauth2 操作 controller 层
 * 1. 登录
 * 2. 获取信息
 * 3. 刷新操作
 * 4. 退出登录
 * @author lidashuang
 * @version 1.0
 */
@RestController
@RequestMapping("/oauth2")
public class P6eOauth2AuthController extends P6eBaseController {

    private static final String ERROR_RESOURCES_NO_EXIST = "ERROR_RESOURCES_NO_EXIST";
    private static final String ERROR_ACCOUNT_OR_PASSWORD = "ERROR_ACCOUNT_OR_PASSWORD";

    @Resource
    private P6eOauth2AuthService oauth2AuthService;

    /**
     * 如果是通过第三方登录的授权过来
     * 那么可以把这里的这个接口注释掉
     * 添加一个通过前段传如的 token 去通过第三方平台获取用户的数据信息
     * @param param 请求的参数
     * @return 请求的结果
     */
    @PostMapping("/auth")
    public P6eResultModel auth(@RequestBody P6eOauth2AuthParamVo param) {
        try {
            if (param == null || param.getAccount() == null || param.getPassword() == null) {
                return P6eResultModel.build(P6eResultConfig.ERROR_PARAM_EXCEPTION);
            } else {
                final P6eOauth2AuthResultDto resultDto =
                        oauth2AuthService.auth(CopyUtil.run(param, P6eOauth2AuthParamDto.class));
                return result(resultDto);
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            return P6eResultModel.build(P6eResultConfig.ERROR_SERVICE_INSIDE, e.getMessage());
        }
    }

    @P6eAuth
    @GetMapping("/info")
    public P6eResultModel info(P6eAuthModel authModel) {
        try {
            final P6eOauth2AuthResultDto resultDto =
                    oauth2AuthService.info(new P6eOauth2AuthParamDto().setToken(authModel.getToken()));
            return result(resultDto);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            return P6eResultModel.build(P6eResultConfig.ERROR_SERVICE_INSIDE, e.getMessage());
        }
    }

    @P6eAuth
    @PutMapping("/refresh")
    public P6eResultModel refresh(P6eAuthModel authModel) {
        try {
            final P6eOauth2AuthResultDto resultDto =
                    oauth2AuthService.refresh(new P6eOauth2AuthParamDto().setToken(authModel.getToken()));
            return result(resultDto);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            return P6eResultModel.build(P6eResultConfig.ERROR_SERVICE_INSIDE, e.getMessage());
        }
    }

    @P6eAuth
    @DeleteMapping("/logout")
    public P6eResultModel logout(P6eAuthModel authModel) {
        try {
            final P6eOauth2AuthResultDto resultDto =
                    oauth2AuthService.logout(new P6eOauth2AuthParamDto().setToken(authModel.getToken()));
            return result(resultDto);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            return P6eResultModel.build(P6eResultConfig.ERROR_SERVICE_INSIDE, e.getMessage());
        }
    }

    /**
     * 对返回的结果进行统一的处理
     * @param resultDto 返回的对象
     * @return 返回的包装对象
     */
    public P6eResultModel result(final P6eOauth2AuthResultDto resultDto) {
        if (resultDto == null) {
            return P6eResultModel.build(P6eResultConfig.ERROR_SERVICE_INSIDE);
        }
        if (resultDto.getError() != null) {
            switch (resultDto.getError()) {
                case ERROR_RESOURCES_NO_EXIST:
                    return P6eResultModel.build(P6eResultConfig.ERROR_RESOURCES_NO_EXIST);
                case ERROR_ACCOUNT_OR_PASSWORD:
                    return P6eResultModel.build(P6eResultConfig.ERROR_ACCOUNT_OR_PASSWORD);
                default:
                    return P6eResultModel.build(P6eResultConfig.ERROR_SERVICE_INSIDE);
            }
        } else {
            return P6eResultModel.build(P6eResultConfig.SUCCESS,
                    CopyUtil.run(resultDto, P6eOauth2AuthResultVo.class));
        }
    }

}
