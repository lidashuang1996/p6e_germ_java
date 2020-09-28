package com.p6e.germ.security.service.impl;

import com.p6e.germ.oauth2.utils.CopyUtil;
import com.p6e.germ.security.mapper.P6eSecurityWholeDataMapper;
import com.p6e.germ.security.model.db.P6eSecurityWholeDataGroupDb;
import com.p6e.germ.security.model.db.P6eSecurityWholeDataUserDb;
import com.p6e.germ.security.model.dto.P6eSecurityWholeDataGroupResultDto;
import com.p6e.germ.security.model.dto.P6eSecurityWholeDataUserResultDto;
import com.p6e.germ.security.service.P6eSecurityWholeDataService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lidashuang
 * @version 1.0
 */
@Service
public class P6eSecurityWholeDataServiceImpl implements P6eSecurityWholeDataService {

    @Resource
    private P6eSecurityWholeDataMapper securityWholeDataMapper;

    @Override
    public P6eSecurityWholeDataUserResultDto user(final Integer id) {
        final List<P6eSecurityWholeDataUserDb> securityWholeDataUserDbList
                = securityWholeDataMapper.user(new P6eSecurityWholeDataUserDb().setUserId(id));
        final List<Map<String, String>> groupList = new ArrayList<>();
        final List<Map<String, String>> jurisdictionList = new ArrayList<>();
        final P6eSecurityWholeDataUserResultDto result = new P6eSecurityWholeDataUserResultDto();
        for (final P6eSecurityWholeDataUserDb db : securityWholeDataUserDbList) {
            // 添加组数据
            boolean gBool = true;
            for (final Map<String, String> map : groupList) {
                if (db.getGroupParam().equals(map.get("id"))) {
                    gBool = false;
                    break;
                }
            }
            if (gBool) {
                final Map<String, String> map = new HashMap<>(2);
                map.put("id", String.valueOf(db.getGroupId()));
                map.put("weight",  String.valueOf(db.getGroupWeight()));
                groupList.add(map);
            }
            // 添加权限数据
            boolean jBool = true;
            for (final Map<String, String> map : jurisdictionList) {
                if (db.getJurisdictionCode().equals(map.get("id"))) {
                    jBool = false;
                    int weight = Integer.parseInt(map.get("weight"));
                    if (weight < db.getGroupWeight()) {
                        map.put("weight", String.valueOf(db.getGroupWeight()));
                        map.put("code",  String.valueOf(db.getJurisdictionCode()));
                        map.put("param",  String.valueOf(db.getGroupParam()));
                        map.put("id", String.valueOf(db.getJurisdictionId()));
                        map.put("content",  String.valueOf(db.getJurisdictionContent()));
                    }
                    break;
                }
            }
            if (jBool) {
                final Map<String, String> map = new HashMap<>(4);
                map.put("id", String.valueOf(db.getJurisdictionId()));
                map.put("weight", String.valueOf(db.getGroupWeight()));
                map.put("code",  String.valueOf(db.getJurisdictionCode()));
                map.put("param",  String.valueOf(db.getGroupParam()));
                map.put("content",  String.valueOf(db.getJurisdictionContent()));
                jurisdictionList.add(map);
            }
        }
        result.setGroupList(groupList);
        result.setJurisdictionList(jurisdictionList);
        return result;
    }

    @Override
    public List<P6eSecurityWholeDataGroupResultDto> group(final Integer id) {
        final List<P6eSecurityWholeDataGroupDb> securityWholeDataGroupDbList =
                securityWholeDataMapper.group(new P6eSecurityWholeDataGroupDb().setGroupId(id));
        return CopyUtil.run(securityWholeDataGroupDbList, P6eSecurityWholeDataGroupResultDto.class);
    }
}
