package com.p6e.germ.oauth2.context.rpc;

import com.p6e.germ.oauth2.application.P6eApplication;
import com.p6e.germ.oauth2.context.support.P6eBaseController;
import com.p6e.germ.oauth2.context.support.model.P6eClientDataParam;
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
        return P6eModel.build().setData(P6eApplication.clientData.get(P6eCopyUtil.run(param, P6eClientDataDto.class)));
    }

    @RequestMapping("/create")
    public P6eModel create(P6eClientDataParam param) {
        return P6eModel.build().setData(P6eApplication.clientData.create(P6eCopyUtil.run(param, P6eClientDataDto.class)));
    }

    @RequestMapping("/delete")
    public P6eModel delete(P6eClientDataParam param) {
        return P6eModel.build().setData(P6eApplication.clientData.delete(P6eCopyUtil.run(param, P6eClientDataDto.class)));
    }

    @RequestMapping("/update")
    public P6eModel update(P6eClientDataParam param) {
        return P6eModel.build().setData(P6eApplication.clientData.update(P6eCopyUtil.run(param, P6eClientDataDto.class)));
    }

    @RequestMapping("/select")
    public P6eModel select(P6eClientDataParam param) {
        return P6eModel.build().setData(P6eApplication.clientData.select(P6eCopyUtil.run(param, P6eClientDataDto.class)));
    }
}
