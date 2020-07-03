package com.p6e.germ.oauth2.service;

import com.p6e.germ.oauth2.model.dto.P6eAdminUserManageParamDto;
import com.p6e.germ.oauth2.model.dto.P6eAdminUserManageResultDto;
import com.p6e.germ.oauth2.model.dto.P6eListResultDto;

/**
 * @author lidashuang
 * @version 1.0
 */
public interface P6eAdminUserManageService {

    public P6eAdminUserManageResultDto get(P6eAdminUserManageParamDto param);
    public P6eListResultDto<P6eAdminUserManageResultDto> list(P6eAdminUserManageParamDto param);
    public P6eAdminUserManageResultDto create(P6eAdminUserManageParamDto param);
    public P6eAdminUserManageResultDto delete(P6eAdminUserManageParamDto param);
    public P6eAdminUserManageResultDto update(P6eAdminUserManageParamDto param);

}
