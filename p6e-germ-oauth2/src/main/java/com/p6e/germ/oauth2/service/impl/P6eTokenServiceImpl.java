package com.p6e.germ.oauth2.service.impl;

import com.p6e.germ.oauth2.cache.IP6eCacheToken;
import com.p6e.germ.oauth2.model.dto.P6eTokenClientParamDto;
import com.p6e.germ.oauth2.model.dto.P6eTokenClientResultDto;
import com.p6e.germ.oauth2.model.dto.P6eTokenParamDto;
import com.p6e.germ.oauth2.model.dto.P6eTokenResultDto;
import com.p6e.germ.oauth2.service.P6eTokenService;
import com.p6e.germ.oauth2.utils.P6eCommonUtil;
import com.p6e.germ.oauth2.utils.P6eJsonUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lidashuang
 * @version 1.0
 */
@Service
public class P6eTokenServiceImpl implements P6eTokenService {

    @Resource
    private IP6eCacheToken p6eCacheToken;

    @Override
    public P6eTokenResultDto set(P6eTokenParamDto param) {
        final String accessToken = P6eCommonUtil.generateUuid();
        final String refreshToken = P6eCommonUtil.generateUuid();
        final P6eTokenResultDto p6eTokenResultDto = new P6eTokenResultDto();
        p6eTokenResultDto.setAccess_token(accessToken);
        p6eTokenResultDto.setExpires_in(3600);
        p6eTokenResultDto.setRefresh_token(refreshToken);
        p6eTokenResultDto.setScope("ALL");
        p6eTokenResultDto.setToken_type("bearer");

        Map<String, String> data = param.getData();
        final String id = String.valueOf(data.get("id"));

        // map
        Map<String, Object> map = new HashMap<>(3);
        map.put("id", id);
        map.put("accessToken", accessToken);
        map.put("refreshToken", refreshToken);

        p6eCacheToken.setUserInfo(id, P6eJsonUtil.toJson(data));
        p6eCacheToken.setAccessToken(accessToken, P6eJsonUtil.toJson(map));
        p6eCacheToken.setRefreshToken(refreshToken, P6eJsonUtil.toJson(map));
        return p6eTokenResultDto;
    }

    @Override
    public P6eTokenResultDto get(P6eTokenParamDto param) {
        final P6eTokenResultDto p6eTokenResultDto = new P6eTokenResultDto();

        return p6eTokenResultDto;
    }

    @Override
    public P6eTokenResultDto refresh(P6eTokenParamDto param) {
        return null;
    }

    @Override
    public P6eTokenClientResultDto setClient(P6eTokenClientParamDto param) {
        return null;
    }

    @Override
    public P6eTokenClientResultDto getClient(P6eTokenClientParamDto param) {
        return null;
    }

    @Override
    public P6eTokenClientResultDto delClient(P6eTokenClientParamDto param) {
        return null;
    }
}
