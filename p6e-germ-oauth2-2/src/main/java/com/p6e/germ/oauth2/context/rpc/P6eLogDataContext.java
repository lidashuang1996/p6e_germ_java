package com.p6e.germ.oauth2.context.rpc;

import com.p6e.germ.oauth2.application.P6eApplication;
import com.p6e.germ.oauth2.context.support.P6eBaseController;
import com.p6e.germ.oauth2.context.support.model.P6eClientDataParam;
import com.p6e.germ.oauth2.context.support.model.P6eLogDataParam;
import com.p6e.germ.oauth2.infrastructure.utils.P6eCopyUtil;
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
        return P6eModel.build().setData(P6eApplication.logData.get(P6eCopyUtil.run(param, P6eLogDataDto.class)));
    }

    @RequestMapping("/select")
    public P6eModel select(P6eLogDataParam param) {
        return P6eModel.build().setData(P6eApplication.logData.select(P6eCopyUtil.run(param, P6eLogDataDto.class)));
    }
}
