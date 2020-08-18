package com.p6e.germ.oauth2.controller.admin;

import com.p6e.germ.oauth2.auth.P6eAdminAuth;
import com.p6e.germ.oauth2.controller.support.P6eBaseController;
import com.p6e.germ.oauth2.model.P6eResultConfig;
import com.p6e.germ.oauth2.model.P6eResultModel;
import com.p6e.germ.oauth2.model.dto.P6eAdminUserManageParamDto;
import com.p6e.germ.oauth2.model.dto.P6eAdminUserManageResultDto;
import com.p6e.germ.oauth2.model.dto.P6eListResultDto;
import com.p6e.germ.oauth2.model.vo.P6eAdminUserManageParamVo;
import com.p6e.germ.oauth2.model.vo.P6eAdminUserManageResultVo;
import com.p6e.germ.oauth2.model.vo.P6eListResultVo;
import com.p6e.germ.oauth2.mybatis.MyBatisTool;
import com.p6e.germ.oauth2.service.P6eAdminUserManageService;
import com.p6e.germ.oauth2.utils.CopyUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author lidashuang
 * @version 1.0
 */
@RestController
@RequestMapping("/admin/user/manage")
public class P6eAdminUserManageController extends P6eBaseController {

    @Resource
    private P6eAdminUserManageService p6eAdminUserManageService;

    @GetMapping
    @P6eAdminAuth(role = 1)
    public P6eResultModel list(P6eAdminUserManageParamVo param) {
        try {
            if (param == null) {
                param = new P6eAdminUserManageParamVo();
            }
            final P6eListResultDto<P6eAdminUserManageResultDto> p6eListResultDto
                    = p6eAdminUserManageService.list(CopyUtil.run(param, P6eAdminUserManageParamDto.class));
            // 查询结果封装返回
            final P6eListResultVo<P6eAdminUserManageResultVo> p6eListResultVo = new P6eListResultVo<>();
            p6eListResultVo.setPage(p6eListResultDto.getPage());
            p6eListResultVo.setSize(p6eListResultDto.getSize());
            p6eListResultVo.setTotal(p6eListResultDto.getTotal());
            p6eListResultVo.setList(CopyUtil.run(p6eListResultDto.getList(), P6eAdminUserManageResultVo.class));
            return P6eResultModel.build(P6eResultConfig.SUCCESS, p6eListResultVo);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return P6eResultModel.build(P6eResultConfig.ERROR_SERVICE_INSIDE);
        }
    }

    @PostMapping
    @P6eAdminAuth(role = 1)
    public P6eResultModel create(@RequestBody P6eAdminUserManageParamVo param) {
        try {
            if (param == null
                    || param.getAccount() == null
                    || param.getPassword() == null
                    || param.getName() == null
                    || param.getNickname() == null
                    || param.getBirthday() == null
                    || param.getRole() == null) {
                return P6eResultModel.build(P6eResultConfig.ERROR_PARAM_EXCEPTION);
            } else {
                if (MyBatisTool.isEmail(param.getAccount()) || MyBatisTool.isPhone(param.getAccount())) {
                    final P6eAdminUserManageResultDto p6eAdminUserManageResultDto
                            = p6eAdminUserManageService.create(CopyUtil.run(param, P6eAdminUserManageParamDto.class));
                    if (p6eAdminUserManageResultDto == null) {
                        return P6eResultModel.build(P6eResultConfig.ERROR_ACCOUNT_EXISTENCE);
                    } else {
                        return P6eResultModel.build(P6eResultConfig.SUCCESS,
                                CopyUtil.run(p6eAdminUserManageResultDto, P6eAdminUserManageResultVo.class));
                    }
                } else {
                    return P6eResultModel.build(P6eResultConfig.ERROR_PARAM_EXCEPTION);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return P6eResultModel.build(P6eResultConfig.ERROR_SERVICE_INSIDE);
        }
    }

    @P6eAdminAuth(role = 1)
    @GetMapping("/{id}")
    public P6eResultModel get(@PathVariable Integer id) {
        try {
            final P6eAdminUserManageResultDto p6eAdminUserManageResultDto
                    = p6eAdminUserManageService.get(new P6eAdminUserManageParamDto(id));
            if (p6eAdminUserManageResultDto == null) {
                return P6eResultModel.build(P6eResultConfig.ERROR_RESOURCES_NO_EXIST);
            } else {
                return P6eResultModel.build(P6eResultConfig.SUCCESS,
                        CopyUtil.run(p6eAdminUserManageResultDto, P6eAdminUserManageResultVo.class));
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return P6eResultModel.build(P6eResultConfig.ERROR_SERVICE_INSIDE);
        }
    }

    @P6eAdminAuth(role = 1)
    @DeleteMapping("/{id}")
    public P6eResultModel delete(@PathVariable Integer id) {
        try {
            if (id == 1) {
                return P6eResultModel.build(P6eResultConfig.ERROR_BAN_DEFAULT_DATA_OPERATE);
            } else {
                final P6eAdminUserManageResultDto p6eAdminUserManageResultDto
                        = p6eAdminUserManageService.delete(new P6eAdminUserManageParamDto(id));
                if (p6eAdminUserManageResultDto == null) {
                    return P6eResultModel.build(P6eResultConfig.ERROR_RESOURCES_NO_EXIST);
                } else {
                    return P6eResultModel.build(P6eResultConfig.SUCCESS,
                            CopyUtil.run(p6eAdminUserManageResultDto, P6eAdminUserManageResultVo.class));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return P6eResultModel.build(P6eResultConfig.ERROR_SERVICE_INSIDE);
        }
    }

    @P6eAdminAuth(role = 1)
    @PutMapping("/{id}")
    public P6eResultModel update(@PathVariable Integer id, @RequestBody P6eAdminUserManageParamVo param) {
        try {
            if (param == null) {
                param = new P6eAdminUserManageParamVo();
            }
            // 写入修改的 ID
            param.setId(id);
            final P6eAdminUserManageResultDto p6eAdminUserManageResultDto
                    = p6eAdminUserManageService.update(CopyUtil.run(param, P6eAdminUserManageParamDto.class));
            if (p6eAdminUserManageResultDto == null) {
                return P6eResultModel.build(P6eResultConfig.ERROR_RESOURCES_NO_EXIST);
            } else {
                return P6eResultModel.build(P6eResultConfig.SUCCESS,
                        CopyUtil.run(p6eAdminUserManageResultDto, P6eAdminUserManageResultVo.class));
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return P6eResultModel.build(P6eResultConfig.ERROR_SERVICE_INSIDE);
        }
    }
}
