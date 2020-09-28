package com.p6e.germ.security.service.impl;

import com.p6e.germ.oauth2.utils.CopyUtil;
import com.p6e.germ.security.cache.P6eCacheAuth;
import com.p6e.germ.security.mapper.P6eOauth2UserMapper2;
import com.p6e.germ.security.model.db.P6eOauth2UserDb;
import com.p6e.germ.security.model.dto.P6eOauth2AuthParamDto;
import com.p6e.germ.security.model.dto.P6eOauth2AuthResultDto;
import com.p6e.germ.security.model.dto.P6eSecurityWholeDataUserResultDto;
import com.p6e.germ.security.service.P6eOauth2AuthService;
import com.p6e.germ.security.service.P6eSecurityWholeDataService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lidashuang
 * @version 1.0
 */
@Service
public class P6eOauth2AuthServiceImpl implements P6eOauth2AuthService {

    private static final String ERROR_RESOURCES_NO_EXIST = "ERROR_RESOURCES_NO_EXIST";
    private static final String ERROR_ACCOUNT_OR_PASSWORD = "ERROR_ACCOUNT_OR_PASSWORD";

    @Resource
    private P6eCacheAuth cacheAuth;

    @Resource
    private P6eOauth2UserMapper2 oauth2UserMapper;

    @Resource
    private P6eSecurityWholeDataService securityWholeDataService;

    @Override
    public P6eOauth2AuthResultDto auth(P6eOauth2AuthParamDto param) {
        final P6eOauth2AuthResultDto result = new P6eOauth2AuthResultDto();
        final P6eOauth2UserDb oauth2UserDb =
                oauth2UserMapper.selectByAccountAndPassword(CopyUtil.run(param, P6eOauth2UserDb.class));
        if (oauth2UserDb == null) {
            result.setError(ERROR_ACCOUNT_OR_PASSWORD);
        } else {
            final Map<String, Object> paramMap = new HashMap<>(8);
            paramMap.put("uid", String.valueOf(oauth2UserDb.getId()));
            paramMap.put("status", String.valueOf(oauth2UserDb.getStatus()));
            paramMap.put("avatar", oauth2UserDb.getAvatar());
            paramMap.put("name", oauth2UserDb.getName());
            paramMap.put("nickname", oauth2UserDb.getNickname());
            paramMap.put("sex", oauth2UserDb.getSex());
            paramMap.put("birthday", oauth2UserDb.getBirthday());
            paramMap.put("role", oauth2UserDb.getRole());
            final P6eSecurityWholeDataUserResultDto wholeDataUser = securityWholeDataService.user(oauth2UserDb.getId());
            paramMap.put("groupList", wholeDataUser.getGroupList());
            paramMap.put("jurisdictionList", wholeDataUser.getJurisdictionList());
            final Map<String, Object> resultMap = cacheAuth.setData(paramMap);
            transformData(resultMap, result);
        }
        return result;
    }

    @Override
    public P6eOauth2AuthResultDto info(P6eOauth2AuthParamDto param) {
        final P6eOauth2AuthResultDto result = new P6eOauth2AuthResultDto();
        final Map<String, Object> map = cacheAuth.getDataByToken(param.getToken());
        transformData(map, result);
        return result;
    }

    @Override
    public P6eOauth2AuthResultDto refresh(P6eOauth2AuthParamDto param) {
        final P6eOauth2AuthResultDto result = new P6eOauth2AuthResultDto();
        final Map<String, Object> map = cacheAuth.delDataByToken(param.getToken());
        if (map == null) {
            result.setError(ERROR_RESOURCES_NO_EXIST);
        } else {
            cacheAuth.setData(map);
            transformData(map, result);
        }
        return result;
    }

    @Override
    public P6eOauth2AuthResultDto logout(P6eOauth2AuthParamDto param) {
        final P6eOauth2AuthResultDto result = new P6eOauth2AuthResultDto();
        final Map<String, Object> map = cacheAuth.delDataByToken(param.getToken());
        if (map == null) {
            result.setError(ERROR_RESOURCES_NO_EXIST);
        } else {
            transformData(map, result);
        }
        return result;
    }

    @SuppressWarnings("all")
    private void transformData(final Map<String, Object> param, final P6eOauth2AuthResultDto result) {
        if (param != null) {
            result.setId(Integer.valueOf(String.valueOf(param.get("uid"))));
            result.setStatus(Integer.valueOf(String.valueOf(param.get("status"))));
            result.setAvatar(String.valueOf(param.get("avatar")));
            result.setName(String.valueOf(param.get("name")));
            result.setNickname(String.valueOf(param.get("nickname")));
            result.setSex(String.valueOf(param.get("sex")));
            result.setBirthday(String.valueOf(param.get("birthday")));
            result.setRole(String.valueOf(param.get("role")));
            result.setGroupList((List<Map<String, String>>) param.get("groupList"));
            result.setJurisdictionList((List<Map<String, String>>) param.get("jurisdictionList"));
            result.setToken(String.valueOf(param.get("token")));
            result.setRefreshToken(String.valueOf(param.get("refreshToken")));
            result.setExpirationTime(Long.valueOf(Double.valueOf(String.valueOf(param.get("expirationTime"))).intValue()));
        }
    }
}
