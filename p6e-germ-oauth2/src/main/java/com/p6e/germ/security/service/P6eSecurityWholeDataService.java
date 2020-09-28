package com.p6e.germ.security.service;

import com.p6e.germ.security.model.dto.P6eSecurityWholeDataGroupResultDto;
import com.p6e.germ.security.model.dto.P6eSecurityWholeDataUserResultDto;

import java.util.List;

/**
 * @author lidashuang
 * @version 1.0
 */
public interface P6eSecurityWholeDataService {

    public P6eSecurityWholeDataUserResultDto user(Integer id);

    public List<P6eSecurityWholeDataGroupResultDto> group(Integer id);
}
