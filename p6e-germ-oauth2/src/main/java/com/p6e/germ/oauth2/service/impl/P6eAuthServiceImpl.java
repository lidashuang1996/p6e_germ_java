package com.p6e.germ.oauth2.service.impl;

import com.p6e.germ.oauth2.cache.IP6eCacheAuth;
import com.p6e.germ.oauth2.model.dto.P6eAuthParamDto;
import com.p6e.germ.oauth2.model.dto.P6eAuthResultDto;
import com.p6e.germ.oauth2.model.dto.P6eClientParamDto;
import com.p6e.germ.oauth2.model.dto.P6eClientResultDto;
import com.p6e.germ.oauth2.service.P6eAuthService;
import com.p6e.germ.oauth2.service.P6eClientService;
import com.p6e.germ.oauth2.utils.CommonUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author lidashuang
 * @version 1.0
 */
@Service
public class P6eAuthServiceImpl implements P6eAuthService {

    @Resource
    private P6eClientService p6eClientService;

    @Resource
    private IP6eCacheAuth p6eCacheAuth;

    @Override
    public P6eAuthResultDto code(P6eAuthParamDto param) {
        // 结果对象的创建
        final P6eAuthResultDto p6eAuthResultDto = new P6eAuthResultDto();
        // 查询认证数据
        final String clientId = param.getClientId();
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
            final String redirectUri = param.getRedirectUri();
            if (redirectUri == null) {
                p6eAuthResultDto.setError("从定向URL NULL");
            } else {
                final String redirectUriDb = p6eClientResultDto.getClientRedirectUri();
                final String[] redirectUriDbs = redirectUriDb.split(",");
                for (String uri : redirectUriDbs) {
                    // 匹配回调的 uri 地址
                    if (uri.equals(redirectUri)) {
                        bool = true;
                        break;
                    }
                }
            }
            // 3. 登陆 / 返回 code
            if (bool) {
                // 生产随机 voucher
                final String voucher = CommonUtil.generateUUID();
                p6eCacheAuth.setCodeVoucher(voucher, clientId);
                p6eAuthResultDto.setVoucher(voucher);
            } else {
                p6eAuthResultDto.setError("从定向URL不存在");
            }
        }
        return p6eAuthResultDto;
    }

}
