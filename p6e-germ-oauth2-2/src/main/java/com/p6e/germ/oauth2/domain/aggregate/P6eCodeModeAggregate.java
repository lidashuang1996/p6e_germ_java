package com.p6e.germ.oauth2.domain.aggregate;

import com.p6e.germ.oauth2.domain.entity.*;
import com.p6e.germ.oauth2.domain.keyvalue.P6eAuthKeyValue;
import com.p6e.germ.oauth2.domain.keyvalue.P6eCodeAuthKeyValue;
import com.p6e.germ.oauth2.infrastructure.cache.IP6eCacheAuth;
import com.p6e.germ.oauth2.infrastructure.exception.ParamException;
import com.p6e.germ.oauth2.infrastructure.utils.GeneratorUtil;
import com.p6e.germ.oauth2.infrastructure.utils.JsonUtil;
import com.p6e.germ.oauth2.infrastructure.utils.SpringUtil;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * CODE 授权认证模式
 * @author lidashuang
 * @version 1.0
 */
public class P6eCodeModeAggregate implements Serializable {

    private static final String AUTH_COOKIE = "P6E_OAUTH2_AUTH_TOKEN";

    /** 唯一标记 */
    private final String uniqueId;

    /** 注入缓存对象 */
    private final IP6eCacheAuth p6eCacheAuth = SpringUtil.getBean(IP6eCacheAuth.class);

    /**
     * 创建对象
     * @return P6eCodeModeAggregate 对象
     */
    public static P6eCodeModeAggregate create() {
        return new P6eCodeModeAggregate();
    }

    /** 私有构造方法 */
    private P6eCodeModeAggregate() {
        uniqueId = GeneratorUtil.uuid();
    }

    /**
     * 生成 CODE 授权认证模式需要的数据
     * @param param 参数
     * @return 结果
     */
    public P6eCodeAuthKeyValue generate(final P6eAuthKeyValue param) {
        final P6eClientEntity p6eClientEntity = new P6eClientEntity(param.getClientId());
        if (p6eClientEntity.verificationScope(param.getScope())
                && p6eClientEntity.verificationRedirectUri(param.getRedirectUri())) {
            final P6eVoucherEntity p6eVoucherEntity = P6eVoucherEntity.create(GeneratorUtil.uuid());
            return new P6eCodeAuthKeyValue(
                    P6eMarkEntity.create(GeneratorUtil.uuid(), param).getMark(),
                    p6eVoucherEntity.getKey(),
                    p6eVoucherEntity.getPublicSecretKey()
            );
        } else {
            throw new ParamException(this.getClass() + " generate() ==> Failed to verify scope and redirectUri.");
        }
    }

    public Map<String, String> execute(final String code, final String redirectUri,
                                       final String clientId, final String clientSecret) {
        final P6eClientEntity p6eClientEntity = new P6eClientEntity(clientId);
        if (p6eClientEntity.verificationSecret(clientSecret)
                && p6eClientEntity.verificationRedirectUri(redirectUri)) {
            return JsonUtil.fromJsonToMap(P6eTokenEntity.fetch(
                    P6eAuthEntity.fetch(code).getValue()).getValue(), String.class, String.class);
        } else {
            throw new ParamException(this.getClass() + " execute() ==> Failed to verify scope and redirectUri.");
        }
    }

    public Map<String, String> verification(final HttpServletRequest request, final P6eAuthKeyValue param) {
        try {
            final Cookie[] cookies = request.getCookies();
            if (cookies != null && cookies.length > 0) {
                for (final Cookie cookie : cookies) {
                    if (AUTH_COOKIE.equals(cookie.getName().toUpperCase())) {
                        final P6eClientEntity p6eClientEntity = new P6eClientEntity(param.getClientId());
                        if (p6eClientEntity.verificationScope(param.getScope())
                                && p6eClientEntity.verificationRedirectUri(param.getRedirectUri())) {
                            final String token = P6eTokenEntity.fetch(cookie.getValue()).refresh(GeneratorUtil.uuid()).getKey();
                            final Map<String, String> map = new HashMap<>(2);
                            map.put("token", token);
                            map.put("redirectUri", GeneratorUtil.callbackUrl(
                                    p6eClientEntity.getRedirectUri(),
                                    P6eAuthEntity.create(GeneratorUtil.uuid(), token).getKey()
                            ));
                            return map;
                        } else {
                            throw new ParamException(this.getClass() + " generate() ==> Failed to verify scope and redirectUri.");
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

}
