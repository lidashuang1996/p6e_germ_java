package com.p6e.germ.oauth2.application;

import com.p6e.germ.oauth2.model.dto.P6eClientDataDto;

/**
 * @author lidashuang
 * @version 1.0
 */
public interface P6eClientDataService {

    /**
     *
     */
    public P6eClientDataDto create(P6eClientDataDto param);

    /**
     *
     */
    public P6eClientDataDto delete(P6eClientDataDto param);

    /**
     *
     */
    public P6eClientDataDto update(P6eClientDataDto param);

    /**
     *
     */
    public P6eClientDataDto get(P6eClientDataDto param);

    /**
     *
     */
    public P6eClientDataDto select(P6eClientDataDto param);

}
