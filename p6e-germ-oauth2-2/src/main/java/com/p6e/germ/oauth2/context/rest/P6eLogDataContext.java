package com.p6e.germ.oauth2.context.rest;

import com.p6e.germ.common.utils.P6eCopyUtil;
import com.p6e.germ.oauth2.application.P6eApplication;
import com.p6e.germ.oauth2.context.support.P6eBaseController;
import com.p6e.germ.oauth2.context.support.model.*;
import com.p6e.germ.oauth2.model.P6eModel;
import com.p6e.germ.oauth2.model.dto.P6eLogDataDto;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lidashuang
 * @version 1.0
 */
@RestController
@RequestMapping("/context/log")
public class P6eLogDataContext extends P6eBaseController {

    @RequestMapping("/get")
    public P6eModel get(P6eLogDataParam param) {
        final P6eLogDataDto p6eLogDataDto = P6eApplication.logData.get(P6eCopyUtil.run(param, P6eLogDataDto.class));
        if (p6eLogDataDto.getError() == null) {
            return P6eModel.build().setData(P6eCopyUtil.run(p6eLogDataDto, P6eLogDataResult.class));
        } else {
            return P6eModel.build(p6eLogDataDto.getError());
        }
    }

    @RequestMapping("/select")
    public P6eModel select(P6eLogDataParam param) {
        final P6eLogDataDto p6eLogDataDto = P6eApplication.logData.select(P6eCopyUtil.run(param, P6eLogDataDto.class));
        if (p6eLogDataDto.getError() == null) {
            final P6ePagingResult<P6eLogDataResult> p6ePagingResult = new P6ePagingResult<>();
            p6ePagingResult.setPage(p6eLogDataDto.getPage());
            p6ePagingResult.setSize(p6eLogDataDto.getSize());
            p6ePagingResult.setTotal(p6eLogDataDto.getTotal());
            p6ePagingResult.setList(P6eCopyUtil.runList(p6eLogDataDto.getList(), P6eLogDataResult.class));
            return P6eModel.build().setData(p6ePagingResult);
        } else {
            return P6eModel.build(p6eLogDataDto.getError());
        }
    }
}
