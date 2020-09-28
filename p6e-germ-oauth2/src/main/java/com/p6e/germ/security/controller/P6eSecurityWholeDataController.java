package com.p6e.germ.security.controller;

import com.p6e.germ.oauth2.controller.support.P6eBaseController;
import com.p6e.germ.oauth2.model.P6eResultConfig;
import com.p6e.germ.oauth2.model.P6eResultModel;
import com.p6e.germ.oauth2.utils.CopyUtil;
import com.p6e.germ.security.config.P6eSecurityConstant;
import com.p6e.germ.security.model.dto.P6eSecurityWholeDataGroupResultDto;
import com.p6e.germ.security.model.vo.P6eSecurityWholeDataGroupResultVo;
import com.p6e.germ.security.service.P6eSecurityWholeDataService;
import com.p6e.germ.starter.oauth2.P6eAuth;
import com.p6e.germ.starter.security.P6eSecurity;
import com.p6e.germ.starter.security.P6eSecurityType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 查询全部数据的接口
 * @author lidashuang
 * @version 1.0
 */
@RestController
@RequestMapping("/security/whole")
public class P6eSecurityWholeDataController extends P6eBaseController {

    /**
     * 注入数据对象
     */
    @Resource
    private P6eSecurityWholeDataService securityWholeDataService;

    @P6eAuth
    @GetMapping("/group/{id}")
    @P6eSecurity(values = {
            P6eSecurityConstant.ADMIN_AUTH_OWN,
            P6eSecurityConstant.ADMIN_JURISDICTION_SELECT_OWN,
            P6eSecurityConstant.ADMIN_GROUP_RELATION_JURISDICTION_OWN
    }, condition = P6eSecurityType.Condition.AND)
    public P6eResultModel group(@PathVariable Integer id) {
        try {
            final List<P6eSecurityWholeDataGroupResultDto> resultDtoList = securityWholeDataService.group(id);
            return P6eResultModel.build(P6eResultConfig.SUCCESS,
                    CopyUtil.run(resultDtoList, P6eSecurityWholeDataGroupResultVo.class));
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            return P6eResultModel.build(P6eResultConfig.ERROR_SERVICE_INSIDE, e.getMessage());
        }
    }

}
