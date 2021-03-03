package com.p6e.germ.oauth2.context.rest;

import com.p6e.germ.common.utils.P6eCopyUtil;
import com.p6e.germ.oauth2.application.P6eApplication;
import com.p6e.germ.oauth2.context.support.model.P6eLogDataResult;
import com.p6e.germ.oauth2.context.support.model.P6eUserDataParam;
import com.p6e.germ.oauth2.model.P6eModel;
import com.p6e.germ.oauth2.model.dto.P6eUserDataDto;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lidashuang
 * @version 1.0
 */
@RestController
@RequestMapping("/context/user")
public class P6eUserDataContext {

    @RequestMapping("/get")
    public P6eModel get(P6eUserDataParam param) {
        final P6eUserDataDto p6eLogDataDto = P6eApplication.userData.get(P6eCopyUtil.run(param, P6eUserDataDto.class));
        if (p6eLogDataDto.getError() == null) {
            return P6eModel.build().setData(P6eCopyUtil.run(p6eLogDataDto, P6eLogDataResult.class));
        } else {
            return P6eModel.build(p6eLogDataDto.getError());
        }
    }

    @RequestMapping("/update")
    public P6eModel update(P6eUserDataParam param) {
        final P6eUserDataDto p6eLogDataDto = P6eApplication.userData.update(P6eCopyUtil.run(param, P6eUserDataDto.class));
        if (p6eLogDataDto.getError() == null) {
            return P6eModel.build().setData(P6eCopyUtil.run(p6eLogDataDto, P6eLogDataResult.class));
        } else {
            return P6eModel.build(p6eLogDataDto.getError());
        }
    }

    @RequestMapping("/delete")
    public P6eModel delete(P6eUserDataParam param) {
        final P6eUserDataDto p6eLogDataDto = P6eApplication.userData.delete(P6eCopyUtil.run(param, P6eUserDataDto.class));
        if (p6eLogDataDto.getError() == null) {
            return P6eModel.build().setData(P6eCopyUtil.run(p6eLogDataDto, P6eLogDataResult.class));
        } else {
            return P6eModel.build(p6eLogDataDto.getError());
        }
    }

    @RequestMapping("/select")
    public P6eModel select(P6eUserDataParam param) {
        final P6eUserDataDto p6eLogDataDto = P6eApplication.userData.select(P6eCopyUtil.run(param, P6eUserDataDto.class));
        if (p6eLogDataDto.getError() == null) {
            return P6eModel.build().setData(P6eCopyUtil.run(p6eLogDataDto, P6eLogDataResult.class));
        } else {
            return P6eModel.build(p6eLogDataDto.getError());
        }
    }

}
