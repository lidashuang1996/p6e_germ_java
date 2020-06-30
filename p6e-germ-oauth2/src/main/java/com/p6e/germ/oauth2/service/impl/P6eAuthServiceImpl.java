package com.p6e.germ.oauth2.service.impl;

import com.google.gson.reflect.TypeToken;
import com.p6e.germ.oauth2.cache.IP6eCacheAuth;
import com.p6e.germ.oauth2.model.dto.*;
import com.p6e.germ.oauth2.service.P6eAuthService;
import com.p6e.germ.oauth2.service.P6eClientService;
import com.p6e.germ.oauth2.utils.CommonUtil;
import com.p6e.germ.oauth2.utils.GsonUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 认证服务的实现
 * @author lidashuang
 * @version 1.0
 */
@Service
public class P6eAuthServiceImpl implements P6eAuthService {

    /**
     * 客户端授权信息查询对象
     */
    @Resource
    private P6eClientService p6eClientService;

    /**
     * 缓存对象
     */
    @Resource
    private IP6eCacheAuth p6eCacheAuth;

    @Override
    public P6eAuthResultDto code(final P6eAuthParamDto param) {
        // 结果对象的创建
        final P6eAuthResultDto p6eAuthResultDto = new P6eAuthResultDto();
        // 查询认证数据
        final String clientId = param.getClient_id();
        if (clientId == null) {
            p6eAuthResultDto.setError("客户端ID NULL");
        } else {
            // 数据查询结果对象
            // 查询客户端 ID 对应的数据
            final P6eClientParamDto p6eClientParamDto = new P6eClientParamDto();
            p6eClientParamDto.setClientId(clientId);
            P6eClientResultDto p6eClientResultDto = p6eClientService.selectOne(p6eClientParamDto);
            // 判断是否符合
            // 1. 验证 CLIENT ID 的合法性
            if (p6eClientResultDto == null) {
                p6eAuthResultDto.setError("客户端ID不存在");
            } else {
                boolean bool = false;
                // 2. 验证重定向 URL 合法性
                final String redirectUri = param.getRedirect_uri();
                if (redirectUri == null) {
                    p6eAuthResultDto.setError("从定向URL NULL");
                } else {
                    final String redirectUriDb = p6eClientResultDto.getClientRedirectUri();
                    final String[] redirectUriDbs = redirectUriDb.split(",");
                    for (String uri : redirectUriDbs) {
                        // 匹配回调的 uri 地址
                        if (uri.equals(redirectUri)) {
                            bool = true;
                            // 重写
                            p6eClientResultDto.setClientRedirectUri(uri);
                            break;
                        }
                    }
                }
                // 3. 登陆 / 返回 code
                if (bool) {
                    // 生产随机 mark
                    final String mark = CommonUtil.generateUUID();
                    p6eCacheAuth.setCodeMark(mark, GsonUtil.toJson(p6eClientResultDto));
                    p6eAuthResultDto.setMark(mark);
                } else {
                    p6eAuthResultDto.setError("重定向URL不存在");
                }
            }
        }
        return p6eAuthResultDto;
    }

    @Override
    public P6eAuthManageCodeResultDto manageCode(P6eAuthManageCodeParamDto param) {
        final String mark = param.getMark();
        try {
            final String content = p6eCacheAuth.getCodeMark(mark);
            if (content != null) {
                final Map<String, Object> map =
                        GsonUtil.fromJson(content, new TypeToken<Map<String, Object>>(){}.getType());
                if (map != null && map.get("clientRedirectUri") != null) {
                    // 写入用户的数据
                    if (param.getData() != null) {
                        for (String key : param.getData().keySet()) {
                            map.put(key, param.getData().get(key));
                        }
                    }
                    final String codeVoucher = CommonUtil.generateUUID();
                    p6eCacheAuth.setCodeVoucher(codeVoucher, GsonUtil.toJson(map));
                    P6eAuthManageCodeResultDto p6eAuthManageCodeResultDto = new P6eAuthManageCodeResultDto();
                    p6eAuthManageCodeResultDto.setCode(codeVoucher);
                    p6eAuthManageCodeResultDto.setRedirectUri(String.valueOf(map.get("clientRedirectUri")));
                    return p6eAuthManageCodeResultDto;
                } else {
                    return null;
                }
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        } finally {
            p6eCacheAuth.delCodeMark(mark);
        }
    }

    @Override
    public P6eAuthCollateCodeResultDto collateCode(P6eAuthCollateCodeParamDto param) {
        final String codeVoucher = param.getCodeVoucher();
        final P6eAuthCollateCodeResultDto p6eAuthCollateCodeResultDto = new P6eAuthCollateCodeResultDto();
        try {
            String content = p6eCacheAuth.getCodeVoucher(codeVoucher);
            p6eAuthCollateCodeResultDto.setData(GsonUtil.fromJson(content, new TypeToken<Map<String, String>>(){}.getType()));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            p6eCacheAuth.delCodeVoucher(codeVoucher);
        }
        return p6eAuthCollateCodeResultDto;
    }
}
