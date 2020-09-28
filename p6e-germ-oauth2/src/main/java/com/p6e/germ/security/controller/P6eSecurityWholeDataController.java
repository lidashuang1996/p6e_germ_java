package com.p6e.germ.security.controller;

import com.p6e.germ.oauth2.controller.support.P6eBaseController;
import com.p6e.germ.oauth2.model.P6eResultConfig;
import com.p6e.germ.oauth2.model.P6eResultModel;
import com.p6e.germ.oauth2.utils.CopyUtil;
import com.p6e.germ.security.model.dto.P6eSecurityWholeDataGroupResultDto;
import com.p6e.germ.security.model.vo.P6eSecurityWholeDataGroupResultVo;
import com.p6e.germ.security.service.P6eSecurityWholeDataService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author lidashuang
 * @version 1.0
 */
@RestController
@RequestMapping("/security/whole")
public class P6eSecurityWholeDataController extends P6eBaseController {

    @Resource
    private P6eSecurityWholeDataService securityWholeDataService;

    @GetMapping("/group/{id}")
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
