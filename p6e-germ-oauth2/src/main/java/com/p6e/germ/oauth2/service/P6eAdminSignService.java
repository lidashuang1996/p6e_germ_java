package com.p6e.germ.oauth2.service;

import com.p6e.germ.oauth2.model.dto.P6eAdminSignParamDto;
import com.p6e.germ.oauth2.model.dto.P6eAdminSignResultDto;

/**
 * @author lidashuang
 * @version 1.0
 */
public interface P6eAdminSignService {

    public P6eAdminSignResultDto in(P6eAdminSignParamDto param);

    public P6eAdminSignResultDto up(P6eAdminSignParamDto param);

    public P6eAdminSignResultDto out(P6eAdminSignParamDto param);

    public P6eAdminSignResultDto refresh(P6eAdminSignParamDto param);

}
