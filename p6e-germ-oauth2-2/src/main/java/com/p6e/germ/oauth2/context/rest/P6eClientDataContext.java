package com.p6e.germ.oauth2.context.rest;

import com.p6e.germ.oauth2.application.P6eApplication;
import com.p6e.germ.oauth2.context.support.P6eBaseController;
import com.p6e.germ.oauth2.context.support.model.P6eClientDataParam;
import com.p6e.germ.oauth2.context.support.model.P6eClientDataResult;
import com.p6e.germ.oauth2.context.support.model.P6ePagingResult;
import com.p6e.germ.oauth2.infrastructure.utils.P6eCopyUtil;
import com.p6e.germ.oauth2.model.P6eModel;
import com.p6e.germ.oauth2.model.dto.P6eClientDataDto;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lidashuang
 * @version 1.0
 */
@RestController
@RequestMapping("/context/client")
public class P6eClientDataContext extends P6eBaseController {

    @RequestMapping("/get")
    public P6eModel get(P6eClientDataParam param) {
        final P6eClientDataDto p6eClientDataDto =
                P6eApplication.clientData.get(P6eCopyUtil.run(param, P6eClientDataDto.class));
        if (p6eClientDataDto.getError() == null) {
            return P6eModel.build().setData(P6eCopyUtil.run(p6eClientDataDto, P6eClientDataResult.class));
        }  else {
            return P6eModel.build(p6eClientDataDto.getError());
        }
    }

    @RequestMapping("/create")
    public P6eModel create(P6eClientDataParam param) {
        final P6eClientDataDto p6eClientDataDto =
                P6eApplication.clientData.create(P6eCopyUtil.run(param, P6eClientDataDto.class));
        if (p6eClientDataDto.getError() == null) {
            return P6eModel.build().setData(P6eCopyUtil.run(p6eClientDataDto, P6eClientDataResult.class));
        }  else {
            return P6eModel.build(p6eClientDataDto.getError());
        }
    }

    @RequestMapping("/delete")
    public P6eModel delete(P6eClientDataParam param) {
        final P6eClientDataDto p6eClientDataDto =
                P6eApplication.clientData.delete(P6eCopyUtil.run(param, P6eClientDataDto.class));
        if (p6eClientDataDto.getError() == null) {
            return P6eModel.build().setData(P6eCopyUtil.run(p6eClientDataDto, P6eClientDataResult.class));
        }  else {
            return P6eModel.build(p6eClientDataDto.getError());
        }
    }

    @RequestMapping("/update")
    public P6eModel update(P6eClientDataParam param) {
        final P6eClientDataDto p6eClientDataDto =
                P6eApplication.clientData.update(P6eCopyUtil.run(param, P6eClientDataDto.class));
        if (p6eClientDataDto.getError() == null) {
            return P6eModel.build().setData(P6eCopyUtil.run(p6eClientDataDto, P6eClientDataResult.class));
        }  else {
            return P6eModel.build(p6eClientDataDto.getError());
        }
    }

    @RequestMapping("/select")
    public P6eModel select(P6eClientDataParam param) {
        final P6eClientDataDto p6eClientDataDto =
                P6eApplication.clientData.select(P6eCopyUtil.run(param, P6eClientDataDto.class));
        if (p6eClientDataDto.getError() == null) {
            final P6ePagingResult<P6eClientDataResult> p6ePagingResult = new P6ePagingResult<>();
            p6ePagingResult.setPage(p6eClientDataDto.getPage());
            p6ePagingResult.setSize(p6eClientDataDto.getSize());
            p6ePagingResult.setTotal(p6eClientDataDto.getTotal());
            p6ePagingResult.setList(P6eCopyUtil.run(p6eClientDataDto.getList(), P6eClientDataResult.class));
            return P6eModel.build().setData(p6ePagingResult);
        }  else {
            return P6eModel.build(p6eClientDataDto.getError());
        }
    }
}
