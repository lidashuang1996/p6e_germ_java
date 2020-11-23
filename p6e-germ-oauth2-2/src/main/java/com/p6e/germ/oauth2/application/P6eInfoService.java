package com.p6e.germ.oauth2.application;

import com.p6e.germ.oauth2.domain.entity.P6eTokenEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author lidashuang
 * @version 1.0
 */
@Service
public class P6eInfoService {

    /**
     * 读取信息
     * @param token accessToken
     * @return 读取的信息内容
     */
    public Map<String, String> execute(final String token) {
        try {
            final P6eTokenEntity p6eTokenEntity = P6eTokenEntity.fetchAccessToken(token);
            return p6eTokenEntity.getValue();
        } catch (Exception e) {
            return null;
        }
    }

}
