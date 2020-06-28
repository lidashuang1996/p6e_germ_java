package com.p6e.germ.oauth2.service.impl;

import com.p6e.germ.oauth2.cache.IP6eCacheAuth;
import com.p6e.germ.oauth2.model.dto.P6eAuthParamDto;
import com.p6e.germ.oauth2.model.dto.P6eAuthResultDto;
import com.p6e.germ.oauth2.model.dto.P6eClientParamDto;
import com.p6e.germ.oauth2.model.dto.P6eClientResultDto;
import com.p6e.germ.oauth2.service.P6eAuthService;
import com.p6e.germ.oauth2.service.P6eClientService;
import com.p6e.germ.oauth2.utils.CommonUtil;
import com.p6e.germ.oauth2.utils.CopyUtil;
import com.p6e.germ.oauth2.utils.GsonUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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
            final P6eClientParamDto p6eClientParamDto = new P6eClientParamDto();
            p6eClientParamDto.setClientId(clientId);
            // 数据查询结果对象
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
    public P6eAuthResultDto getCache(P6eAuthParamDto param) {
        try {
            String content = p6eCacheAuth.getCodeMark(param.getMark());
            if (content != null) {
                final P6eClientResultDto p6eClientResultDto = GsonUtil.fromJson(content, P6eClientResultDto.class);
                final String code = CommonUtil.generateUUID();
                p6eCacheAuth.setCode(code, param.getData());
                P6eAuthResultDto p6eAuthResultDto = new P6eAuthResultDto();
                p6eAuthResultDto.setCode(code);
                p6eAuthResultDto.setRedirectUri(p6eClientResultDto.getClientRedirectUri());
                return p6eAuthResultDto;
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }
}
