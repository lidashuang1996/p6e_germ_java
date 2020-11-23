package com.p6e.germ.oauth2.domain.aggregate;

import com.p6e.germ.oauth2.domain.entity.P6eClientEntity;
import com.p6e.germ.oauth2.domain.entity.P6eTokenEntity;
import com.p6e.germ.oauth2.domain.entity.P6eUserEntity;
import com.p6e.germ.oauth2.domain.keyvalue.P6ePasswordModeKeyValue;
import com.p6e.germ.oauth2.domain.keyvalue.P6eTokenKeyValue;
import com.p6e.germ.oauth2.infrastructure.exception.ParamException;
import com.p6e.germ.oauth2.infrastructure.utils.GeneratorUtil;

import java.io.Serializable;

/**
 * @author lidashuang
 * @version 1.0
 */
public class P6ePasswordModeAggregate implements Serializable {
    /** 唯一标记 */
    private final String uniqueId;

    public static P6ePasswordModeAggregate create() {
        return new P6ePasswordModeAggregate();
    }

    /** 私有构造方法 */
    private P6ePasswordModeAggregate() {
        uniqueId = GeneratorUtil.uuid();
    }


    public P6eTokenKeyValue execute(final P6ePasswordModeKeyValue keyValue) {
        final P6eClientEntity p6eClientEntity = P6eClientEntity.fetch(keyValue.getClientId());
        // 验证客户端的信息和用户输入的信息是否匹配
        if (p6eClientEntity.verificationSecret(keyValue.getClientSecret())
                && p6eClientEntity.verificationRedirectUri(keyValue.getRedirectUri())
                && p6eClientEntity.verificationScope(keyValue.getScope())) {
            final P6eUserEntity p6eUserEntity = P6eUserEntity.fetch(keyValue.getAccount());
            if (p6eUserEntity.defaultVerification(keyValue.getPassword())) {
                final P6eTokenEntity p6eTokenEntity = p6eUserEntity.createTokenCache();
                return new P6eTokenKeyValue(
                        p6eTokenEntity.getModel().getAccessToken(),
                        p6eTokenEntity.getModel().getRefreshToken(),
                        p6eTokenEntity.getModel().getTokenType(),
                        p6eTokenEntity.getModel().getExpiresIn()
                );
            } else {
                throw new ParamException("accoutn password cuo wu");
            }
        } else {
            throw new ParamException();
        }
    }
}
