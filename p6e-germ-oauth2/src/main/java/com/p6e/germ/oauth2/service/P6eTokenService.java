package com.p6e.germ.oauth2.service;

import com.p6e.germ.oauth2.model.dto.P6eTokenClientParamDto;
import com.p6e.germ.oauth2.model.dto.P6eTokenClientResultDto;
import com.p6e.germ.oauth2.model.dto.P6eTokenParamDto;
import com.p6e.germ.oauth2.model.dto.P6eTokenResultDto;

/**
 * @author lidashuang
 * @version 1.0
 */
public interface P6eTokenService {

    public P6eTokenResultDto set(P6eTokenParamDto param);
    public P6eTokenResultDto get(P6eTokenParamDto param);
    public P6eTokenResultDto refresh(P6eTokenParamDto param);

    public P6eTokenClientResultDto setClient(P6eTokenClientParamDto param);
    public P6eTokenClientResultDto getClient(P6eTokenClientParamDto param);
    public P6eTokenClientResultDto delClient(P6eTokenClientParamDto param);

}
