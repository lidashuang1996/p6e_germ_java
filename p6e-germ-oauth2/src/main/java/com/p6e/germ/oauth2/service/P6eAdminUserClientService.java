package com.p6e.germ.oauth2.service;

import com.p6e.germ.oauth2.model.dto.*;

/**
 * @author lidashuang
 * @version 1.0
 */
public interface P6eAdminUserClientService {

    public P6eAdminUserClientResultDto get(P6eAdminUserClientParamDto param);
    public P6eListResultDto<P6eAdminUserClientResultDto> list(P6eAdminUserClientParamDto param);
    public P6eAdminUserClientResultDto create(P6eAdminUserClientParamDto param);
    public P6eAdminUserClientResultDto delete(P6eAdminUserClientParamDto param);
    public P6eAdminUserClientResultDto update(P6eAdminUserClientParamDto param);

}
